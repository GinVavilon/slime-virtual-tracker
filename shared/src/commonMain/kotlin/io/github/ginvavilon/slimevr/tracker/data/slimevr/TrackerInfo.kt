package io.github.ginvavilon.slimevr.tracker.data.slimevr

import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.IMUType

data class TrackerInfo(
    val imuType: IMUType,
) {
    companion object {
        val DEFAULT: TrackerInfo = TrackerInfo(
            IMUType.UNKNOWN
        )
    }
}