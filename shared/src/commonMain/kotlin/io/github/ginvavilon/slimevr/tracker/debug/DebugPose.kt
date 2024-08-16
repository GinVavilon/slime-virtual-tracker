package io.github.ginvavilon.slimevr.tracker.debug

import io.github.ginvavilon.slimevr.tracker.data.math.Vector3
import io.github.ginvavilon.slimevr.tracker.data.pose.BodyPart
import io.github.ginvavilon.slimevr.tracker.data.pose.Pose
import io.github.ginvavilon.slimevr.tracker.data.math.Vector3 as Vector

class DebugPose(
    private val x: Float = 0.0f,
    private val y: Float = 0.0f,
    private val z: Float = 0f,
    private val scaleX: Float = 1.0f,
    private val scaleY: Float = -1.0f,
    private val scaleZ: Float = 1f,
) : Pose {

    val positions = arrayOf(
        Vector3(0.0, 1.8, 0.1), // Nose
        Vector3(-0.1, 1.9, 0.0), // Left eye inner
        Vector3(-0.2, 1.9, 0.0), // Left eye
        Vector3(-0.25, 1.9, 0.0), // Left eye outer
        Vector3(0.1, 1.9, 0.0), // Right eye inner
        Vector3(0.18, 1.9, 0.0), // Right eye
        Vector3(0.25, 1.9, 0.0), // Right eye outer
        Vector3(-0.3, 1.7, 0.05), // Left ear
        Vector3(0.3, 1.7, 0.05), // Right ear
        Vector3(-0.1, 1.5, 0.0), // Left mouth
        Vector3(0.1, 1.5, 0.0), // Right mouth
        Vector3(-0.5, 1.3, 0.0), // Left shoulder
        Vector3(0.5, 1.3, 0.0), // Right shoulder
        Vector3(-0.7, 0.5, 0.2), // Left elbow
        Vector3(0.7, 0.5, -0.2), // Right elbow
        Vector3(-1.2, 0.8, 0.4), // Left wrist
        Vector3(1.2, 0.8, -0.4), // Right wrist
        Vector3(-1.4, 0.8, 0.5), // Left pinky
        Vector3(1.4, 0.8, -0.5), // Right pinky
        Vector3(-1.3, 1.0, 0.55), // Left index
        Vector3(1.3, 1.0, -0.55), // Right index
        Vector3(-1.1, 0.9, 0.4), // Left thumb
        Vector3(1.1, 0.9, -0.4), // Right thumb
        Vector3(-0.3, 0.5, 0.0), // Left hip
        Vector3(0.3, 0.5, 0.0), // Right hip
        Vector3(-0.4, 0.0, 0.1), // Left knee
        Vector3(0.4, 0.0, 0.2), // Right knee
        Vector3(-0.3, -1.0, 0.0), // Left ankle
        Vector3(0.3, -1.0, 0.2), // Right ankle
        Vector3(-0.25, -1.2, -0.1), // Left heel
        Vector3(0.25, -1.2, 0.1), // Right heel
        Vector3(-0.6, -1.2, 0.4), // Left foot index
        Vector3(0.6, -1.2, 0.6)  // Right foot index
    )


    fun Vector3(x: Double, y: Double, z: Double) = Vector(
        this.x + scaleX * x.toFloat(),
        this.y + scaleY * y.toFloat(),
        this.z + scaleZ * z.toFloat(),
    )

    override fun getPosition(bodyPart: BodyPart): Vector3? {
        return positions.getOrNull(bodyPart.ordinal)
    }
}