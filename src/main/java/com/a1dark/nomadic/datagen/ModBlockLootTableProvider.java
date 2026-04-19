package com.a1dark.nomadic.datagen;

import com.a1dark.nomadic.block.ModBlocks;
import com.a1dark.nomadic.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.TENGRIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.TENGRIUM_ORE.get(), ModItems.TENGRIUM.get()));

        this.add(ModBlocks.TENGRIUM_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.TENGRIUM_DEEPSLATE_ORE.get(), ModItems.TENGRIUM.get(), 1, 2));

        dropSelf(ModBlocks.TENGRIUM_BLOCK.get());
        dropSelf(ModBlocks.SPIRIT_BEACON.get());
        dropSelf(ModBlocks.TENGRIUM_CAMPFIRE.get());
        dropSelf(ModBlocks.BLUE_SHYRDAK.get());
        dropSelf(ModBlocks.WHITE_SHYRDAK.get());
        dropSelf(ModBlocks.GREEN_SHYRDAK.get());
        dropSelf(ModBlocks.RED_SHYRDAK.get());
        dropSelf(ModBlocks.ORANGE_SHYRDAK.get());

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
