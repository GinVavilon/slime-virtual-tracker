package io.github.ginvavilon.slimevr.tracker.data.pose

import kotlinx.coroutines.flow.Flow

interface PoseDetection {

    val pose: Flow<Pose>

}