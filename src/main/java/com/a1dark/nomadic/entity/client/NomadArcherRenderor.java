package com.a1dark.nomadic.entity.client;

import com.a1dark.nomadic.entity.custom.NomadArcherEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class NomadArcherRenderor extends MobRenderer<NomadArcherEntity, NomadArcherModel<NomadArcherEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("nomadic", "textures/entity/nomad_archer.png");

    public NomadArcherRenderor(EntityRendererProvider.Context context) {
        super(context,
                new NomadArcherModel<>(context.bakeLayer(NomadArcherModel.LAYER_LOCATION)),
                0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(NomadArcherEntity entity) {
        return TEXTURE;
    }
}