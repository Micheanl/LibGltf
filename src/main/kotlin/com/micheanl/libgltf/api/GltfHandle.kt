package com.micheanl.libgltf.api

import com.micheanl.libgltf.model.GltfAsset
import com.micheanl.libgltf.render.GltfRenderSystem
import java.util.concurrent.atomic.AtomicBoolean

class GltfHandle internal constructor(
    val asset: GltfAsset,
    internal val resourceId: Long
) : AutoCloseable {
    private val closed = AtomicBoolean()

    val isClosed: Boolean
        get() = closed.get()

    override fun close() {
        if (closed.compareAndSet(false, true)) GltfRenderSystem.release(resourceId)
    }
}
