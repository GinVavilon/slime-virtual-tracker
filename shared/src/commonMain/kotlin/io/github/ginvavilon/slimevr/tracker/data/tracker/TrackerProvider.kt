package io.github.ginvavilon.slimevr.tracker.data.tracker

import kotlinx.coroutines.flow.Flow

interface TrackerProvider {
    val tracker: Flow<Tracker>
}