package com.a1dark.nomadic.entity.custom;
import com.a1dark.nomadic.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

public class TengriumSpiritEntity extends Monster {

    public static final int STATE_IDLE   = 0;
    public static final int STATE_MELEE  = 1;
    public static final int STATE_RANGED = 2;
    @net.minecraftforge.api.distmarker.OnlyIn(net.minecraftforge.api.distmarker.Dist.CLIENT)
    private boolean musicStarted = false;

    public static final EntityDataAccessor<Integer> ATTACK_STATE =
            SynchedEntityData.defineId(TengriumSpiritEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState     = new AnimationState();
    public final AnimationState walkAnimationState     = new AnimationState();
    public final AnimationState attackRhAnimationState = new AnimationState();
    public final AnimationState attackLhAnimationState = new AnimationState();

    public int meleeCooldown  = 0;
    public int rangedCooldown = 0;

    public static final double MELEE_RANGE_SQ = 6.25D;

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
        this.goalSelector.addGoal(1, new TengriumMeleeGoal(this));
        this.goalSelector.addGoal(2, new TengriumRangedGoal(this));

        this.goalSelector.addGoal(3, new Goal() {
            @Override public boolean canUse() { return getTarget() != null; }
            @Override public void tick() {
                LivingEntity target = getTarget();
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, true));
    }
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (!this.level().isClientSide() && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(0, 12000, true, true);
        }
    }
    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide() && this.getTarget() != null) {
            LivingEntity target = this.getTarget();
            double targetY = target.getY() + 4.0D;
            if (this.getY() > targetY) {
                Vec3 delta = this.getDeltaMovement();
                this.setDeltaMovement(delta.x, delta.y + (targetY - this.getY()) * 0.02D, delta.z);
            }
        }
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,     250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FOLLOW_RANGE,   64.0D)
                .add(Attributes.FLYING_SPEED,   0.7D)
                .add(Attributes.ARMOR,          10.0D)
                .add(Attributes.ATTACK_DAMAGE,  15.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    @Override
    public boolean fireImmune() { return true; }
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.LIGHTNING_BOLT)) return false;
        if (source.is(DamageTypes.EXPLOSION))      return false;
        if (source.is(DamageTypes.PLAYER_EXPLOSION)) return false;
        return super.hurt(source, amount);
    }
    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        return nav;
    }
    @Override
    protected void checkFallDamage(double pY, boolean pOnGround,
                                   BlockState pState, BlockPos pPos) {}
    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier,
                                   DamageSource pSource) { return false; }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ATTACK_STATE, STATE_IDLE);
    }
    public int getAttackState() { return this.entityData.get(ATTACK_STATE); }
    public void setAttackState(int state) { this.entityData.set(ATTACK_STATE, state); }
    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (meleeCooldown  > 0) meleeCooldown--;
            if (rangedCooldown > 0) rangedCooldown--;
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        }
        if (this.level().isClientSide()) {
            this.tickBossMusic();
            if (!idleAnimationState.isStarted()) {
                idleAnimationState.start(this.tickCount);
            }
            boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.01D;
            if (isMoving) {
                if (!walkAnimationState.isStarted()) walkAnimationState.start(this.tickCount);
            } else {
                walkAnimationState.stop();
            }
            int state = getAttackState();
            if (state == STATE_MELEE) {
                if (!attackRhAnimationState.isStarted()) attackRhAnimationState.start(this.tickCount);
                attackLhAnimationState.stop();
            } else if (state == STATE_RANGED) {
                if (!attackLhAnimationState.isStarted()) attackLhAnimationState.start(this.tickCount);
                attackRhAnimationState.stop();
            } else {
                attackRhAnimationState.stop();
                attackLhAnimationState.stop();
            }
        }
        if (!this.level().isClientSide()) {
            if (meleeCooldown  > 0) meleeCooldown--;
            if (rangedCooldown > 0) rangedCooldown--;
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    public void performMeleeAttack(LivingEntity target) {
        this.doHurtTarget(target);
        Vec3 knockDir = target.position().subtract(this.position()).normalize();
        target.setDeltaMovement(knockDir.x * 1.5, 0.4, knockDir.z * 1.5);
        target.hurtMarked = true;
        Vec3 selfBounce = this.position().subtract(target.position()).normalize();
        this.setDeltaMovement(selfBounce.x * 0.8, 0.2, selfBounce.z * 0.8);
        this.level().explode(
                null,
                target.getX(),
                target.getY(),
                target.getZ(),
                3.0F,
                false,
                Level.ExplosionInteraction.MOB
        );
        this.playSound(ModSounds.TENGRIUM_DASH.get(), 1.5F, 0.8F);
    }
    public void performLightningAttack(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        for (int i = 0; i < 20; i++) {
            double px = this.getX() + (this.random.nextDouble() - 0.5) * 2.0;
            double py = this.getY() + 1.5 + (this.random.nextDouble() - 0.5) * 2.0;
            double pz = this.getZ() + (this.random.nextDouble() - 0.5) * 2.0;
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, px, py, pz, 1, 0, 0, 0, 0.1);
        }
        spawnLightning(serverLevel, target.getX(), target.getY(), target.getZ());
        for (int i = 0; i < 2; i++) {
            double ox = (this.random.nextDouble() - 0.5) * 8.0;
            double oz = (this.random.nextDouble() - 0.5) * 8.0;
            spawnLightning(serverLevel, target.getX() + ox, target.getY(), target.getZ() + oz);
        }
        if (this.getHealth() < this.getMaxHealth() * 0.5f) {
            for (int i = 0; i < 4; i++) {
                double ox = (this.random.nextDouble() - 0.5) * 14.0;
                double oz = (this.random.nextDouble() - 0.5) * 14.0;
                spawnLightning(serverLevel, target.getX() + ox, target.getY(), target.getZ() + oz);
            }
        }
        this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 2.0F, 0.9F);
    }
    private void spawnLightning(ServerLevel level, double x, double y, double z) {
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt == null) return;
        bolt.moveTo(x, y, z);
        bolt.setCause(null);
        level.addFreshEntity(bolt);
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
        tag.putInt("MeleeCooldown", meleeCooldown);
        tag.putInt("RangedCooldown", rangedCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        meleeCooldown  = tag.getInt("MeleeCooldown");
        rangedCooldown = tag.getInt("RangedCooldown");
        if (this.hasCustomName()) this.bossEvent.setName(this.getDisplayName());
    }
    static class TengriumMeleeGoal extends Goal {
        private final TengriumSpiritEntity mob;
        private int timer = 0;
        private boolean hitDealt = false;

        TengriumMeleeGoal(TengriumSpiritEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = mob.getTarget();
            return target != null && mob.meleeCooldown <= 0 && mob.distanceToSqr(target) < 256;
        }

        @Override
        public void start() {
            this.timer = 60;
            this.hitDealt = false;
            mob.setAttackState(TengriumSpiritEntity.STATE_MELEE);
        }
        @Override
        public void tick() {
            LivingEntity target = mob.getTarget();
            if (target != null) {
                mob.getLookControl().setLookAt(target, 30F, 30F);

                double distSq = mob.distanceToSqr(target);

                if (distSq > 4.0D) {
                    mob.setAttackState(TengriumSpiritEntity.STATE_IDLE);
                    mob.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.8D);
                    }
                else {
                    if (!hitDealt) {
                        mob.setAttackState(TengriumSpiritEntity.STATE_MELEE);
                        mob.performMeleeAttack(target);
                        hitDealt = true;
                        this.timer = 15;
                    }
                }
            }
            this.timer--;
        }
        @Override
        public boolean canContinueToUse() {
            return timer > 0 && mob.getTarget() != null && !hitDealt;
        }
        @Override
        public void stop() {
            mob.setAttackState(TengriumSpiritEntity.STATE_IDLE);
            mob.meleeCooldown = 100;
            mob.setDeltaMovement(mob.getDeltaMovement().add(0, 0.5, 0));
        }
    }
    static class TengriumRangedGoal extends Goal {
        private final TengriumSpiritEntity mob;
        private int timer = 0;
        private static final int WINDUP = 20;
        private boolean shotFired = false;

        TengriumRangedGoal(TengriumSpiritEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }
        @Override
        public boolean canUse() {
            LivingEntity target = mob.getTarget();
            if (target == null || mob.rangedCooldown > 0) return false;
            return mob.distanceToSqr(target) > TengriumSpiritEntity.MELEE_RANGE_SQ
                    || mob.meleeCooldown > 0;
        }
        @Override
        public boolean canContinueToUse() { return timer > 0; }

        @Override
        public void start() {
            timer = 40;
            shotFired = false;
            mob.setAttackState(TengriumSpiritEntity.STATE_RANGED);
        }
        @Override
        public void tick() {
            LivingEntity target = mob.getTarget();
            if (target != null) {
                mob.getLookControl().setLookAt(target, 30F, 30F);
                if (!shotFired && timer <= (40 - WINDUP)) {
                    mob.performLightningAttack(target);
                    shotFired = true;
                }
            }
            timer--;
        }
        @Override
        public void stop() {
            mob.setAttackState(TengriumSpiritEntity.STATE_IDLE);
            mob.rangedCooldown = 80;
        }
    }
    @net.minecraftforge.api.distmarker.OnlyIn(net.minecraftforge.api.distmarker.Dist.CLIENT)
    private void tickBossMusic() {
        if (!musicStarted && this.isAlive()) {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.player != null && mc.player.distanceToSqr(this) < 2500) { // Дистанция ~50 блоков
                mc.getSoundManager().play(new net.minecraft.client.resources.sounds.AbstractTickableSoundInstance(
                        ModSounds.SPIRIT_THEME.get(), net.minecraft.sounds.SoundSource.RECORDS,
                        net.minecraft.util.RandomSource.create()) {
                    {
                        this.looping = true;
                        this.delay = 0;
                        this.volume = 1.0F;
                        this.relative = false;
                    }

                    @Override
                    public void tick() {
                        if (TengriumSpiritEntity.this.isRemoved() || !TengriumSpiritEntity.this.isAlive()) {
                            this.stop();
                        } else {
                            this.x = (float) TengriumSpiritEntity.this.getX();
                            this.y = (float) TengriumSpiritEntity.this.getY();
                            this.z = (float) TengriumSpiritEntity.this.getZ();
                        }
                    }
                });
                musicStarted = true;
            }
        }
    }
}