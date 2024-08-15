package io.github.ginvavilon.slimevr.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.ginvavilon.slimevr.tracker.presentation.App
import io.github.ginvavilon.slimevr.tracker.presentation.KoinApp


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Slime Virtual Tracker",
    ) {
        KoinApp()
    }
}
