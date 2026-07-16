package com.micheanl.libgltf.integration.entity

import com.micheanl.libgltf.api.GltfInstance
import net.minecraft.client.renderer.entity.state.EntityRenderState
import org.joml.Matrix4f

class GltfEntityRenderState : EntityRenderState() {
    var instance: GltfInstance? = null
    val transform: Matrix4f = Matrix4f()
}
