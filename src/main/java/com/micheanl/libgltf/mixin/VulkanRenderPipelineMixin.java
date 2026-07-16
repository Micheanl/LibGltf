package com.micheanl.libgltf.mixin;

import com.micheanl.libgltf.LibGltf;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vulkan.VulkanRenderPipeline;
import java.util.Arrays;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VulkanRenderPipeline.class)
public abstract class VulkanRenderPipelineMixin {
    @Redirect(
            method = "compile",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline;getVertexFormatBindings()[Lcom/mojang/blaze3d/vertex/VertexFormat;"
            ),
            require = 1
    )
    private static VertexFormat[] libgltf$resizeVertexBindings(RenderPipeline pipeline) {
        VertexFormat[] vertexBindings = pipeline.getVertexFormatBindings();
        if (!LibGltf.MOD_ID.equals(pipeline.getLocation().getNamespace())) {
            return vertexBindings;
        }

        int vertexAttributeCount = 0;
        for (VertexFormat vertexFormat : vertexBindings) {
            if (vertexFormat != null) {
                vertexAttributeCount += vertexFormat.getElements().size();
            }
        }

        return vertexAttributeCount > vertexBindings.length
                ? Arrays.copyOf(vertexBindings, vertexAttributeCount)
                : vertexBindings;
    }
}
