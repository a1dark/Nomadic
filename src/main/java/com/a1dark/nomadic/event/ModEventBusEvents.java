package com.a1dark.nomadic.event;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.entity.ModEntities;
import com.a1dark.nomadic.entity.client.NomadArcherModel;
import com.a1dark.nomadic.entity.client.NomadWarriorModel;
import com.a1dark.nomadic.entity.client.TengriumSpiritModel;
import com.a1dark.nomadic.entity.custom.NomadArcherEntity;
import com.a1dark.nomadic.entity.custom.NomadWarriorEntity;
import com.a1dark.nomadic.entity.custom.TengriumSpiritEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nomadic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){

    event.registerLayerDefinition(NomadWarriorModel.LAYER_LOCATION, NomadWarriorModel::createBodyLayer);
        event.registerLayerDefinition(NomadArcherModel.LAYER_LOCATION, NomadArcherModel::createBodyLayer);
        event.registerLayerDefinition(TengriumSpiritModel.LAYER_LOCATION, TengriumSpiritModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){

        event.put(ModEntities.NOMAD_WARRIOR.get(), NomadWarriorEntity.createAttributes().build());
        event.put(ModEntities.NOMAD_ARCHER.get(), NomadArcherEntity.createAttributes().build());
        event.put(ModEntities.TENGRIUM_SPIRIT.get(), TengriumSpiritEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.NOMAD_WARRIOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NomadWarriorEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.NOMAD_ARCHER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NomadArcherEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

}
