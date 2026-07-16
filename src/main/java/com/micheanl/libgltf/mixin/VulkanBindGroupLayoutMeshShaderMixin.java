package com.micheanl.libgltf.mixin;

import com.mojang.blaze3d.vulkan.VulkanBindGroupLayout;
import com.mojang.blaze3d.vulkan.VulkanDevice;
import org.lwjgl.vulkan.EXTMeshShader;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(VulkanBindGroupLayout.class)
public abstract class VulkanBindGroupLayoutMeshShaderMixin {
    @Redirect(
            method = "create",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/vulkan/VkDescriptorSetLayoutBinding;stageFlags(I)Lorg/lwjgl/vulkan/VkDescriptorSetLayoutBinding;"
            ),
            require = 1
    )
    private static VkDescriptorSetLayoutBinding libgltf$meshShaderStages(
            VkDescriptorSetLayoutBinding binding,
            int stages,
            VulkanDevice device,
            List<VulkanBindGroupLayout.Entry> entries,
            String name
    ) {
        if (device.vkDevice().getCapabilities().VK_EXT_mesh_shader) {
            stages |= EXTMeshShader.VK_SHADER_STAGE_TASK_BIT_EXT | EXTMeshShader.VK_SHADER_STAGE_MESH_BIT_EXT;
        }
        return binding.stageFlags(stages);
    }
}