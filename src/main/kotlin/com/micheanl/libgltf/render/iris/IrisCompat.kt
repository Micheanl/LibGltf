package com.micheanl.libgltf.render.iris

import com.micheanl.libgltf.material.AlphaMode
import com.mojang.blaze3d.pipeline.RenderPipeline
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.renderer.texture.AbstractTexture

object IrisCompat {
    private val loaded: Boolean = FabricLoader.getInstance().isModLoaded("iris")

    fun available(): Boolean = loaded

    fun shaderPackActive(): Boolean = loaded && IrisInterop.shaderPackActive()

    fun copyEntity(pipeline: RenderPipeline, alphaMode: AlphaMode) {
        if (loaded) IrisInterop.copyEntity(pipeline, alphaMode)
    }

    fun registerPbr(albedo: AbstractTexture, normal: AbstractTexture, specular: AbstractTexture) {
        if (loaded) IrisInterop.registerPbr(albedo, normal, specular)
    }

    fun unregisterPbr(albedo: AbstractTexture) {
        if (loaded) IrisInterop.unregisterPbr(albedo)
    }
}
