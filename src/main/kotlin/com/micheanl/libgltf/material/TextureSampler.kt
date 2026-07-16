package com.micheanl.libgltf.material

data class TextureSampler(
    val magnification: TextureFilter,
    val minification: TextureFilter,
    val mipmap: TextureFilter?,
    val wrapS: TextureWrap,
    val wrapT: TextureWrap
) {
    companion object {
        @JvmField
        val DEFAULT: TextureSampler = TextureSampler(
            TextureFilter.LINEAR,
            TextureFilter.LINEAR,
            TextureFilter.LINEAR,
            TextureWrap.REPEAT,
            TextureWrap.REPEAT
        )
    }
}
