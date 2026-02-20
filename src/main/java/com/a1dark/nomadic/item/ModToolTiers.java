package com.a1dark.nomadic.item;
import com.a1dark.nomadic.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModToolTiers {
    public static final Tier TENGRIUM = new ForgeTier(1400, 4, 3f, 20,
    ModTags.Blocks.NEEDS_TENGRIUM_TOOL, () -> Ingredient.of(ModItems.TENGRIUM.get()),
    ModTags.Blocks.INCORRECT_FOR_TENGRIUM_TOOL);
}