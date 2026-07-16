package com.micheanl.libgltf.mixin;

import com.micheanl.libgltf.render.vulkan.GltfGpuDrivenSettings;
import com.mojang.blaze3d.systems.BackendCreationException;
import com.mojang.blaze3d.vulkan.VulkanBackend;
import com.mojang.blaze3d.vulkan.VulkanPhysicalDevice;
import com.mojang.blaze3d.vulkan.init.VulkanFeature;
import com.mojang.blaze3d.vulkan.init.VulkanPNextStruct;
import java.util.Collection;
import java.util.Set;
import org.lwjgl.vulkan.EXTMeshShader;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceMeshShaderFeaturesEXT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VulkanBackend.class)
public abstract class VulkanBackendMeshShaderMixin {
    private static final VulkanPNextStruct LIBGLTF_MESH_FEATURES = new VulkanPNextStruct(
            EXTMeshShader.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_MESH_SHADER_FEATURES_EXT,
            VkPhysicalDeviceMeshShaderFeaturesEXT.SIZEOF
    );
    private static final VulkanFeature LIBGLTF_TASK_SHADER = new VulkanFeature(
            LIBGLTF_MESH_FEATURES,
            "taskShader",
            VkPhysicalDeviceMeshShaderFeaturesEXT.TASKSHADER
    );
    private static final VulkanFeature LIBGLTF_MESH_SHADER = new VulkanFeature(
            LIBGLTF_MESH_FEATURES,
            "meshShader",
            VkPhysicalDeviceMeshShaderFeaturesEXT.MESHSHADER
    );

    @Shadow
    private static boolean isFeatureSupported(VkPhysicalDevice device, VulkanFeature feature) {
        throw new AssertionError();
    }

    @Shadow
    private static VkDevice createDevice(
            Collection<String> extensions,
            VulkanPhysicalDevice physicalDevice,
            Set<VulkanFeature> features
    ) throws BackendCreationException {
        throw new AssertionError();
    }

    @Redirect(
            method = "createDevice",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vulkan/VulkanBackend;createDevice(Ljava/util/Collection;Lcom/mojang/blaze3d/vulkan/VulkanPhysicalDevice;Ljava/util/Set;)Lorg/lwjgl/vulkan/VkDevice;"
            ),
            require = 1
    )
    private VkDevice libgltf$enableMeshShader(
            Collection<String> extensions,
            VulkanPhysicalDevice physicalDevice,
            Set<VulkanFeature> features
    ) throws BackendCreationException {
        if (GltfGpuDrivenSettings.INSTANCE.getMeshShader()
                && physicalDevice.hasDeviceExtension(EXTMeshShader.VK_EXT_MESH_SHADER_EXTENSION_NAME)
                && isFeatureSupported(physicalDevice.vkPhysicalDevice(), LIBGLTF_TASK_SHADER)
                && isFeatureSupported(physicalDevice.vkPhysicalDevice(), LIBGLTF_MESH_SHADER)) {
            extensions.add(EXTMeshShader.VK_EXT_MESH_SHADER_EXTENSION_NAME);
            features.add(LIBGLTF_TASK_SHADER);
            features.add(LIBGLTF_MESH_SHADER);
        }
        return createDevice(extensions, physicalDevice, features);
    }
}