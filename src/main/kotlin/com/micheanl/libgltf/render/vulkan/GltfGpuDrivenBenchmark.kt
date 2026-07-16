package com.micheanl.libgltf.render.vulkan

import com.mojang.logging.LogUtils

object GltfGpuDrivenBenchmark {
    private var samples = 0
    private var recordNanos = 0L
    private var submits = 0L
    private var visibleInstances = 0L
    private var culledInstances = 0L
    private var visibleMeshlets = 0L
    private var culledMeshlets = 0L
    private var drawCommands = 0L
    private var commandBytes = 0L
    private var taskGroups = 0L
    private var triangles = 0L

    fun record(
        nanos: Long,
        submitCount: Int,
        visibleInstanceCount: Int,
        culledInstanceCount: Int,
        visibleMeshletCount: Int,
        culledMeshletCount: Int,
        drawCommandCount: Int,
        triangleCount: Int,
        lod: Int,
        mode: String,
        skinned: Boolean,
        transparent: Boolean,
        taskGroupCount: Int = 0
    ) {
        if (!GltfGpuDrivenSettings.benchmark) return
        samples++
        recordNanos += nanos
        submits += submitCount
        visibleInstances += visibleInstanceCount
        culledInstances += culledInstanceCount
        visibleMeshlets += visibleMeshletCount
        culledMeshlets += culledMeshletCount
        drawCommands += drawCommandCount
        commandBytes += drawCommandCount.toLong() * COMMAND_STRIDE
        taskGroups += taskGroupCount
        triangles += triangleCount
        if (samples < REPORT_SAMPLES) return
        LOGGER.info(
            "libgltf benchmark backend=Vulkan mode={} samples={} avgRecordNs={} submits={} visibleInstances={} culledInstances={} visibleMeshlets={} culledMeshlets={} drawCommands={} commandBytes={} taskGroups={} triangles={} lod={} skinned={} transparent={}",
            mode,
            samples,
            recordNanos / samples,
            submits,
            visibleInstances,
            culledInstances,
            visibleMeshlets,
            culledMeshlets,
            drawCommands,
            commandBytes,
            taskGroups,
            triangles,
            lod,
            skinned,
            transparent
        )
        samples = 0
        recordNanos = 0L
        submits = 0L
        visibleInstances = 0L
        culledInstances = 0L
        visibleMeshlets = 0L
        culledMeshlets = 0L
        drawCommands = 0L
        commandBytes = 0L
        taskGroups = 0L
        triangles = 0L
    }

    private const val REPORT_SAMPLES = 300
    private const val COMMAND_STRIDE = 20
    private val LOGGER = LogUtils.getLogger()
}