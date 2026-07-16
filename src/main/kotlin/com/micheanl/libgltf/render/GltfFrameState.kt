package com.micheanl.libgltf.render

import com.micheanl.libgltf.api.GltfInstance

object GltfFrameState {
    @Volatile
    private var instances: Array<GltfInstance> = emptyArray()

    fun capture() {
        instances = GltfRenderRegistry.instances()
    }

    fun instances(): Array<GltfInstance> = instances
}
