package com.a1dark.nomadic.item;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.item.custom.KymuzItem;
import com.a1dark.nomadic.item.custom.ModArmorItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS=
            DeferredRegister.create(ForgeRegistries.ITEMS, Nomadic.MOD_ID);
    public static final RegistryObject<Item> CHINUTE=ITEMS.register("chinute", () -> new Item(new Item.Properties().food(ModFoodProperties.CHINUTE)));
    public static final RegistryObject<Item> KYMUZ=ITEMS.register("kymuz", () -> new KymuzItem(new Item.Properties().food(ModFoodProperties.KYMUZ)));

    public static final RegistryObject<Item> TENGRIUM= ITEMS.register("tengrium", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TENGRIUM_NOMAD_SABER = ITEMS.register("tengrium_nomad_saber", ()->
            new SwordItem(ModToolTiers.TENGRIUM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.TENGRIUM, 5, -2F))));
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

    public static final RegistryObject<Item> TENGRIUM_NOMAD_HELMET = ITEMS.register("tengrium_nomad_helmet", ()->
            new ModArmorItem(ModArmorMaterials.TENGRIUM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));

    public static final RegistryObject<Item> TENGRIUM_NOMAD_CHESTPLATE = ITEMS.register("tengrium_nomad_chestplate", ()->
            new ModArmorItem(ModArmorMaterials.TENGRIUM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));

    public static final RegistryObject<Item> TENGRIUM_NOMAD_LEGGINGS = ITEMS.register("tengrium_nomad_leggings", ()->
            new ModArmorItem(ModArmorMaterials.TENGRIUM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));

    public static final RegistryObject<Item> TENGRIUM_NOMAD_BOOTS = ITEMS.register("tengrium_nomad_boots", ()->
            new ModArmorItem(ModArmorMaterials.TENGRIUM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));
    //Iron Nomad Armor
    public static final RegistryObject<Item> IRON_NOMAD_HELMET = ITEMS.register("iron_nomad_helmet", ()->
            new ModArmorItem(ModArmorMaterials.NOMAD_IRON_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final RegistryObject<Item> IRON_NOMAD_CHESTPLATE = ITEMS.register("iron_nomad_chestplate", ()->
            new ModArmorItem(ModArmorMaterials.NOMAD_IRON_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));
    public static final RegistryObject<Item> IRON_NOMAD_LEGGINGS = ITEMS.register("iron_nomad_leggings", ()->
            new ModArmorItem(ModArmorMaterials.NOMAD_IRON_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
    public static final RegistryObject<Item> IRON_NOMAD_BOOTS = ITEMS.register("iron_nomad_boots", ()->
            new ModArmorItem(ModArmorMaterials.NOMAD_IRON_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));

    public static final RegistryObject<Item> NOMAD_BOW = ITEMS.register("nomad_bow",
            ()-> new BowItem(new Item.Properties().durability(400)));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
