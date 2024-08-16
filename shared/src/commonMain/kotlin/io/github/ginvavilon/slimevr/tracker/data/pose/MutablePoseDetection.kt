package io.github.ginvavilon.slimevr.tracker.data.pose

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MutablePoseDetection : PoseDetection {

    private val _pose = MutableSharedFlow<Pose>(replay = 1)

    override val pose: Flow<Pose> = _pose.asSharedFlow()

    fun send(pose: Pose) {
        _pose.tryEmit(pose)
    }

}