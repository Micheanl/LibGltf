package com.micheanl.libgltf.model

data class GltfSkin(
    val name: String,
    val joints: IntArray,
    val inverseBindMatrices: FloatArray
)
