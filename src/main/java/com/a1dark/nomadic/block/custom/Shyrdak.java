package com.a1dark.nomadic.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Shyrdak extends FaceAttachedHorizontalDirectionalBlock {
    public static final MapCodec<Shyrdak> CODEC = simpleCodec(Shyrdak::new);
    protected static final VoxelShape FLOOR_SHAPE = Block.box(0, 0, 0, 16, 1, 16);
    protected static final VoxelShape CEILING_SHAPE = Block.box(0, 15, 0, 16, 16, 16);
    protected static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 15, 16, 16, 16);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 0, 16, 16, 1);
    protected static final VoxelShape WEST_SHAPE = Block.box(15, 0, 0, 16, 16, 16);
    protected static final VoxelShape EAST_SHAPE = Block.box(0, 0, 0, 1, 16, 16);

    public Shyrdak(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.FLOOR));
    }

    @Override
    protected MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        AttachFace face = state.getValue(FACE);
        Direction direction = state.getValue(FACING);
        if (face == AttachFace.FLOOR) return FLOOR_SHAPE;
        if (face == AttachFace.CEILING) return CEILING_SHAPE;
        return switch (direction) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> FLOOR_SHAPE;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, FACE);
    }
}