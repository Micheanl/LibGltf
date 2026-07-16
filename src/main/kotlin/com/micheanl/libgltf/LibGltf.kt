package com.micheanl.libgltf

import com.micheanl.libgltf.api.GltfApi
import com.micheanl.libgltf.api.GltfApiImpl
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier

object LibGltf : ModInitializer {
    const val MOD_ID: String = "libgltf"

    @JvmField
    val api: GltfApi = GltfApiImpl

    override fun onInitialize() = Unit

    @JvmStatic
    fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)
}
