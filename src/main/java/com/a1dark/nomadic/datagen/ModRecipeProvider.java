package com.a1dark.nomadic.datagen;

import com.a1dark.nomadic.item.ModItems;
import com.a1dark.nomadic.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CHINUTE.get())
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A', Items.APPLE)
                .define('B', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(Items.APPLE), has(Items.APPLE)).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TENGRIUM_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_NOMAD_SABER.get())
                .pattern("  B")
                .pattern(" BB")
                .pattern("A  ")
                .define('A', Items.STICK)
                .define('B', Items.COBBLESTONE)
                .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE)).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_NOMAD_SABER.get())
                .pattern("  B")
                .pattern(" BB")
                .pattern("A  ")
                .define('A', Items.STICK)
                .define('B', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_NOMAD_SABER.get())
                .pattern("  B")
                .pattern(" BB")
                .pattern("A  ")
                .define('A', Items.STICK)
                .define('B', Items.GOLD_INGOT)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT)).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_NOMAD_SABER.get())
                .pattern("  B")
                .pattern(" BB")
                .pattern("AD ")
                .define('A', Items.STICK)
                .define('B', Items.DIAMOND)
                .define('D', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);
        /// Armor recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TENGRIUM_NOMAD_HELMET.get())
                .pattern("DDD")
                .pattern("DBD")
                .pattern("   ")
                .define('B', Items.DIAMOND_HELMET)
                .define('D', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TENGRIUM_NOMAD_CHESTPLATE.get())
                .pattern("D D")
                .pattern("DBD")
                .pattern("DDD")
                .define('B', Items.DIAMOND_CHESTPLATE)
                .define('D', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TENGRIUM_NOMAD_LEGGINGS.get())
                .pattern("DDD")
                .pattern("DBD")
                .pattern("D D")
                .define('B', Items.DIAMOND_LEGGINGS)
                .define('D', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TENGRIUM_NOMAD_BOOTS.get())
                .pattern("D D")
                .pattern("DBD")
                .pattern("   ")
                .define('B', Items.DIAMOND_BOOTS)
                .define('D', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);
        List<ItemLike> TENGRIUM_SMELTABLES = List.of(ModBlocks.TENGRIUM_ORE.get(), ModBlocks.TENGRIUM_DEEPSLATE_ORE.get());
        oreSmelting(pRecipeOutput, TENGRIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TENGRIUM.get(), 0.25f, 200, "churkakrite" );
        oreBlasting(pRecipeOutput, TENGRIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TENGRIUM.get(), 0.25f, 100, "churkakrite" );

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TENGRIUM.get(), 9)
                .requires(ModBlocks.TENGRIUM_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.TENGRIUM_BLOCK.get()), has(ModBlocks.TENGRIUM_BLOCK.get())).save(pRecipeOutput);
    }

}
