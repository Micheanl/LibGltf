package com.micheanl.libgltf.material

data class MaterialOverride(
    val baseColorFactor: FloatArray? = null,
    val baseColorTextureIndex: Int = -1,
    val alphaCutoff: Float = Float.NaN
)
