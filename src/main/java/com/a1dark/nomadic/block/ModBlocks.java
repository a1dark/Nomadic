package com.a1dark.nomadic.block;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.block.custom.Shyrdak;
import com.a1dark.nomadic.block.custom.SpiritBeacon;
import com.a1dark.nomadic.block.custom.Tengrium_CampFire;
import com.a1dark.nomadic.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Nomadic.MOD_ID);

    public static final RegistryObject<Block> TENGRIUM_BLOCK = registerBlock("tengrium_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> TENGRIUM_DEEPSLATE_ORE = registerBlock("tengrium_deepslate_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> TENGRIUM_ORE = registerBlock("tengrium_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SPIRIT_BEACON = registerBlock("spirit_beacon",
            () -> new SpiritBeacon(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(6f).requiresCorrectToolForDrops().sound(SoundType.SCULK_CATALYST)));
    public static final RegistryObject<Block> TENGRIUM_CAMPFIRE = registerBlock("tengrium_campfire",
            () -> new Tengrium_CampFire(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.5f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BLUE_SHYRDAK = registerBlock("blue_shyrdak",
            () -> new Shyrdak(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.1f).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> WHITE_SHYRDAK = registerBlock("white_shyrdak",
            () -> new Shyrdak(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.1f).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> GREEN_SHYRDAK = registerBlock("green_shyrdak",
            () -> new Shyrdak(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.1f).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> RED_SHYRDAK = registerBlock("red_shyrdak",
            () -> new Shyrdak(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.1f).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> ORANGE_SHYRDAK = registerBlock("orange_shyrdak",
            () -> new Shyrdak(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.1f).sound(SoundType.WOOL)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}