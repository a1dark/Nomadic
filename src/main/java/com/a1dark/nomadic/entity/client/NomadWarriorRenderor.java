package com.a1dark.nomadic.entity.client;

import com.a1dark.nomadic.entity.custom.NomadWarriorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class NomadWarriorRenderor extends MobRenderer<NomadWarriorEntity, NomadWarriorModel<NomadWarriorEntity>> {

    public NomadWarriorRenderor(EntityRendererProvider.Context context) {
        super(context,
                new NomadWarriorModel<>(context.bakeLayer(NomadWarriorModel.LAYER_LOCATION)),
                0.5f);

        this.addLayer(new ItemInHandLayer<>(
                this,
                context.getItemInHandRenderer()
        ));
    }
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            ResourceLocation.fromNamespaceAndPath(
                    "nomadic",
                    "textures/entity/nomadwarrior.png"),

            ResourceLocation.fromNamespaceAndPath(
                    "nomadic",
                    "textures/entity/nomadarcher.png")
    };

    @Override
    public ResourceLocation getTextureLocation(NomadWarriorEntity entity) {
        return TEXTURES[entity.getVariant().getId()];
    }

}