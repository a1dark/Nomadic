package com.a1dark.nomadic.sound;

import com.a1dark.nomadic.Nomadic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Nomadic.MOD_ID);

    public static final RegistryObject<SoundEvent> TENGRIUM_DASH = registerSoundEvent("tengrium_dash");
    public static final RegistryObject<SoundEvent> SPIRIT_THEME = registerSoundEvent("spirit_theme");

private static RegistryObject<SoundEvent> registerSoundEvent(String name){
    return SOUND_EVENTS.register(name, ()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Nomadic.MOD_ID, name)));
}

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
