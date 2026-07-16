package com.micheanl.libgltf.integration.item

import com.micheanl.libgltf.api.GltfInstance
import net.minecraft.world.item.ItemStack

fun interface GltfItemInstanceProvider {
    fun instance(stack: ItemStack): GltfInstance?
}
