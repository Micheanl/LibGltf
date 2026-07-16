package com.micheanl.libgltf.asset

import com.micheanl.libgltf.util.JsonValue
import com.micheanl.libgltf.util.JsonFields
import java.net.URLDecoder
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.Base64

internal class GltfBufferResolver(
    private val root: JsonValue,
    private val basePath: Path,
    binaryChunk: ByteBuffer?
) {
    private val buffers: Array<ByteBuffer>

    init {
        val values = JsonFields.value(root, "buffers")
        buffers = if (values == null) {
            if (binaryChunk == null) emptyArray() else arrayOf(binaryChunk.asReadOnlyBuffer().order(ByteOrder.LITTLE_ENDIAN))
        } else {
            Array(values.size()) { index ->
                val value = values[index]
                val uri = JsonFields.string(value, "uri")
                val data = if (uri.isEmpty()) {
                    require(index == 0 && binaryChunk != null)
                    binaryChunk.asReadOnlyBuffer()
                } else {
                    ByteBuffer.wrap(readUri(uri))
                }
                data.order(ByteOrder.LITTLE_ENDIAN)
            }
        }
    }

    fun view(index: Int): ByteBuffer {
        val views = JsonFields.value(root, "bufferViews") ?: error("bufferViews is missing")
        val view = views[index]
        val buffer = buffers[JsonFields.int(view, "buffer", 0)].duplicate().order(ByteOrder.LITTLE_ENDIAN)
        val offset = JsonFields.int(view, "byteOffset", 0)
        val length = JsonFields.int(view, "byteLength", 0)
        return buffer.slice(offset, length).order(ByteOrder.LITTLE_ENDIAN)
    }

    fun viewStride(index: Int): Int {
        val views = JsonFields.value(root, "bufferViews") ?: error("bufferViews is missing")
        return JsonFields.int(views[index], "byteStride", 0)
    }

    fun readUri(uri: String): ByteArray {
        if (uri.startsWith("data:")) {
            val comma = uri.indexOf(',')
            require(comma >= 0)
            val metadata = uri.substring(5, comma)
            val payload = uri.substring(comma + 1)
            return if (metadata.endsWith(";base64")) {
                Base64.getDecoder().decode(payload)
            } else {
                URLDecoder.decode(payload, StandardCharsets.UTF_8).toByteArray(StandardCharsets.UTF_8)
            }
        }
        val decoded = URLDecoder.decode(uri, StandardCharsets.UTF_8)
        return Files.readAllBytes(basePath.resolve(decoded).normalize())
    }
}
