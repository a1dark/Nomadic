package com.a1dark.nomadic.entity.custom;

import com.a1dark.nomadic.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class NomadWarriorEntity extends Monster {

    public NomadWarriorEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));

        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                        DifficultyInstance difficulty,
                                        MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnData) {

        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        int random = this.random.nextInt(3);
        ItemStack weapon = switch (random) {
            case 0 -> new ItemStack(ModItems.GOLDEN_NOMAD_SABER.get());
            case 1 -> new ItemStack(ModItems.IRON_NOMAD_SABER.get());
            default -> new ItemStack(ModItems.STONE_NOMAD_SABER.get());
        };
        this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.2F);
        return spawnData;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)     // Diamond sword damage
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }
    @Override
    public boolean isSunBurnTick() {
        return false; // prevents burning in sunlight
    }
    public static boolean canSpawn(EntityType<NomadWarriorEntity> type,
                                   LevelAccessor level,
                                   MobSpawnType spawnType,
                                   net.minecraft.core.BlockPos pos,
                                   net.minecraft.util.RandomSource random) {
        return level.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL;
    }
}
