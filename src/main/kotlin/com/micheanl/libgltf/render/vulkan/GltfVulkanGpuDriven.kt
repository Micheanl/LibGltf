package com.micheanl.libgltf.render.vulkan

import com.micheanl.libgltf.mixin.GpuDeviceBackendAccessor
import com.micheanl.libgltf.render.GltfGpuBackendType
import com.micheanl.libgltf.render.gpu.GltfGpuBackend
import com.mojang.blaze3d.systems.GpuDevice
import com.mojang.blaze3d.vulkan.VulkanDevice

class GltfVulkanGpuDriven private constructor(
    val pipeline: GltfVulkanComputePipeline,
    val meshPipelines: GltfVulkanMeshPipelineCache?
) : AutoCloseable {
    override fun close() {
        meshPipelines?.close()
        pipeline.close()
    }

    companion object {
        fun create(device: GpuDevice): GltfVulkanGpuDriven? {
            val capabilities = GltfGpuBackend.capabilities()
            if (!GltfGpuDrivenSettings.enabled ||
                capabilities.backend != GltfGpuBackendType.VULKAN ||
                !capabilities.drawIndirect ||
                !capabilities.multiDrawIndirect ||
                !capabilities.nonZeroFirstInstance
            ) return null
            val backend = (device as GpuDeviceBackendAccessor).libgltfBackend as? VulkanDevice ?: return null
            val mesh = if (GltfGpuDrivenSettings.meshShader && backend.vkDevice().capabilities.VK_EXT_mesh_shader) {
                GltfVulkanMeshPipelineCache(backend).takeIf { it.supported }
            } else {
                null
            }
            return GltfVulkanGpuDriven(GltfVulkanComputePipeline.create(backend), mesh)
        }
    }
}