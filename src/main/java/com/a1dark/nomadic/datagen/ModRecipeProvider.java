package com.a1dark.nomadic.datagen;

import com.a1dark.nomadic.item.ModItems;
import com.a1dark.nomadic.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        //Drief Food
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DRIED_BEEF.get())
                .pattern(" AA")
                .pattern(" BA")
                .pattern("   ")
                .define('A', ModItems.SALT.get())
                .define('B',Items.BEEF)
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DRIED_PORKCHOP.get())
                .pattern(" AA")
                .pattern(" BA")
                .pattern("   ")
                .define('A', ModItems.SALT.get())
                .define('B',Items.PORKCHOP)
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DRIED_MUTTON.get())
                .pattern(" AA")
                .pattern(" BA")
                .pattern("   ")
                .define('A', ModItems.SALT.get())
                .define('B',Items.MUTTON)
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DRIED_CHICKEN.get())
                .pattern(" AA")
                .pattern(" BA")
                .pattern("   ")
                .define('A', ModItems.SALT.get())
                .define('B',Items.CHICKEN)
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DRIED_RABBIT.get())
                .pattern(" AA")
                .pattern(" BA")
                .pattern("   ")
                .define('A', ModItems.SALT.get())
                .define('B',Items.RABBIT)
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get())).save(pRecipeOutput);
        //Chinute
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CHINUTE.get())
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A', Items.APPLE)
                .define('B', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(Items.APPLE), has(Items.APPLE)).save(pRecipeOutput);
        //Kymuz
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KYMUZ.get())
                .pattern("   ")
                .pattern("BAB")
                .pattern("BBB")
                .define('A', Items.MILK_BUCKET)
                .define('B', ModItems.SALT.get())
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get())).save(pRecipeOutput);

        //Block
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TENGRIUM_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SPIRIT_BEACON.get())
                .pattern(" A ")
                .pattern("AAA")
                .pattern("BBB")
                .define('A', ModItems.SPIRIT_SHARD.get())
                .define('B', Items.SOUL_SAND)
                .unlockedBy(getHasName(ModItems.SPIRIT_SHARD.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);
        //Shyrdak
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BLUE_SHYRDAK.get())
                .pattern("   ")
                .pattern("A  ")
                .pattern("BB ")
                .define('A', Items.LIGHT_BLUE_DYE)
                .define('B', Items.BLUE_WOOL)
                .unlockedBy(getHasName(Items.BLUE_WOOL), has(Items.BLUE_WOOL)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GREEN_SHYRDAK.get())
                .pattern("   ")
                .pattern("A  ")
                .pattern("BB ")
                .define('A', Items.RED_DYE)
                .define('B', Items.GREEN_WOOL)
                .unlockedBy(getHasName(Items.GREEN_WOOL), has(Items.GREEN_WOOL)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WHITE_SHYRDAK.get())
                .pattern("   ")
                .pattern("A  ")
                .pattern("BB ")
                .define('A', Items.RED_DYE)
                .define('B', Items.WHITE_WOOL)
                .unlockedBy(getHasName(Items.WHITE_WOOL), has(Items.WHITE_WOOL)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RED_SHYRDAK.get())
                .pattern("   ")
                .pattern("A  ")
                .pattern("BB ")
                .define('A', Items.GREEN_DYE)
                .define('B', Items.RED_WOOL)
                .unlockedBy(getHasName(Items.RED_WOOL), has(Items.RED_WOOL)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ORANGE_SHYRDAK.get())
                .pattern("   ")
                .pattern("A  ")
                .pattern("BB ")
                .define('A', Items.BLUE_DYE)
                .define('B', Items.ORANGE_WOOL)
                .unlockedBy(getHasName(Items.ORANGE_WOOL), has(Items.ORANGE_WOOL)).save(pRecipeOutput);
        //Tengrium Campfire
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TENGRIUM_CAMPFIRE.get())
                .pattern("   ")
                .pattern("BB ")
                .pattern("AB ")
                .define('A', Items.CAMPFIRE)
                .define('B', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);


        //Sabers
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TENGRIUM_NOMAD_SABER.get())
                .pattern("  D")
                .pattern("CDD")
                .pattern("AB ")
                .define('A', Items.STICK)
                .define('B', ModItems.SPIRIT.get())
                .define('C', Items.FEATHER)
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_NOMAD_HELMET.get())
                .pattern(" C ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', Items.IRON_HELMET)
                .define('C', Blocks.RED_WOOL)
                .define('B', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_NOMAD_CHESTPLATE.get())
                .pattern(" C ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', Items.IRON_CHESTPLATE)
                .define('C', Blocks.RED_WOOL)
                .define('B', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_NOMAD_LEGGINGS.get())
                .pattern(" C ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', Items.IRON_LEGGINGS)
                .define('C', Blocks.RED_WOOL)
                .define('B', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_NOMAD_BOOTS.get())
                .pattern(" C ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', Items.IRON_BOOTS)
                .define('C', Blocks.RED_WOOL)
                .define('B', ModItems.TENGRIUM.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(pRecipeOutput);

        //Bow
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NOMAD_BOW.get())
                .pattern(" AA")
                .pattern(" BA")
                .pattern("   ")
                .define('A', ModItems.TENGRIUM.get())
                .define('B',Items.BOW)
                .unlockedBy(getHasName(ModItems.TENGRIUM.get()), has(ModItems.TENGRIUM.get())).save(pRecipeOutput);
        //Smelting and Blasting of Tengrium
        List<ItemLike> TENGRIUM_SMELTABLES = List.of(ModBlocks.TENGRIUM_ORE.get(), ModBlocks.TENGRIUM_DEEPSLATE_ORE.get());
        oreSmelting(pRecipeOutput, TENGRIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TENGRIUM.get(), 0.25f, 200, "churkakrite" );
        oreBlasting(pRecipeOutput, TENGRIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TENGRIUM.get(), 0.25f, 100, "churkakrite" );

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TENGRIUM.get(), 9)
                .requires(ModBlocks.TENGRIUM_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.TENGRIUM_BLOCK.get()), has(ModBlocks.TENGRIUM_BLOCK.get())).save(pRecipeOutput);
    }

}
