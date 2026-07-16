package com.micheanl.libgltf.model

data class GltfStats(
    val nodeCount: Int,
    val meshCount: Int,
    val primitiveCount: Int,
    val triangleCount: Long,
    val materialCount: Int,
    val textureCount: Int,
    val skinCount: Int,
    val morphTargetCount: Int
)
