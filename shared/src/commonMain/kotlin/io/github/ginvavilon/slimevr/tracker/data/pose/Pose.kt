package io.github.ginvavilon.slimevr.tracker.data.pose

import io.github.ginvavilon.slimevr.tracker.data.math.Vector3

interface Pose {
    
    fun getPosition(bodyPart: BodyPart): Vector3?

    object NONE : Pose {
        override fun getPosition(bodyPart: BodyPart): Vector3? = null
    }

}