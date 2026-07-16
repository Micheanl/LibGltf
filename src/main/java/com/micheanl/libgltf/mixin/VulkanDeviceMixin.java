package com.micheanl.libgltf.mixin;

import com.micheanl.libgltf.render.gpu.VertexAttributeLimitProvider;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vulkan.VulkanDevice;
import com.mojang.blaze3d.vulkan.VulkanInstance;
import com.mojang.blaze3d.vulkan.VulkanPhysicalDevice;
import com.mojang.blaze3d.vulkan.checkpoints.CheckpointExtension;
import java.util.Set;
import org.lwjgl.vulkan.VkDevice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VulkanDevice.class)
public abstract class VulkanDeviceMixin implements VertexAttributeLimitProvider {
    @Unique
    private int libgltf$maxVertexAttributes = VertexFormat.MAX_VERTEX_ELEMENTS;

    @Inject(method = "<init>", at = @At("RETURN"), require = 1)
    private void libgltf$captureVertexAttributeLimit(
            ShaderSource defaultShaderSource,
            VulkanInstance instance,
            VulkanPhysicalDevice physicalDevice,
            Set<String> enabledDeviceExtensions,
            VkDevice vkDevice,
            long vma,
            CheckpointExtension checkpointExtension,
            CallbackInfo callback
    ) {
        libgltf$maxVertexAttributes = physicalDevice.vkPhysicalDeviceProperties().limits().maxVertexInputAttributes();
    }

    @Override
    public int getMaxVertexAttributes() {
        return libgltf$maxVertexAttributes;
    }
}
