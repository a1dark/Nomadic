package com.a1dark.nomadic.entity.client;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.entity.custom.NomadWarriorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class NomadWarriorModel<T extends NomadWarriorEntity>
        extends EntityModel<T> implements ArmedModel {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Nomadic.MOD_ID, "nomad_warrior"),
                    "main"
            );

    private final ModelPart root;
    private final ModelPart waist;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public NomadWarriorModel(ModelPart root) {
        this.root = root;
        this.waist = root.getChild("waist");
        this.body = waist.getChild("body");
        this.head = body.getChild("head");
        this.rightArm = body.getChild("rightArm");
        this.leftArm = body.getChild("leftArm");
        this.rightLeg = body.getChild("rightLeg");
        this.leftLeg = body.getChild("leftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition waist = partdefinition.addOrReplaceChild("waist",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition body = waist.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 32)
                        .addBox(-4.0F, -3.0F, -2.0F, 8.0F, 15.0F, 4.0F),
                PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -11.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("rightArm",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 15.0F, 4.0F),
                PartPose.offset(-6.0F, -2.0F, 0.0F));

        body.addOrReplaceChild("leftArm",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .mirror()
                        .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 15.0F, 4.0F)
                        .mirror(false),
                PartPose.offset(6.0F, -2.0F, 0.0F));

        body.addOrReplaceChild("rightLeg",
                CubeListBuilder.create()
                        .texOffs(32, 19)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-2.0F, 12.0F, 0.0F));

        body.addOrReplaceChild("leftLeg",
                CubeListBuilder.create()
                        .texOffs(32, 19)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F)
                        .mirror(false),
                PartPose.offset(2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = Mth.clamp(netHeadYaw, -30.0F, 30.0F) * ((float)Math.PI / 180F);
        this.head.xRot = Mth.clamp(headPitch, -25.0F, 45.0F) * ((float)Math.PI / 180F);
        float armSwing = 1.4F * limbSwingAmount;
        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F) * armSwing;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * armSwing;
        float legSwing = 1.4F * limbSwingAmount;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * legSwing;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * legSwing;

        if (entity.isAggressive()) {
            float attackAnim = entity.getAttackAnim(0.0F); // вместо this.attackTime
            AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, entity, attackAnim, ageInTicks);
        }
    }
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
        poseStack.translate(0.0D, 0.33D, 0.0D);
    }
}