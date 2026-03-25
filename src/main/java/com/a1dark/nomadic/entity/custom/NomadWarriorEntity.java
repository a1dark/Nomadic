package com.a1dark.nomadic.entity.custom;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.entity.NomadVariant;
import com.a1dark.nomadic.item.ModItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

public class NomadWarriorEntity extends Monster implements RangedAttackMob {

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(NomadWarriorEntity.class, EntityDataSerializers.INT);

    public final AnimationState attackAnimationState = new AnimationState();

    private MeleeAttackGoal meleeGoal;
    private RangedBowAttackGoal<NomadWarriorEntity> bowGoal;

    public NomadWarriorEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    public NomadVariant getVariant() {
        return NomadVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                LivingEntity attacker = NomadWarriorEntity.this.getLastHurtByMob();
                if (attacker != null && attacker.getType()
                        .builtInRegistryHolder().key().location()
                        .getNamespace().equals(Nomadic.MOD_ID)) {
                    return false;
                }
                return super.canUse();
            }
        }.setAlertOthers(NomadWarriorEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private void reassessWeaponGoal() {
        if (meleeGoal != null) this.goalSelector.removeGoal(meleeGoal);
        if (bowGoal   != null) this.goalSelector.removeGoal(bowGoal);

        if (getVariant() == NomadVariant.ARCHER) {
            if (bowGoal == null) {
                bowGoal = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
            }
            this.goalSelector.addGoal(1, bowGoal);
        } else {
            if (meleeGoal == null) {
                meleeGoal = new MeleeAttackGoal(this, 1.3D, true);
            }
            this.goalSelector.addGoal(1, meleeGoal);
        }
    }
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                        DifficultyInstance difficulty,
                                        MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);

        NomadVariant variant = (this.random.nextInt(3) == 0)
                ? NomadVariant.ARCHER
                : NomadVariant.WARRIOR;

        this.entityData.set(VARIANT, variant.getId());
        reassessWeaponGoal();

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(
                this, Monster.class, 10, true, false,
                entity -> !entity.getType().builtInRegistryHolder()
                        .key()
                        .location()
                        .getNamespace()
                        .equals(Nomadic.MOD_ID)
        ));

        if (variant == NomadVariant.ARCHER) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else {
            ItemStack weapon = switch (this.random.nextInt(3)) {
                case 0  -> new ItemStack(ModItems.GOLDEN_NOMAD_SABER.get());
                case 1  -> new ItemStack(ModItems.IRON_NOMAD_SABER.get());
                default -> new ItemStack(ModItems.STONE_NOMAD_SABER.get());
            };
            this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
        }

        this.setDropChance(EquipmentSlot.MAINHAND, 0.2F);
        return spawnData;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,     35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE,  7.0D)
                .add(Attributes.FOLLOW_RANGE,   70.0D);
    }

    public static boolean canSpawn(EntityType<NomadWarriorEntity> type,
                                   LevelAccessor level,
                                   MobSpawnType spawnType,
                                   net.minecraft.core.BlockPos pos,
                                   net.minecraft.util.RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public boolean isSunBurnTick() {
        return false;
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(VARIANT, 0);
    }
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            if (this.swinging && !attackAnimationState.isStarted()) {
                attackAnimationState.start(this.tickCount);
            } else if (!this.swinging) {
                attackAnimationState.stop();
            }
        }
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

        arrow.shoot(aimDx, aimDy, aimDz, 1.6F, inaccuracy);
        this.level().addFreshEntity(arrow);
    }
}