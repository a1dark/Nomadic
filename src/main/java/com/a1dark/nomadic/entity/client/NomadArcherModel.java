package com.a1dark.nomadic.entity.client;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.entity.custom.NomadArcherEntity;
import com.a1dark.nomadic.entity.custom.NomadWarriorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.client.model.AnimationUtils;

public class NomadArcherModel<T extends NomadArcherEntity>
        extends HierarchicalModel<T> implements ArmedModel {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Nomadic.MOD_ID, "nomad_archer"),
                    "main"
            );

    private final ModelPart root;
    private final ModelPart waist;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart hat;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public NomadArcherModel(ModelPart root) {
        this.root = root.getChild("waist");
        this.waist = this.root;
        this.body = waist.getChild("body");
        this.head = body.getChild("head");
        this.hat = head.getChild("hat");
        this.rightArm = body.getChild("rightArm");
        this.leftArm = body.getChild("leftArm");
        this.rightLeg = body.getChild("rightLeg");
        this.leftLeg = body.getChild("leftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition waist = partdefinition.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = waist.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.9F, -3.0F, 8.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 21).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(28, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
                .texOffs(46, 46).addBox(0.0F, -15.0F, -1.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(46, 35).addBox(-5.0F, -10.0F, -5.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(46, 35).addBox(4.0F, -10.0F, -5.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = hat.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(46, 35).addBox(-1.0F, -16.0F, -8.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 6.0F, 5.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r2 = hat.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(46, 35).addBox(-1.0F, -16.0F, -8.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 6.0F, -4.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 37).addBox(-1.0F, -0.75F, -3.4749F, 3.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 16).addBox(-2.0F, -1.0F, -4.0F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -5.15F, 1.4749F));

        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 37).addBox(-2.0F, -0.75F, -2.5F, 3.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -5.15F, 0.5F));

        PartDefinition cube_r3 = leftArm.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(48, 25).addBox(-2.0F, -2.0F, -4.0F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0251F, 0.0F, 3.1416F, 0.0F));

        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(32, 16).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 50).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 37).addBox(-3.1F, -3.25F, -2.5251F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(32, 16).mirror().addBox(-2.0F, -3.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(14, 50).addBox(-1.8F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition cube_r4 = leftLeg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 37).addBox(-3.0F, -5.0F, -4.0F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 1.75F, -1.5251F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);

        this.animateWalk(
                NomadArcherAnimations.NOMAD_WARRIOR_WALK,
                limbSwing, limbSwingAmount,
                2f, 2.5f
        );

        if (entity.isUsingItem()) {
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
        }
    }
    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    public ModelPart getArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }


    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        this.getArm(arm).translateAndRotate(poseStack);
        poseStack.translate(0.0D, 0.3D, -0.133D);
    }
}