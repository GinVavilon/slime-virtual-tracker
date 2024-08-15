package io.github.ginvavilon.slimevr.tracker.data.tracker

import kotlinx.coroutines.flow.Flow

interface TrackerRepository {
    fun getTracker(position: TrackerPosition): Flow<Tracker>
    fun getTrackers(): Flow<Tracker>
}