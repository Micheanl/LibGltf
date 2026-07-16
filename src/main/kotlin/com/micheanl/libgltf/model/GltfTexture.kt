package com.micheanl.libgltf.model

import com.micheanl.libgltf.material.TextureSampler

data class GltfTexture(
    val imageIndex: Int,
    val sampler: TextureSampler
)
