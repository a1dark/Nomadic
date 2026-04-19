package com.a1dark.nomadic.util;

import com.a1dark.nomadic.Nomadic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_TENGRIUM_TOOL = createTag("needs_tengrium_tool");
        public static final TagKey<Block> INCORRECT_FOR_TENGRIUM_TOOL = createTag("incorrect_for_tengrium_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Nomadic.MOD_ID, name));
        }
    }
}
