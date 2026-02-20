package com.a1dark.nomadic.event;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.item.ModItems;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nomadic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onArrowLoose(LivingHurtEvent event) {
        DamageSource source = event.getSource();

        if (source.is(DamageTypeTags.IS_PROJECTILE)
                && source.getDirectEntity() instanceof AbstractArrow arrow
                && arrow.getOwner() instanceof Player player) {
            if (player.getMainHandItem().is(ModItems.NOMAD_BOW.get())
                    || player.getOffhandItem().is(ModItems.NOMAD_BOW.get())) {

                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 150, 1));
            }
        }
    }
}
