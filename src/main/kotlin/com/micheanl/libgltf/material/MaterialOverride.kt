package com.micheanl.libgltf.material

import net.minecraft.resources.Identifier

data class MaterialOverride(
    val baseColorFactor: FloatArray? = null,
    val baseColorTextureIndex: Int = -1,
    val baseColorIdentifier: Identifier? = null,
    val alphaCutoff: Float = Float.NaN
)
