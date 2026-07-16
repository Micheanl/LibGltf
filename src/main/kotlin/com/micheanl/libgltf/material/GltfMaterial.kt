package com.micheanl.libgltf.material

data class GltfMaterial(
    val name: String,
    val baseColorFactor: FloatArray,
    val baseColorTexture: TextureBinding?,
    val metallicFactor: Float,
    val roughnessFactor: Float,
    val metallicRoughnessTexture: TextureBinding?,
    val normalTexture: TextureBinding?,
    val normalScale: Float,
    val occlusionTexture: TextureBinding?,
    val occlusionStrength: Float,
    val emissiveTexture: TextureBinding?,
    val emissiveFactor: FloatArray,
    val emissiveStrength: Float,
    val alphaMode: AlphaMode,
    val alphaCutoff: Float,
    val doubleSided: Boolean,
    val unlit: Boolean,
    val specular: SpecularMaterial?,
    val clearcoat: ClearcoatMaterial?,
    val sheen: SheenMaterial?
)
