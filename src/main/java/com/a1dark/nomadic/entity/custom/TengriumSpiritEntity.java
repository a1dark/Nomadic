package com.a1dark.nomadic.entity.custom;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerBossEvent;

public class TengriumSpiritEntity extends Monster {

    // -------------------------------------------------------------------------
    // Synced data
    // -------------------------------------------------------------------------

    private static final EntityDataAccessor<Boolean> IS_ATTACKING =
            SynchedEntityData.defineId(TengriumSpiritEntity.class, EntityDataSerializers.BOOLEAN);

    // -------------------------------------------------------------------------
    // Animation states
    // -------------------------------------------------------------------------

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    // Attack cooldown — how many ticks between lightning strikes
    private int attackCooldown = 0;
    private static final int ATTACK_WINDUP_TICKS = 20;
    private int attackWindupTick = 0;
    private boolean lightningFired = false;

    // Boss health bar
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            this.getDisplayName(),
            BossEvent.BossBarColor.BLUE,
            BossEvent.BossBarOverlay.NOTCHED_10
    );

    public TengriumSpiritEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setNoGravity(true);
        this.xpReward = 100;
    }

    // -------------------------------------------------------------------------
    // Goals
    // -------------------------------------------------------------------------

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TengriumLightningAttackGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, true));
    }

    // -------------------------------------------------------------------------
    // Navigation — flying
    // -------------------------------------------------------------------------

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(true);
        return nav;
    }
    // -------------------------------------------------------------------------
    // Attributes
    // -------------------------------------------------------------------------

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,      300.0D)
                .add(Attributes.MOVEMENT_SPEED,  0.35D)
                .add(Attributes.FOLLOW_RANGE,    64.0D)
                .add(Attributes.FLYING_SPEED,    0.50D)
                .add(Attributes.ARMOR,           10.0D)
                .add(Attributes.ATTACK_DAMAGE,   12.0D);
    }

    // -------------------------------------------------------------------------
    // Synced data
    // -------------------------------------------------------------------------

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(IS_ATTACKING, false);
    }

    public boolean isAttacking() {
        return this.entityData.get(IS_ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(IS_ATTACKING, attacking);
    }

    // -------------------------------------------------------------------------
    // Boss bar
    // -------------------------------------------------------------------------

    @Override
    public void startSeenByPlayer(net.minecraft.server.level.ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(net.minecraft.server.level.ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    // -------------------------------------------------------------------------
    // Tick — animation sync + boss bar update
    // -------------------------------------------------------------------------

    @Override
    public void tick() {
        super.tick();
        // На сервере обновляем только КД и полоску хитбара
        if (!this.level().isClientSide()) {
            if (attackCooldown > 0) {
                attackCooldown--;
            }
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        }

        // На клиенте — только анимации
        if (this.level().isClientSide()) {
            if (!idleAnimationState.isStarted()) idleAnimationState.start(this.tickCount);

            boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.001D;
            if (isMoving) {
                if (!walkAnimationState.isStarted()) walkAnimationState.start(this.tickCount);
            } else {
                walkAnimationState.stop();
            }

            if (this.isAttacking()) {
                if (!attackAnimationState.isStarted()) attackAnimationState.start(this.tickCount);
            } else {
                attackAnimationState.stop();
            }
        }

        // Update boss health bar
        if (!this.level().isClientSide()) {
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
            this.bossEvent.setName(this.getDisplayName());
        }

        // Client-side animation driving
        if (this.level().isClientSide()) {

            // Idle всегда играет
            if (!idleAnimationState.isStarted()) {
                idleAnimationState.start(this.tickCount);
            }

            // Walk анимация поверх idle
            boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.001D;
            if (isMoving) {
                if (!walkAnimationState.isStarted()) {
                    walkAnimationState.start(this.tickCount);
                }
            } else {
                walkAnimationState.stop();
            }

            // Attack анимация поверх idle
            if (this.isAttacking()) {
                if (!attackAnimationState.isStarted()) {
                    attackAnimationState.start(this.tickCount);
                }
            } else {
                attackAnimationState.stop();
            }

        }

        // Server-side attack windup — fire lightning after windup completes
        if (!this.level().isClientSide() && this.isAttacking()) {
            attackWindupTick++;
            if (!lightningFired && attackWindupTick >= ATTACK_WINDUP_TICKS) {
                fireLightning();
                lightningFired = true;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Lightning strike
    // -------------------------------------------------------------------------

    public void fireLightning() {
        LivingEntity target = this.getTarget();
        if (target == null || !(this.level() instanceof ServerLevel serverLevel)) return;

        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverLevel);
        if (lightning != null) {
            lightning.moveTo(target.getX(), target.getY(), target.getZ());
            serverLevel.addFreshEntity(lightning);
            this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 2.0F, 0.8F + this.random.nextFloat() * 0.4F);
        }
        // Устанавливаем КД здесь
        this.attackCooldown = 45;
    }

    public void setAttackCooldown(int ticks) {
        this.attackCooldown = ticks;
    }

    // -------------------------------------------------------------------------
    // NBT — persist boss data
    // -------------------------------------------------------------------------

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackCooldown", attackCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        attackCooldown = tag.getInt("AttackCooldown");
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    public boolean isSunBurnTick() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    // -------------------------------------------------------------------------
    // Custom lightning attack goal
    // -------------------------------------------------------------------------

    static class TengriumLightningAttackGoal extends Goal {
        private final TengriumSpiritEntity mob;
        private int timer;

        public TengriumLightningAttackGoal(TengriumSpiritEntity mob) {
            this.mob = mob;
            this.setFlags(java.util.EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            // Начинаем, если есть цель и КД прошел
            return mob.getTarget() != null && mob.attackCooldown <= 0;
        }

        @Override
        public boolean canContinueToUse() {
            // Продолжаем выполнять Goal, пока не закончится таймер анимации/замаха
            return timer > 0 && mob.getTarget() != null;
        }

        @Override
        public void start() {
            // ATTACK_WINDUP_TICKS (20 тиков)
            this.timer = 25;
            mob.setAttacking(true);
        }

        @Override
        public void stop() {
            mob.setAttacking(false);
            this.timer = 0;
        }

        @Override
        public void tick() {
            LivingEntity target = mob.getTarget();
            if (target == null) return;

            // Постоянно смотрим на цель
            mob.getLookControl().setLookAt(target, 30F, 30F);

            // Держимся на дистанции (не подлетая вплотную)
            if (mob.distanceToSqr(target) > 64.0D) {
                mob.getNavigation().moveTo(target, 1.2D);
            } else {
                mob.getNavigation().stop();
            }

            this.timer--;

            // В нужный момент (например, на 20-м тике) пускаем молнию
            if (this.timer == 5) {
                mob.fireLightning();
            }
        }
    }
}