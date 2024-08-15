package io.github.ginvavilon.slimevr.tracker.debug

import io.github.axisangles.ktmath.Quaternion
import io.github.axisangles.ktmath.Vector3
import io.github.ginvavilon.slimevr.tracker.data.tracker.Tracker
import io.github.ginvavilon.slimevr.tracker.data.tracker.TrackerData
import io.github.ginvavilon.slimevr.tracker.data.tracker.TrackerPosition
import io.github.ginvavilon.slimevr.tracker.data.tracker.TrackerProvider
import io.github.ginvavilon.slimevr.tracker.data.tracker.toData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DebugTrackerProvider : TrackerProvider {

    private val _tracker = MutableSharedFlow<Tracker>(replay = 1)

    override val tracker: Flow<Tracker> = _tracker.asSharedFlow()

    private fun sendTracker(tracker: Tracker) {
        println("Send tracker $tracker")
        _tracker.tryEmit(tracker)
    }

    fun sendTracker(
        position: TrackerPosition,
        w: Float,
        x: Float,
        y: Float,
        z: Float,
    ) {
        sendTracker(position, TrackerData.Quaternion(w = w, x = x, y = y, z = z))
    }

    fun sendTracker(
        position: TrackerPosition,
        x: Float,
        y: Float,
        z: Float,
    ) {
        val data = Quaternion.fromRotationVector(Vector3(x, y, z)).toData()
        sendTracker(position, data)
    }

    private fun sendTracker(
        position: TrackerPosition,
        data: TrackerData.Quaternion
    ) {
        val tracker = Tracker(
            id = position.toString(),
            position = position,
            data = data,
        )
        sendTracker(tracker)
    }

}