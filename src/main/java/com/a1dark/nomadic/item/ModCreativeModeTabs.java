package com.a1dark.nomadic.item;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Nomadic.MOD_ID);
    public static final RegistryObject<CreativeModeTab> NOMADIC_ITEMS_TAB = CREATIVE_MODE_TABS.register("nomadic_items_tab",
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.DIAMOND_NOMAD_SABER.get()))
                    .title(Component.translatable("creativetab.nomadic.nomadic_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.TENGRIUM_NOMAD_SABER.get());
                        output.accept(ModItems.DIAMOND_NOMAD_SABER.get());
                        output.accept(ModItems.GOLDEN_NOMAD_SABER.get());
                        output.accept(ModItems.IRON_NOMAD_SABER.get());
                        output.accept(ModItems.STONE_NOMAD_SABER.get());
                        output.accept(ModItems.WOODEN_NOMAD_SABER.get());
                        output.accept(ModItems.CHINUTE.get());
                        output.accept(ModItems.KYMUZ.get());
                        output.accept(ModItems.TENGRIUM.get());
                        output.accept(ModItems.NOMAD_BOW.get());
                        output.accept(ModItems.NOMAD_WARRIOR_SPAWN_EGG.get());


                        output.accept(ModItems.TENGRIUM_NOMAD_HELMET.get());
                        output.accept(ModItems.TENGRIUM_NOMAD_CHESTPLATE.get());
                        output.accept(ModItems.TENGRIUM_NOMAD_LEGGINGS.get());
                        output.accept(ModItems.TENGRIUM_NOMAD_BOOTS.get());

                        output.accept(ModItems.IRON_NOMAD_HELMET.get());
                        output.accept(ModItems.IRON_NOMAD_CHESTPLATE.get());
                        output.accept(ModItems.IRON_NOMAD_LEGGINGS.get());
                        output.accept(ModItems.IRON_NOMAD_BOOTS.get());

                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> NOMADIC_BLOCKS_TAB = CREATIVE_MODE_TABS.register("nomadic_blocks_tab",
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModBlocks.TENGRIUM_BLOCK.get()))
                    .withTabsBefore(NOMADIC_ITEMS_TAB.getId())
                    .title(Component.translatable("creativetab.nomadic.nomadic_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.TENGRIUM_BLOCK.get());
                        output.accept(ModBlocks.TENGRIUM_ORE.get());
                        output.accept(ModBlocks.TENGRIUM_DEEPSLATE_ORE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
