package io.github.ginvavilon.slimevr.tracker.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.ginvavilon.slimevr.tracker.data.tracker.TrackerPosition
import io.github.ginvavilon.slimevr.tracker.presentation.tracker.TrackerUi
import io.github.ginvavilon.slimevr.tracker.presentation.utils.InputValue
import io.github.ginvavilon.slimevr.tracker.presentation.utils.ListDropdown
import io.github.ginvavilon.slimevr.tracker.presentation.utils.TextInput
import io.github.ginvavilon.slimevr.tracker.presentation.utils.inputValueOf
import kotlin.math.PI

class DebugTrackerUi(
    private val trackerProvider: DebugTrackerProvider,
) : TrackerUi {

    @Composable
    override fun content(modifier: Modifier) {
        val scrollState = rememberScrollState()
        Column(
            modifier.verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            var position by remember { mutableStateOf(TrackerPosition.UNKNOWN) }
            val w = remember { inputValueOf(0f) }
            val x = remember { inputValueOf(0f) }
            val y = remember { inputValueOf(0f) }
            val z = remember { inputValueOf(0f) }


            ListDropdown(
                label = { Text("Position") },
                list = TrackerPosition.entries,
                value = position
            ) {
                position = it
            }
//            TextInput("w", w)
            TextInput("x", x)
            TextInput("y", y)
            TextInput("z", z)
            Button({ send(position, w, x, y, z) }) {
                Text("Send")
            }
        }

    }

    private fun send(
        position: TrackerPosition,
        w: InputValue<Float>,
        x: InputValue<Float>,
        y: InputValue<Float>,
        z: InputValue<Float>
    ) {
        trackerProvider.sendTracker(
            position,
//            w.value,
            x.value.toRad().toFloat(),
            y.value.toRad().toFloat(),
            z.value.toRad().toFloat(),
        )
    }
}

fun Number.toRad(): Double {
    return (PI * this.toDouble()) / 180.0
}
