package com.micheanl.libgltf.animation

data class AnimationClip(
    val name: String,
    val durationSeconds: Float,
    val channels: Array<AnimationChannel>
)
