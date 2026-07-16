package com.micheanl.libgltf.render.vulkan

import com.mojang.blaze3d.buffers.GpuBufferSlice

interface VulkanIndirectRenderPass {
    fun drawIndexedIndirectCount(commands: GpuBufferSlice, count: GpuBufferSlice, maxDrawCount: Int)
}