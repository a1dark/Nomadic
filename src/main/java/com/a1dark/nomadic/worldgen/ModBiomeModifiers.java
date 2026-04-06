package com.a1dark.nomadic.worldgen;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_TENGRIUM_ORE = registerKey("add_tengrium_ore");
    public static final ResourceKey<BiomeModifier> SPAWN_NOMAD_WARRIOR= registerKey("spawn_nomad_warrior");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_TENGRIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.TENGRIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(SPAWN_NOMAD_WARRIOR, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SAVANNA), biomes.getOrThrow(Biomes.DESERT), biomes.getOrThrow(Biomes.FOREST)),
                List.of(
                        new MobSpawnSettings.SpawnerData(ModEntities.NOMAD_WARRIOR.get(), 85, 2, 4),
                        new MobSpawnSettings.SpawnerData(ModEntities.NOMAD_ARCHER.get(), 70, 2, 5)
                )));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Nomadic.MOD_ID, name));
    }
}
