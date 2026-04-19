package com.a1dark.nomadic.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties CHINUTE = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(1.2F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 3), 1.0F)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400, 2), 1.0F)
            .alwaysEdible().build();
    public static final FoodProperties KYMUZ = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(1.2F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1600, 2), 1.0F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 2), 1.0F)
            .alwaysEdible().build();
    public static final FoodProperties DRIED_BEEF = new FoodProperties.Builder()
            .nutrition(7)
            .saturationModifier(1.2F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 5.0F)
            .alwaysEdible().build();
    public static final FoodProperties DRIED_PORKCHOP = new FoodProperties.Builder()
            .nutrition(7)
            .saturationModifier(1.2F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 5.0F)
            .alwaysEdible().build();
    public static final FoodProperties DRIED_MUTTON = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(1.2F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 5.0F)
            .alwaysEdible().build();
    public static final FoodProperties DRIED_CHICKEN = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(1.2F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 5.0F)
            .alwaysEdible().build();
    public static final FoodProperties DRIED_RABBIT = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(1.2F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 8000, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 5.0F)
            .alwaysEdible().build();
}
