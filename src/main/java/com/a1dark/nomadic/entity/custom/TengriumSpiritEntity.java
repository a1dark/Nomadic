package com.a1dark.nomadic.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
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
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TengriumSpiritEntity extends Monster {

    public static final EntityDataAccessor<Integer> ATTACK_STATE =
            SynchedEntityData.defineId(TengriumSpiritEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackRhAnimationState = new AnimationState();
    public final AnimationState attackLhAnimationState = new AnimationState();

    public int attackCooldown = 0;

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

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // Наш единственный новый Goal для атак
        this.goalSelector.addGoal(1, new TengriumAttackGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.55D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.FLYING_SPEED, 1D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ATTACK_STATE, 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (this.attackCooldown > 0) this.attackCooldown--;

            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
            this.bossEvent.setName(this.getDisplayName());
        }

        if (this.level().isClientSide()) {
            if (!idleAnimationState.isStarted()) idleAnimationState.start(this.tickCount);

            boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.001D;
            if (isMoving) {
                if (!walkAnimationState.isStarted()) walkAnimationState.start(this.tickCount);
            } else {
                walkAnimationState.stop();
            }
            int state = this.entityData.get(ATTACK_STATE);
            if (state == 1) {
                if (!attackRhAnimationState.isStarted()) attackRhAnimationState.start(this.tickCount);
                attackLhAnimationState.stop();
            } else if (state == 2) {
                if (!attackLhAnimationState.isStarted()) attackLhAnimationState.start(this.tickCount);
                attackRhAnimationState.stop();
            } else {
                attackRhAnimationState.stop();
                attackLhAnimationState.stop();
            }
        }
    }

    public void fireFireball() {
        LivingEntity target = this.getTarget();
        if (target != null) {
            float bodyYaw = this.yBodyRot * ((float)Math.PI / 180F);
            double offsetX = Math.cos(bodyYaw) * 1.2D;
            double offsetZ = Math.sin(bodyYaw) * 1.2D;
            double offsetY = 1.5D; // Высота руки

            double d0 = target.getX() - (this.getX() + offsetX);
            double d1 = target.getY(0.5D) - (this.getY() + offsetY);
            double d2 = target.getZ() - (this.getZ() + offsetZ);

            Vec3 velocity = new Vec3(d0, d1, d2);
            SmallFireball fireball = new SmallFireball(this.level(), this, velocity);
            fireball.setPos(this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ);

            this.level().addFreshEntity(fireball);
            this.level().addFreshEntity(fireball);
            this.level().addFreshEntity(fireball);
            this.level().addFreshEntity(fireball);
            this.playSound(SoundEvents.GHAST_SHOOT, 1.0F, 1.0F);
        }
    }

    public void fireLightning() {
        LivingEntity target = this.getTarget();
        if (target != null && this.level() instanceof ServerLevel serverLevel) {
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverLevel);
            if (lightning != null) {
                lightning.moveTo(target.position());
                serverLevel.addFreshEntity(lightning);
                serverLevel.addFreshEntity(lightning);
                serverLevel.addFreshEntity(lightning);
                this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 2.0F, 1.0F);
            }
        }
    }
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }
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
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackCooldown", attackCooldown);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        attackCooldown = tag.getInt("AttackCooldown");
    }
    @Override
    public boolean isNoGravity() { return true; }
    static class TengriumAttackGoal extends Goal {
        private final TengriumSpiritEntity mob;
        private int timer;

        public TengriumAttackGoal(TengriumSpiritEntity mob) {
            this.mob = mob;
            this.setFlags(java.util.EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return mob.getTarget() != null && mob.attackCooldown <= 0;
        }
        @Override
        public void start() {
            this.timer = 60;
            int type = mob.getRandom().nextBoolean() ? 1 : 2;
            mob.getEntityData().set(TengriumSpiritEntity.ATTACK_STATE, type);
        }
        @Override
        public void tick() {
            LivingEntity target = mob.getTarget();
            if (target != null) {
                mob.getLookControl().setLookAt(target, 30F, 30F);
                int type = mob.getEntityData().get(TengriumSpiritEntity.ATTACK_STATE);
                if (type == 1 && this.timer == 30) {
                    mob.fireLightning();
                }
                if (type == 2 && this.timer <= 40 && this.timer >= 25) {
                    if (this.timer % 3 == 0) {
                        mob.fireFireball();
                    }
                }
            }
            this.timer--;
        }

        @Override
        public boolean canContinueToUse() {
            return this.timer > 0;
        }

        @Override
        public void stop() {
            mob.getEntityData().set(TengriumSpiritEntity.ATTACK_STATE, 0);
            mob.attackCooldown = 70;
        }
    }
}