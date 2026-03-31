package com.a1dark.nomadic.entity.client;

import com.a1dark.nomadic.entity.custom.TengriumSpiritEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.minecraft.resources.ResourceLocation;

public class TengriumSpiritRenderor extends MobRenderer<TengriumSpiritEntity, TengriumSpiritModel<TengriumSpiritEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("nomadic", "textures/entity/tengriumspirit.png");

    public TengriumSpiritRenderor(EntityRendererProvider.Context context) {
        super(context,
                new TengriumSpiritModel<>(context.bakeLayer(TengriumSpiritModel.LAYER_LOCATION)),
                0.5f);
    }
    @Override
    public ResourceLocation getTextureLocation(TengriumSpiritEntity pEntity) {
        return TEXTURE;
    }
}