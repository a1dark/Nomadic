package com.a1dark.nomadic.block.custom;

import com.a1dark.nomadic.sound.ModSounds;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Tengrium_CampFire extends HorizontalDirectionalBlock {
    public static final MapCodec<Tengrium_CampFire> CODEC = simpleCodec(Tengrium_CampFire::new);
    public Tengrium_CampFire(Properties pProperties) {
        super(pProperties.lightLevel((state) -> 15).noOcclusion());
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.fireImmune() && entity instanceof LivingEntity) {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }
        super.stepOn(level, pos, state, entity);
    }
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;
            if (level.isDay() && !level.isThundering()) {
                player.displayClientMessage(Component.translatable("tile.minecraft.bed.no_sleep"), true);
                return InteractionResult.SUCCESS;
            }
            Component skipMessage = Component.translatable("sleep.skipping_night");
            for (net.minecraft.server.level.ServerPlayer serverPlayer : serverLevel.getServer().getPlayerList().getPlayers()) {
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 90, 1));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 10));
                serverLevel.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                        ModSounds.TENGRIUM.get(), SoundSource.AMBIENT, 6.0F, 2.0F);
                serverPlayer.displayClientMessage(skipMessage, true);
            }
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            long dayTime = serverLevel.getDayTime();
            serverLevel.setDayTime((dayTime / 24000L + 1L) * 24000L);

            if (serverLevel.isThundering() || serverLevel.isRaining()) {
                serverLevel.setWeatherParameters(6000, 0, false, false);
            }
            level.destroyBlock(pos, false);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = (double) pos.getX() + 0.5D;
        double y = (double) pos.getY();
        double z = (double) pos.getZ() + 0.5D;
        if (random.nextInt(10) == 0) {
            level.playLocalSound(x, y, z, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
        }
        for(int i = 0; i < 2; i++) {
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                    x + (random.nextDouble() - 0.5D) * 0.3D,
                    y + 0.1D + random.nextDouble() * 0.3D,
                    z + (random.nextDouble() - 0.5D) * 0.3D,
                    0.0D, 0.01D, 0.0D);
        }
        if (random.nextInt(1) == 0) { // Каждый тик
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    x + (random.nextDouble() - 0.5D) * 0.2D,
                    y + 0.4D,
                    z + (random.nextDouble() - 0.5D) * 0.2D,
                    0.0D, 0.07D, 0.0D);
        }
        if (random.nextInt(3) == 0) {
            level.addParticle(ParticleTypes.SOUL, x, y + 0.2D, z, 0.0D, 0.02D, 0.0D);
        }
        if (random.nextFloat() < 0.2F) {
            level.addParticle(ParticleTypes.LARGE_SMOKE, x, y + 0.3D, z, 0.0D, 0.01D, 0.0D);
        }
    }
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}