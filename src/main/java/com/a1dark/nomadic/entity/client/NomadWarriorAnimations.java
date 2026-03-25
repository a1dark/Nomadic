package com.a1dark.nomadic.entity.client;


import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class NomadWarriorAnimations {
        public static final AnimationDefinition NOMAD_WARRIOR_WALK =
                AnimationDefinition.Builder.withLength(1.0417F).looping()

                        .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.0417F, KeyframeAnimations.degreeVec(3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                        ))

                        .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30F, 0F, 10F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25F, KeyframeAnimations.degreeVec(10F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(30F, 0F, -10F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1F, KeyframeAnimations.degreeVec(-30F, 0F, 10F), AnimationChannel.Interpolations.LINEAR)
                        ))

                        .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(30F, 0F, -10F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30F, 0F, 10F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1F, KeyframeAnimations.degreeVec(30F, 0F, -10F), AnimationChannel.Interpolations.LINEAR)
                        ))

                        .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1F, KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                        ))

                        .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1F, KeyframeAnimations.degreeVec(40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                        ))

                        .build();
        public static final AnimationDefinition NOMAD_WARRIOR_ATTACK =
                AnimationDefinition.Builder.withLength(0.8333F)

                        .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0F, 20F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10F, 35F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(5F, 15F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                        ))

                        .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25F, 20F, 20F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25F, KeyframeAnimations.degreeVec(-80F, 40F, 30F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(-60F, 10F, 10F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-25F, 20F, 20F), AnimationChannel.Interpolations.LINEAR)
                        ))

                        .addAnimation("hat", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.25F, KeyframeAnimations.degreeVec(-3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(1F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                        ))

                        .build();
}
