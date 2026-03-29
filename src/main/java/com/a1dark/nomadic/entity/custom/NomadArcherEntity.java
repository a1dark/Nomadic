package com.a1dark.nomadic.entity.custom;

import com.a1dark.nomadic.Nomadic;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class NomadArcherEntity extends Monster implements RangedAttackMob {

    private boolean spawnSetupDone = false;

    public NomadArcherEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    @Override
    protected GroundPathNavigation createNavigation(Level level) {
        GroundPathNavigation nav = new GroundPathNavigation(this, level);
        nav.setCanFloat(true);
        nav.setCanOpenDoors(true);
        nav.setCanPassDoors(true);
        return nav;
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                LivingEntity attacker = NomadArcherEntity.this.getLastHurtByMob();
                if (attacker != null && attacker.getType()
                        .builtInRegistryHolder().key().location()
                        .getNamespace().equals(Nomadic.MOD_ID)) {
                    return false;
                }
                return super.canUse();
            }
        }.setAlertOthers(NomadArcherEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(
                this, Monster.class, 10, true, false,
                entity -> !entity.getType().builtInRegistryHolder()
                        .key().location().getNamespace().equals(Nomadic.MOD_ID)
        ));
    }
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                        DifficultyInstance difficulty,
                                        MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.2F);
        spawnSetupDone = true;
        return spawnData;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (!this.level().isClientSide() && !spawnSetupDone) {
            if (this.getMainHandItem().isEmpty()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
        }
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,     30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE,  3.0D)
                .add(Attributes.FOLLOW_RANGE,   70.0D);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnType) {
        return level.getDifficulty() != Difficulty.PEACEFUL;
    }
    public static boolean canSpawn(EntityType<NomadArcherEntity> type,
                                   LevelAccessor level,
                                   MobSpawnType spawnType,
                                   BlockPos pos,
                                   RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public boolean isSunBurnTick() {
        return false;
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        if (this.level().isClientSide()) return;

        Arrow arrow = EntityType.ARROW.create(this.level());
        if (arrow == null) return;

        arrow.setPos(this.getX(), this.getEyeY() - 0.1, this.getZ());
        arrow.setOwner(this);

        double dx = target.getX() - this.getX();
        double dy = target.getY(0.3333D) - this.getEyeY();
        double dz = target.getZ() - this.getZ();
        double horizontalDist = Math.sqrt(dx * dx + dz * dz);

        double travelTicks = horizontalDist / 1.6D;
        double aimDx = (target.getX() + target.getDeltaMovement().x * travelTicks) - this.getX();
        double aimDz = (target.getZ() + target.getDeltaMovement().z * travelTicks) - this.getZ();
        double aimDy = dy + horizontalDist * 0.2F;

        float inaccuracy = switch (this.level().getDifficulty()) {
            case EASY   -> 7.0F;
            case NORMAL -> 4.0F;
            case HARD   -> 1.0F;
            default     -> 6.0F;
        };
        arrow.shoot(aimDx, aimDy, aimDz, 2.4F, inaccuracy);
        this.level().addFreshEntity(arrow);
    }
}