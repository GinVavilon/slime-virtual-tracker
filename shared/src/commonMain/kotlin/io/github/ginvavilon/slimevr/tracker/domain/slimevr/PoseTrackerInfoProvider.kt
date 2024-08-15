package io.github.ginvavilon.slimevr.tracker.domain.slimevr

import io.github.ginvavilon.slimevr.tracker.data.slimevr.TrackerInfo
import io.github.ginvavilon.slimevr.tracker.data.slimevr.TrackerInfoProvider
import io.github.ginvavilon.slimevr.tracker.data.tracker.Tracker
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.IMUType

class PoseTrackerInfoProvider : TrackerInfoProvider {

    override fun getTrackerIndex(tracker: Tracker): Byte {
        return tracker.position.ordinal.toByte()
    }

    override fun getTrackerInfo(tracker: Tracker): TrackerInfo {
        return TrackerInfo(IMUType.UNKNOWN)
    }
}