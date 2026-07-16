package com.micheanl.libgltf.model

data class GltfMesh(
    val name: String,
    val primitives: Array<GltfPrimitive>,
    val weights: FloatArray
)
