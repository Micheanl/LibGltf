package com.micheanl.libgltf.render.gpu

import com.micheanl.libgltf.mixin.GpuDeviceBackendAccessor
import com.micheanl.libgltf.render.GltfGpuBackendType
import com.micheanl.libgltf.render.GltfGpuCapabilities
import com.micheanl.libgltf.render.GltfGpuPath
import com.micheanl.libgltf.render.vulkan.GltfGpuDrivenSettings
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vulkan.VulkanDevice
import org.lwjgl.opengl.GL

object GltfGpuBackend {
    @Volatile
    private var vertexAttributeLimit = VertexFormat.MAX_VERTEX_ELEMENTS

    @Volatile
    private var capabilities = GltfGpuCapabilities(
        GltfGpuBackendType.UNKNOWN,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        GltfGpuPath.CPU
    )

    fun refresh() {
        val device = RenderSystem.tryGetDevice() ?: return
        val info = device.deviceInfo
        val backend = when (info.backendName()) {
            "OpenGL" -> GltfGpuBackendType.OPENGL
            "Vulkan" -> GltfGpuBackendType.VULKAN
            else -> GltfGpuBackendType.UNKNOWN
        }
        val meshShader = when (backend) {
            GltfGpuBackendType.OPENGL -> {
                val gl = GL.getCapabilities()
                gl.GL_EXT_mesh_shader || gl.GL_NV_mesh_shader
            }
            GltfGpuBackendType.VULKAN -> info.underlyingExtensions().any { it.startsWith("VK_EXT_mesh_shader") }
            GltfGpuBackendType.UNKNOWN -> false
        }
        val features = info.features()
        val limit = ((device as GpuDeviceBackendAccessor).libgltfBackend as? VertexAttributeLimitProvider)
            ?.maxVertexAttributes
            ?: VertexFormat.MAX_VERTEX_ELEMENTS
        val instancing = backend != GltfGpuBackendType.UNKNOWN &&
            GltfGpuFormats.REQUIRED_VERTEX_ATTRIBUTES <= limit
        val nativeMeshShader = meshShader &&
            backend == GltfGpuBackendType.VULKAN &&
            GltfGpuDrivenSettings.meshShader &&
            ((device as GpuDeviceBackendAccessor).libgltfBackend as? VulkanDevice)?.vkDevice()?.capabilities?.VK_EXT_mesh_shader == true
        vertexAttributeLimit = limit
        capabilities = GltfGpuCapabilities(
            backend,
            instancing,
            features.shaderDrawParameters(),
            features.multiDrawDirectInterleaved(),
            features.multiDrawDirectSeparate(),
            features.drawIndirect(),
            features.multiDrawIndirect(),
            features.nonZeroFirstInstance(),
            features.persistentMapping(),
            meshShader,
            false,
            if (instancing) GltfGpuPath.INSTANCED else GltfGpuPath.CPU
        )
    }

    fun vertexAttributeLimit(): Int = vertexAttributeLimit

    fun capabilities(): GltfGpuCapabilities = capabilities
}
