package com.micheanl.libgltf.render.texture

import com.micheanl.libgltf.material.TextureFilter

object MipmapFilterState {
    private val active = ThreadLocal.withInitial { TextureFilter.LINEAR }

    @JvmStatic
    fun nearest(): Boolean = active.get() == TextureFilter.NEAREST

    fun <T> create(filter: TextureFilter, factory: () -> T): T {
        val previous = active.get()
        active.set(filter)
        return try {
            factory()
        } finally {
            active.set(previous)
        }
    }
}
