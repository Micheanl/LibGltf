package com.micheanl.libgltf.render.feature

import net.minecraft.client.renderer.rendertype.RenderType

data class GltfGpuBatchKey(
    val resourceId: Long,
    val meshIndex: Int,
    val primitiveIndex: Int,
    val lod: Int,
    val skinIndex: Int,
    val renderType: RenderType
)
