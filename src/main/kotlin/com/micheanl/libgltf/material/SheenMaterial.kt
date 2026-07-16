package com.micheanl.libgltf.material

data class SheenMaterial(
    val colorFactor: FloatArray,
    val colorTexture: TextureBinding?,
    val roughnessFactor: Float,
    val roughnessTexture: TextureBinding?
)
