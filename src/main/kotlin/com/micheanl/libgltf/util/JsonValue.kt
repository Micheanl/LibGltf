package com.micheanl.libgltf.util

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement

@JvmInline
internal value class JsonValue(val element: JsonElement) {
    fun size(): Int = (element as JsonArray).size

    operator fun get(index: Int): JsonValue = JsonValue((element as JsonArray)[index])
}
