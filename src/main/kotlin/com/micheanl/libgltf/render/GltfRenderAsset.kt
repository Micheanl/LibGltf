package com.micheanl.libgltf.render

import com.micheanl.libgltf.model.GltfAsset
import com.micheanl.libgltf.render.gpu.GltfGpuResources
import com.micheanl.libgltf.render.texture.GltfTextureFactory
import com.micheanl.libgltf.render.texture.GltfTextureSet

class GltfRenderAsset(
    val id: Long,
    val asset: GltfAsset
) : AutoCloseable {
    @Volatile
    private var textureSet: GltfTextureSet? = null
    private var gpuResources: GltfGpuResources? = null

    fun textures(): GltfTextureSet {
        var textures = textureSet
        if (textures == null) {
            textures = GltfTextureFactory.create(asset, id)
            textureSet = textures
        }
        return textures
    }

    fun gpu(): GltfGpuResources {
        var resources = gpuResources
        if (resources == null) {
            resources = GltfGpuResources(id, asset)
            gpuResources = resources
        }
        return resources
    }

    override fun close() {
        gpuResources?.close()
        gpuResources = null
        textureSet?.close()
        textureSet = null
    }
}
