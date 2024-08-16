package io.github.ginvavilon.slimevr.tracker.data.mlkit

import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.PoseLandmark.LandmarkType
import io.github.ginvavilon.slimevr.tracker.data.math.Vector3
import io.github.ginvavilon.slimevr.tracker.data.pose.BodyPart
import io.github.ginvavilon.slimevr.tracker.data.pose.Pose
import com.google.mlkit.vision.pose.Pose as MLPose

class MLKitPose(
    private val pose: MLPose
) : Pose {
    override fun getPosition(bodyPart: BodyPart): Vector3? {
        return pose.getPoseLandmark(getIndex(bodyPart))?.let { landmark ->
            landmark.position3D.let { point ->
                Vector3(
                    x = point.x,
                    y = point.y,
                    z = point.z
                )
            }
        }
    }

    @LandmarkType
    private fun getIndex(bodyPart: BodyPart): Int {
        return when (bodyPart) {
            BodyPart.NOSE -> PoseLandmark.NOSE
            BodyPart.LEFT_EYE_INNER -> PoseLandmark.LEFT_EYE_INNER
            BodyPart.LEFT_EYE -> PoseLandmark.LEFT_EYE
            BodyPart.LEFT_EYE_OUTER -> PoseLandmark.LEFT_EYE_OUTER
            BodyPart.RIGHT_EYE_INNER -> PoseLandmark.RIGHT_EYE_INNER
            BodyPart.RIGHT_EYE -> PoseLandmark.RIGHT_EYE
            BodyPart.RIGHT_EYE_OUTER -> PoseLandmark.RIGHT_EYE_OUTER
            BodyPart.LEFT_EAR -> PoseLandmark.LEFT_EAR
            BodyPart.RIGHT_EAR -> PoseLandmark.RIGHT_EAR
            BodyPart.LEFT_MOUTH -> PoseLandmark.LEFT_MOUTH
            BodyPart.RIGHT_MOUTH -> PoseLandmark.RIGHT_MOUTH
            BodyPart.LEFT_SHOULDER -> PoseLandmark.LEFT_SHOULDER
            BodyPart.RIGHT_SHOULDER -> PoseLandmark.RIGHT_SHOULDER
            BodyPart.LEFT_ELBOW -> PoseLandmark.LEFT_ELBOW
            BodyPart.RIGHT_ELBOW -> PoseLandmark.RIGHT_ELBOW
            BodyPart.LEFT_WRIST -> PoseLandmark.LEFT_WRIST
            BodyPart.RIGHT_WRIST -> PoseLandmark.RIGHT_WRIST
            BodyPart.LEFT_PINKY -> PoseLandmark.LEFT_PINKY
            BodyPart.RIGHT_PINKY -> PoseLandmark.RIGHT_PINKY
            BodyPart.LEFT_INDEX -> PoseLandmark.LEFT_INDEX
            BodyPart.RIGHT_INDEX -> PoseLandmark.RIGHT_INDEX
            BodyPart.LEFT_THUMB -> PoseLandmark.LEFT_THUMB
            BodyPart.RIGHT_THUMB -> PoseLandmark.RIGHT_THUMB
            BodyPart.LEFT_HIP -> PoseLandmark.LEFT_HIP
            BodyPart.RIGHT_HIP -> PoseLandmark.RIGHT_HIP
            BodyPart.LEFT_KNEE -> PoseLandmark.LEFT_KNEE
            BodyPart.RIGHT_KNEE -> PoseLandmark.RIGHT_KNEE
            BodyPart.LEFT_ANKLE -> PoseLandmark.LEFT_ANKLE
            BodyPart.RIGHT_ANKLE -> PoseLandmark.RIGHT_ANKLE
            BodyPart.LEFT_HEEL -> PoseLandmark.LEFT_HEEL
            BodyPart.RIGHT_HEEL -> PoseLandmark.RIGHT_HEEL
            BodyPart.LEFT_FOOT_INDEX -> PoseLandmark.LEFT_FOOT_INDEX
            BodyPart.RIGHT_FOOT_INDEX -> PoseLandmark.RIGHT_FOOT_INDEX
        }

    }
}