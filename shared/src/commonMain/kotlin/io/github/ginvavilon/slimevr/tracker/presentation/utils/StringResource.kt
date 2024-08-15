package io.github.ginvavilon.slimevr.tracker.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import org.jetbrains.compose.resources.StringResource

@Composable
fun stringResource(resource: StringResource, undefine: String = "UndefinePreview"): String {
    return if (LocalInspectionMode.current) {
        undefine
    } else {
        org.jetbrains.compose.resources.stringResource(resource)
    }

}

@Composable
fun String.stringResource(resource: StringResource): String {
    return if (LocalInspectionMode.current) {
        this
    } else {
        org.jetbrains.compose.resources.stringResource(resource)
    }
}