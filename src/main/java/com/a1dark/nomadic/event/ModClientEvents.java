package com.a1dark.nomadic.event;

import com.a1dark.nomadic.Nomadic;

import com.a1dark.nomadic.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nomadic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event) {
        if (event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.NOMAD_BOW.get()) {
            float fovModifier = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float) ticksUsingItem / 20f;
            if (deltaTicks > 1f) {
                deltaTicks = 1f;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifier *= 1f - deltaTicks * 0.15f;
            event.setNewFovModifier(fovModifier);
        }
    }
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {

        if (event.getItemStack().getItem() == ModItems.NOMAD_BOW.get()) {
            if(Screen.hasShiftDown()) {
                event.getToolTip().add(
                        Component.translatable("tooltip.nomadic.nomad_bow.tooltip.shift_down"));
            } else {
                event.getToolTip().add(
                        Component.translatable("tooltip.nomadic.nomad_bow.tooltip"));
            }
        }
        if (event.getItemStack().getItem() == (ModItems.SPIRIT_SHARD.get()))
            event.getToolTip().add(Component.translatable("tooltip.nomadic.spirit_shard.tooltip"));
        if (event.getItemStack().getItem() == (ModItems.SPIRIT.get()))
            event.getToolTip().add(Component.translatable("tooltip.nomadic.spirit.tooltip"));
        if (event.getItemStack().getItem() == (ModItems.TENGRIUM_NOMAD_HELMET.get()))
                event.getToolTip().add(Component.translatable("tooltip.nomadic.tengrium_nomad_armor.tooltip"));
        if (event.getItemStack().getItem() == (ModItems.TENGRIUM_NOMAD_CHESTPLATE.get()))
            event.getToolTip().add(Component.translatable("tooltip.nomadic.tengrium_nomad_armor.tooltip"));
        if (event.getItemStack().getItem() == (ModItems.TENGRIUM_NOMAD_LEGGINGS.get()))
            event.getToolTip().add(Component.translatable("tooltip.nomadic.tengrium_nomad_armor.tooltip"));
        if (event.getItemStack().getItem() == (ModItems.TENGRIUM_NOMAD_BOOTS.get()))
            event.getToolTip().add(Component.translatable("tooltip.nomadic.tengrium_nomad_armor.tooltip"));
        if (event.getItemStack().getItem() == ModItems.TENGRIUM_NOMAD_SABER.get()) {
            if(Screen.hasShiftDown()) {
                event.getToolTip().add(
                        Component.translatable("tooltip.nomadic.tengrium_nomad_saber.tooltip.shift_down"));
            } else {
                event.getToolTip().add(
                        Component.translatable("tooltip.nomadic.tengrium_nomad_saber.tooltip"));
            }
        }
    }
}
