package com.micheanl.libgltf.asset

data class GltfLoadFailure(val message: String, val cause: Throwable? = null) : GltfLoadResult
