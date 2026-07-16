package com.micheanl.libgltf.animation

data class AnimationState(
    val name: String,
    val segment: AnimationSegment,
    val looping: Boolean = true,
    val speed: Float = 1.0f
)
