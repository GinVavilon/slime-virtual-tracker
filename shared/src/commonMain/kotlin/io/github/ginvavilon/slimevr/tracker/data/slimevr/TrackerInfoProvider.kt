package io.github.ginvavilon.slimevr.tracker.data.slimevr

import io.github.ginvavilon.slimevr.tracker.data.tracker.Tracker

interface TrackerInfoProvider {

    fun getTrackerIndex(tracker: Tracker): Byte

    fun getTrackerInfo(tracker: Tracker): TrackerInfo

}