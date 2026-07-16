package com.micheanl.libgltf.mixin.iris;

import com.micheanl.libgltf.LibGltf;
import com.mojang.blaze3d.opengl.GlRenderPipeline;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.Collection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.mojang.blaze3d.opengl.GlCommandEncoder", priority = 1100)
public abstract class GlCommandEncoderMixin {
    @Shadow
    private RenderPipeline lastPipeline;

    @Inject(method = "trySetup", at = @At("HEAD"))
    private void libgltf$invalidateBlendState(
            @Coerce Object renderPass,
            Collection<String> dynamicUniforms,
            CallbackInfoReturnable<Boolean> callback
    ) {
        GlRenderPipeline compiled = ((GlRenderPassAccessor) renderPass).libgltf$getPipeline();
        if (compiled == null) {
            return;
        }
        RenderPipeline pipeline = compiled.info();
        ColorTargetState colorTarget = pipeline.getColorTargetState();
        if (LibGltf.MOD_ID.equals(pipeline.getLocation().getNamespace())
                && colorTarget != null
                && colorTarget.blendFunction().isPresent()) {
            lastPipeline = null;
        }
    }
}
