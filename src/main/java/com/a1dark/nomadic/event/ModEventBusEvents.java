package com.a1dark.nomadic.event;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.entity.ModEntities;
import com.a1dark.nomadic.entity.client.NomadWarriorModel;
import com.a1dark.nomadic.entity.custom.NomadWarriorEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nomadic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){

    event.registerLayerDefinition(NomadWarriorModel.LAYER_LOCATION, NomadWarriorModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.NOMAD_WARRIOR.get(), NomadWarriorEntity.createAttributes().build());
    }
}
