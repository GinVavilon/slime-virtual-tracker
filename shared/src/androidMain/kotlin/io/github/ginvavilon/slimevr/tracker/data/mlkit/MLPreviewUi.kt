package io.github.ginvavilon.slimevr.tracker.data.mlkit;

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.github.ginvavilon.slimevr.tracker.R
import io.github.ginvavilon.slimevr.tracker.data.pose.PoseDetection
import io.github.ginvavilon.slimevr.tracker.data.settings.SettingRepository
import io.github.ginvavilon.slimevr.tracker.debug.DebugPoseUi
import io.github.ginvavilon.slimevr.tracker.presentation.tracker.TrackerUi

class MLPreviewUi(
    poseDetection: PoseDetection,
    private val mlKitPoseDetection: MLKitPoseDetection,
    private val settingRepository: SettingRepository,
) : TrackerUi {

    private val bodyUi = DebugPoseUi(poseDetection)

    @Composable
    override fun content(modifier: Modifier) {
        permission {
            Box(modifier = modifier) {
                preview()
                bodyUi.content(Modifier.fillMaxSize())
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun permission(content: @Composable () -> Unit) {
        val cameraPermissionState: PermissionState =
            rememberPermissionState(android.Manifest.permission.CAMERA)
        if (cameraPermissionState.status.isGranted) {
            content()
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = cameraPermissionState::launchPermissionRequest) {
                    Text(text = stringResource(R.string.request_camera_permission))
                }
            }

        }
    }

    @Composable
    private fun preview() {
        val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
        var frontCamera by remember { mutableStateOf(settingRepository.isFrontCamera) }
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            update = { previewView ->
                bindCamera(
                    lifecycleOwner = lifecycleOwner,
                    previewView = previewView,
                    frontCamera = frontCamera,
                )
            },
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(Color.BLACK)
                    implementationMode = PreviewView.ImplementationMode.PERFORMANCE
                    scaleType = PreviewView.ScaleType.FIT_CENTER
                }

            }
        )
        Box(
            Modifier
                .fillMaxSize()
                .padding(10.dp), contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    frontCamera = !frontCamera
                    settingRepository.isFrontCamera = frontCamera
                }) {
                Icon(Icons.Default.Face, contentDescription = "Front Camera")
            }
        }
    }

    private fun bindCamera(
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
        frontCamera: Boolean
    ) {
        val cameraController = mlKitPoseDetection.cameraController
        cameraController.unbind()
        cameraController.cameraSelector =
            if (frontCamera) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }


        previewView.controller = cameraController
        cameraController.bindToLifecycle(lifecycleOwner)
    }

}
