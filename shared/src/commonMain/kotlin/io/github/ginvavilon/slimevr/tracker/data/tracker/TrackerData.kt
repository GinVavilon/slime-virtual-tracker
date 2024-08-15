package io.github.ginvavilon.slimevr.tracker.data.tracker

import io.github.axisangles.ktmath.Quaternion

sealed interface TrackerData {
    data object None : TrackerData
    data class Quaternion(val w: Float, val x: Float, val y: Float, val z: Float) : TrackerData
}

fun Quaternion.toData() = TrackerData.Quaternion(
    w = w,
    x = x,
    y = y,
    z = z,
)