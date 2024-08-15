package io.github.ginvavilon.slimevr.tracker.presentation.tracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

fun interface TrackerUi {

    @Composable
    fun content(modifier: Modifier)

}