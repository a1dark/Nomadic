package com.a1dark.nomadic.entity;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.entity.custom.NomadWarriorEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES=
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Nomadic.MOD_ID);
    public static final RegistryObject<EntityType<NomadWarriorEntity>> NOMAD_WARRIOR=
            ENTITY_TYPES.register("nomad_warrior", () -> EntityType.Builder.of(NomadWarriorEntity::new, MobCategory.CREATURE)
                    .sized(1f, 3f).build("nomad_warrior"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
