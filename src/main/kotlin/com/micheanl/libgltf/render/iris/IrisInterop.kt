package com.micheanl.libgltf.render.iris

import com.micheanl.libgltf.material.AlphaMode
import com.mojang.blaze3d.opengl.GlTexture
import com.mojang.blaze3d.pipeline.RenderPipeline
import net.irisshaders.iris.api.v0.IrisApi
import net.irisshaders.iris.pipeline.IrisPipelines
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.client.renderer.texture.AbstractTexture

object IrisInterop {
    fun shaderPackActive(): Boolean = IrisApi.getInstance().isShaderPackInUse

    fun copyEntity(pipeline: RenderPipeline, alphaMode: AlphaMode) {
        val source = when (alphaMode) {
            AlphaMode.OPAQUE -> RenderPipelines.ENTITY_SOLID
            AlphaMode.MASK -> RenderPipelines.ENTITY_CUTOUT
            AlphaMode.BLEND -> RenderPipelines.ENTITY_TRANSLUCENT
        }
        IrisPipelines.copyPipeline(source, pipeline)
    }

    fun registerPbr(albedo: AbstractTexture, normal: AbstractTexture, specular: AbstractTexture) {
        val texture = albedo.texture
        if (texture is GlTexture) IrisPbrTextures.register(texture.glId(), normal, specular)
    }

    fun unregisterPbr(albedo: AbstractTexture) {
        val texture = albedo.texture
        if (texture is GlTexture) IrisPbrTextures.unregister(texture.glId())
    }
}
