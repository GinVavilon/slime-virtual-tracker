package io.github.ginvavilon.slimevr.tracker.debug

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import io.github.ginvavilon.slimevr.tracker.data.math.Vector3
import io.github.ginvavilon.slimevr.tracker.data.pose.BodyPart
import io.github.ginvavilon.slimevr.tracker.data.pose.Pose
import io.github.ginvavilon.slimevr.tracker.data.pose.PoseDetection
import io.github.ginvavilon.slimevr.tracker.presentation.AppPreview
import io.github.ginvavilon.slimevr.tracker.presentation.tracker.TrackerUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview

class DebugPoseUi(
    private val poseDetection: PoseDetection,
    private val scalePreview: Boolean = true,
) : TrackerUi {

    @Composable
    override fun content(modifier: Modifier) {

        val pose by poseDetection.pose.collectAsState(Pose.NONE)
        var preview by remember { mutableStateOf(scalePreview) }
        Box(Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.TopEnd) {
            Button({ preview = !preview }) {
                Icon(Icons.Default.Lock, "")
            }
        }
        Canvas(modifier) {
            val previewSize = if (preview) Size(4f, 4f) else size
            val previewCenter = if (preview) Offset.Zero else center
            for (bodyPart in BodyPart.entries) {
                drawCircle(
                    color = Color.Red,
                    radius = 10f,
                    center = pose.getPosition(bodyPart).toOffset(previewSize, previewCenter)
                )
            }

            drawSpin(pose, BodyPart.NOSE, BodyPart.LEFT_EYE_INNER, previewSize, previewCenter)
            drawSpin(pose, BodyPart.NOSE, BodyPart.RIGHT_EYE_INNER, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_EYE, BodyPart.RIGHT_EYE_INNER, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_EYE, BodyPart.LEFT_EYE_INNER, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_EYE, BodyPart.RIGHT_EYE_OUTER, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_EYE, BodyPart.LEFT_EYE_OUTER, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_MOUTH, BodyPart.RIGHT_MOUTH, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_EYE_OUTER, BodyPart.LEFT_EAR, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_EYE_OUTER, BodyPart.RIGHT_EAR, previewSize, previewCenter)

            drawSpin(
                pose,
                BodyPart.LEFT_SHOULDER,
                BodyPart.RIGHT_SHOULDER,
                previewSize,
                previewCenter
            )
            drawSpin(pose, BodyPart.LEFT_HIP, BodyPart.RIGHT_HIP, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_HIP, BodyPart.RIGHT_SHOULDER, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_HIP, BodyPart.LEFT_SHOULDER, previewSize, previewCenter)

            drawSpin(
                pose,
                BodyPart.RIGHT_ELBOW,
                BodyPart.RIGHT_SHOULDER,
                previewSize,
                previewCenter
            )
            drawSpin(pose, BodyPart.LEFT_ELBOW, BodyPart.LEFT_SHOULDER, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_ELBOW, BodyPart.RIGHT_WRIST, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_ELBOW, BodyPart.LEFT_WRIST, previewSize, previewCenter)

            drawSpin(pose, BodyPart.LEFT_THUMB, BodyPart.LEFT_WRIST, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_INDEX, BodyPart.LEFT_WRIST, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_PINKY, BodyPart.LEFT_WRIST, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_PINKY, BodyPart.LEFT_INDEX, previewSize, previewCenter)

            drawSpin(pose, BodyPart.RIGHT_THUMB, BodyPart.RIGHT_WRIST, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_INDEX, BodyPart.RIGHT_WRIST, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_PINKY, BodyPart.RIGHT_WRIST, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_PINKY, BodyPart.RIGHT_INDEX, previewSize, previewCenter)

            drawSpin(pose, BodyPart.LEFT_HIP, BodyPart.LEFT_KNEE, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_HIP, BodyPart.RIGHT_KNEE, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_ANKLE, BodyPart.LEFT_KNEE, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_ANKLE, BodyPart.RIGHT_KNEE, previewSize, previewCenter)

            drawSpin(pose, BodyPart.LEFT_ANKLE, BodyPart.LEFT_HEEL, previewSize, previewCenter)
            drawSpin(pose, BodyPart.RIGHT_ANKLE, BodyPart.RIGHT_HEEL, previewSize, previewCenter)
            drawSpin(pose, BodyPart.LEFT_FOOT_INDEX, BodyPart.LEFT_HEEL, previewSize, previewCenter)
            drawSpin(
                pose,
                BodyPart.RIGHT_FOOT_INDEX,
                BodyPart.RIGHT_HEEL,
                previewSize,
                previewCenter
            )
            drawSpin(
                pose,
                BodyPart.LEFT_FOOT_INDEX,
                BodyPart.LEFT_ANKLE,
                previewSize,
                previewCenter
            )
            drawSpin(
                pose,
                BodyPart.RIGHT_FOOT_INDEX,
                BodyPart.RIGHT_ANKLE,
                previewSize,
                previewCenter
            )
        }
    }

    private fun DrawScope.drawSpin(
        pose: Pose,
        start: BodyPart,
        end: BodyPart,
        size: Size = Size(1f, 1f),
        center: Offset,
    ) {
        val start = pose.getPosition(start)
        val end = pose.getPosition(end)
        drawLine(
            color = Color.Blue,
            start = start.toOffset(size, center),
            end = end.toOffset(size, center),
            strokeWidth = 5f
        )
    }

}

private fun Vector3.asOffset(scale: Float): Offset = Offset(x * scale, y * scale)

private fun Vector3?.toOffset(size: Size, center: Offset): Offset =
    (this?.asOffset(size.div(4f).minDimension) ?: Offset.Zero) + center

@Preview
@Composable
fun DebugPosePreview() {
    AppPreview {
        DebugPoseUi(object : PoseDetection {
            override val pose: Flow<Pose> = flowOf(
                DebugPose(
                    x = 500f,
                    y = 1000f,
                    z = 500f,
                    scaleX = 300f,
                    scaleY = -300f,
                    scaleZ = 300f,
                )
            )

        }, true).content(Modifier.fillMaxSize())
    }
}