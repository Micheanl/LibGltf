package com.micheanl.libgltf.material

data class ClearcoatMaterial(
    val factor: Float,
    val texture: TextureBinding?,
    val roughnessFactor: Float,
    val roughnessTexture: TextureBinding?,
    val normalTexture: TextureBinding?,
    val normalScale: Float
)
