package io.github.ginvavilon.slimevr.tracker.data.mlkit

import android.content.Context
import androidx.camera.core.ImageAnalysis
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import io.github.ginvavilon.slimevr.tracker.data.pose.MutablePoseDetection
import java.util.concurrent.Executor
import com.google.mlkit.vision.pose.PoseDetection as MLPoseDetection

class MLKitPoseDetection(
    context: Context,
    private val mutablePoseDetection: MutablePoseDetection,
    executor: Executor = ContextCompat.getMainExecutor(context)
) {

    val cameraController: LifecycleCameraController = LifecycleCameraController(context).apply {

        val options = createPoseDetectorOptions()
        val client = MLPoseDetection.getClient(options)
        val analyzer = MlKitAnalyzer(
            listOf(client),
            ImageAnalysis.COORDINATE_SYSTEM_ORIGINAL,
            executor
        ) { result ->
            val value = result.getValue(client)
            if (value != null) {
                mutablePoseDetection.send(MLKitPose(value))
            }
        }

        setImageAnalysisAnalyzer(executor, analyzer)
    }

    companion object {
        private fun createPoseDetectorOptions(accurate: Boolean = true): PoseDetectorOptionsBase {
            return if (accurate) {
                createAccuratePoseDetectorOptions()
            } else {
                createNormalPoseDetectorOptions()
            }
        }

        private fun createAccuratePoseDetectorOptions(): AccuratePoseDetectorOptions {
            return AccuratePoseDetectorOptions.Builder()
                .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
                .build()
        }

        private fun createNormalPoseDetectorOptions(): PoseDetectorOptions {
            return PoseDetectorOptions.Builder()
                .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
                .build()
        }
    }

}