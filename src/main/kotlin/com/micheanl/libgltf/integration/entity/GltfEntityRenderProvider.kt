package com.micheanl.libgltf.integration.entity

import com.micheanl.libgltf.api.GltfInstance
import net.minecraft.world.entity.Entity

interface GltfEntityRenderProvider<T : Entity> {
    fun instance(entity: T): GltfInstance?

    fun extract(entity: T, state: GltfEntityRenderState, partialTicks: Float) {
        state.transform.identity()
    }
}
