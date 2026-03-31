package com.a1dark.nomadic.entity.client;// Save this class in your mod and generate all required imports

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.entity.custom.TengriumSpiritEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class TengriumSpiritModel<T extends TengriumSpiritEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Nomadic.MOD_ID, "tengrium_spirit"),
                    "main"
            );
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart wing0;
    private final ModelPart wingtip0;
    private final ModelPart cicrle;
    private final ModelPart wing1;
    private final ModelPart wingtip1;
    private final ModelPart circle2;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart tailcircle;
    private final ModelPart tailtip;

    public TengriumSpiritModel(ModelPart root) {
        this.root = root;

        this.body = root.getChild("body");
        this.wing0 = this.body.getChild("wing0");
        this.wingtip0 = this.wing0.getChild("wingtip0");
        this.cicrle = this.wingtip0.getChild("cicrle");
        this.wing1 = this.body.getChild("wing1");
        this.wingtip1 = this.wing1.getChild("wingtip1");
        this.circle2 = this.wingtip1.getChild("circle2");
        this.head = this.body.getChild("head");
        this.tail = this.body.getChild("tail");
        this.tailcircle = this.tail.getChild("tailcircle");
        this.tailtip = this.tail.getChild("tailtip");
    }
    public ModelPart root() {
        return this.root;
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, -4.0F, -16.0F, 10.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, 0.0F, 0.0F));

        PartDefinition wing0 = body.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(46, 24).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -4.0F, -16.0F, 0.0F, 0.0F, 0.0995F));

        PartDefinition wingtip0 = wing0.addOrReplaceChild("wingtip0", CubeListBuilder.create(), PartPose.offsetAndRotation(12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0995F));

        PartDefinition wing0_r1 = wingtip0.addOrReplaceChild("wing0_r1", CubeListBuilder.create().texOffs(62, 80).addBox(7.1627F, -52.8411F, -17.3931F, 4.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 52.0F, 24.0F, 0.0F, 0.7418F, 0.0F));

        PartDefinition wingtip0_r1 = wingtip0.addOrReplaceChild("wingtip0_r1", CubeListBuilder.create().texOffs(44, 60).addBox(31.3454F, -48.6144F, -12.929F, 30.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.2182F, 44.5575F, -16.5009F, 0.2624F, -1.5524F, -0.1792F));

        PartDefinition cicrle = wingtip0.addOrReplaceChild("cicrle", CubeListBuilder.create(), PartPose.offset(58.0F, 4.0F, 8.0F));

        PartDefinition tailtip_r1 = cicrle.addOrReplaceChild("tailtip_r1", CubeListBuilder.create().texOffs(72, 112).addBox(-6.0F, -51.0F, 22.0F, 18.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.092F, -0.0041F, -1.7252F));

        PartDefinition tailtip_r2 = cicrle.addOrReplaceChild("tailtip_r2", CubeListBuilder.create().texOffs(70, 112).addBox(-8.0F, -51.0F, 22.0F, 18.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-34.0F, 42.0F, -4.0F, -0.0001F, -0.0921F, -0.1979F));

        PartDefinition tailtip_r3 = cicrle.addOrReplaceChild("tailtip_r3", CubeListBuilder.create().texOffs(72, 112).addBox(-6.0F, -51.0F, 22.0F, 18.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -4.0F, 2.0F, 0.092F, -0.0041F, -1.7252F));

        PartDefinition tailtip_r4 = cicrle.addOrReplaceChild("tailtip_r4", CubeListBuilder.create().texOffs(70, 112).addBox(-8.0F, -51.0F, 22.0F, 18.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-30.0F, 62.0F, -4.0F, -0.0001F, -0.0921F, -0.1979F));

        PartDefinition wing1 = body.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(46, 24).mirror().addBox(-12.0F, 0.0F, 0.0F, 12.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, -4.0F, -16.0F, 0.0F, 0.0F, -0.0995F));

        PartDefinition wingtip1 = wing1.addOrReplaceChild("wingtip1", CubeListBuilder.create(), PartPose.offsetAndRotation(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0995F));

        PartDefinition wing0_r2 = wingtip1.addOrReplaceChild("wing0_r2", CubeListBuilder.create().texOffs(62, 80).addBox(7.1008F, -51.1589F, -17.4425F, 4.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -48.0F, 24.0F, 0.0F, 0.6981F, 3.1416F));

        PartDefinition wingtip1_r1 = wingtip1.addOrReplaceChild("wingtip1_r1", CubeListBuilder.create().texOffs(44, 60).mirror().addBox(-61.3454F, -50.2076F, -48.6282F, 30.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(34.7818F, 46.5575F, -18.5009F, -2.9188F, 1.5524F, -2.9229F));

        PartDefinition circle2 = wingtip1.addOrReplaceChild("circle2", CubeListBuilder.create(), PartPose.offset(4.0F, 66.0F, 4.0F));

        PartDefinition tailtip_r5 = circle2.addOrReplaceChild("tailtip_r5", CubeListBuilder.create().texOffs(70, 112).addBox(-8.0F, -51.0F, 22.0F, 18.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0001F, -0.0921F, -0.1979F));

        PartDefinition tailtip_r6 = circle2.addOrReplaceChild("tailtip_r6", CubeListBuilder.create().texOffs(72, 112).addBox(-6.0F, -51.0F, 22.0F, 18.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(48.0F, -66.0F, 6.0F, 0.092F, -0.0041F, -1.7252F));

        PartDefinition tailtip_r7 = circle2.addOrReplaceChild("tailtip_r7", CubeListBuilder.create().texOffs(72, 112).addBox(-6.0F, -51.0F, 22.0F, 18.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(30.0F, -62.0F, 4.0F, 0.092F, -0.0041F, -1.7252F));

        PartDefinition tailtip_r8 = circle2.addOrReplaceChild("tailtip_r8", CubeListBuilder.create().texOffs(70, 112).addBox(-8.0F, -51.0F, 22.0F, 18.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -20.0F, 0.0F, -0.0001F, -0.0921F, -0.1979F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, -14.0F, 0.2007F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(72, 4).addBox(-11.8412F, -7.7883F, -1.0412F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 2.5936F, 0.008F, -0.0146F));

        PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(38, 4).addBox(-9.8412F, -7.7883F, -5.0412F, 14.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.0F, -4.0F, 0.8483F, 0.008F, -0.0146F));

        PartDefinition head_r3 = head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -52.0F, -24.0F, 14.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 46.0F, 0.9599F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 76).addBox(-8.0F, 0.0F, 0.0F, 14.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 2.0F));

        PartDefinition tailcircle = tail.addOrReplaceChild("tailcircle", CubeListBuilder.create().texOffs(66, 112).addBox(-12.0F, -63.0F, 14.0F, 22.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(66, 112).addBox(-12.0F, -51.0F, 14.0F, 22.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(66, 112).addBox(-12.0F, -63.0F, 22.0F, 22.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(66, 112).addBox(-12.0F, -51.0F, 22.0F, 22.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 58.0F, 0.0F));

        PartDefinition tailtip_r9 = tailcircle.addOrReplaceChild("tailtip_r9", CubeListBuilder.create().texOffs(72, 112).addBox(-6.0F, -51.0F, 22.0F, 12.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(72, 112).addBox(-6.0F, -51.0F, 14.0F, 12.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(60.0F, -54.0F, 0.0F, 0.0F, 0.0F, -1.5272F));

        PartDefinition tailtip_r10 = tailcircle.addOrReplaceChild("tailtip_r10", CubeListBuilder.create().texOffs(72, 112).addBox(-6.0F, -51.0F, 22.0F, 12.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(72, 112).addBox(-6.0F, -51.0F, 14.0F, 12.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(38.0F, -54.0F, 0.0F, 0.0F, 0.0F, -1.5272F));

        PartDefinition tailtip = tail.addOrReplaceChild("tailtip", CubeListBuilder.create().texOffs(-12, 92).addBox(-8.0F, 0.0F, 0.0F, 14.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 12.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        // Idle (always)
        this.animate(entity.idleAnimationState, TengriumSpiritAnimations.idle, ageInTicks);

        // Walk
        this.animate(entity.walkAnimationState, TengriumSpiritAnimations.walk, ageInTicks);

        // Attack
        this.animate(entity.attackAnimationState, TengriumSpiritAnimations.attack_rh, ageInTicks);

        // Head tracking (additive)
        this.head.yRot += netHeadYaw * ((float) Math.PI / 90F);
        this.head.xRot += headPitch * ((float) Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}