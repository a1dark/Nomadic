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
                        output.accept(ModItems.DIAMOND_NOMAD_SABER.get());
                        output.accept(ModItems.GOLDEN_NOMAD_SABER.get());
                        output.accept(ModItems.IRON_NOMAD_SABER.get());
                        output.accept(ModItems.STONE_NOMAD_SABER.get());
                        output.accept(ModItems.WOODEN_NOMAD_SABER.get());
                        output.accept(ModItems.CHINUTE.get());
                        output.accept(ModItems.KYMUZ.get());

                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> NOMADIC_BLOCKS_TAB = CREATIVE_MODE_TABS.register("nomadic_blocks_tab",
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.CHINUTE.get()))
                    .withTabsBefore(NOMADIC_ITEMS_TAB.getId())
                    .title(Component.translatable("creativetab.nomadic.nomadic_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
