package com.a1dark.nomadic.item;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.item.custom.KymuzItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static net.minecraft.world.item.Items.registerItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS=
            DeferredRegister.create(ForgeRegistries.ITEMS, Nomadic.MOD_ID);
    public static final RegistryObject<Item> CHINUTE=ITEMS.register("chinute", () -> new Item(new Item.Properties().food(ModFoodProperties.CHINUTE)));
    public static final RegistryObject<Item> KYMUZ=ITEMS.register("kymuz", () -> new KymuzItem(new Item.Properties().food(ModFoodProperties.KYMUZ)));

    public static final RegistryObject<Item> DIAMOND_NOMAD_SABER = ITEMS.register("diamond_nomad_saber", ()->
            new SwordItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.DIAMOND, 5, -2F))));

    public static final RegistryObject<Item> GOLDEN_NOMAD_SABER = ITEMS.register("golden_nomad_saber", ()->
            new SwordItem(Tiers.GOLD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.GOLD, 5, -2F))));

    public static final RegistryObject<Item> IRON_NOMAD_SABER = ITEMS.register("iron_nomad_saber", ()->
            new SwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 5, -2F))));

    public static final RegistryObject<Item> STONE_NOMAD_SABER = ITEMS.register("stone_nomad_saber", ()->
            new SwordItem(Tiers.STONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.STONE, 5, -2F))));

    public static final RegistryObject<Item> WOODEN_NOMAD_SABER = ITEMS.register("wooden_nomad_saber", ()->
            new SwordItem(Tiers.WOOD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.WOOD, 5, -2F))));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
