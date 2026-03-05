package com.a1dark.nomadic.entity.client;
import com.a1dark.nomadic.Nomadic;
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
        this.addLayer(new ItemInHandLayer<NomadWarriorEntity, NomadWarriorModel<NomadWarriorEntity>>(
                this,
                context.getItemInHandRenderer()
        ));
    }
    @Override
    public ResourceLocation getTextureLocation(NomadWarriorEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Nomadic.MOD_ID, "textures/entity/nomadwarrior.png");
    }
}
