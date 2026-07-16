package com.micheanl.libgltf.animation

data class AnimationChannel(
    val nodeIndex: Int,
    val path: AnimationPath,
    val interpolation: Interpolation,
    val times: FloatArray,
    val values: FloatArray,
    val componentCount: Int
)
