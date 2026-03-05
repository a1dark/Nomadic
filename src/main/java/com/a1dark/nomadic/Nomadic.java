package com.a1dark.nomadic;

import com.a1dark.nomadic.block.ModBlocks;
import com.a1dark.nomadic.entity.ModEntities;
import com.a1dark.nomadic.entity.client.NomadWarriorRenderor;
import com.a1dark.nomadic.item.ModCreativeModeTabs;
import com.a1dark.nomadic.item.ModItems;
import com.a1dark.nomadic.sound.ModSounds;
import com.a1dark.nomadic.util.ModItemProperties;
import com.mojang.logging.LogUtils;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
@Mod(Nomadic.MOD_ID)
public class Nomadic
{
    public static final String MOD_ID = "nomadic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Nomadic()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntities.register(modEventBus);


        modEventBus.addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            ModItemProperties.addCustomItemProperties();

            EntityRenderers.register(ModEntities.NOMAD_WARRIOR.get(), NomadWarriorRenderor::new);
        }
    }
}