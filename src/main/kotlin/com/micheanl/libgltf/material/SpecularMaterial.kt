package com.micheanl.libgltf.material

data class SpecularMaterial(
    val factor: Float,
    val texture: TextureBinding?,
    val colorFactor: FloatArray,
    val colorTexture: TextureBinding?
)
