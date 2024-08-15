package io.github.ginvavilon.slimevr.tracker.presentation.connection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.ginvavilon.slimevr.tracker.presentation.AppPreview
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ConnectingScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp)
        )
    }
}

@Preview
@Composable
fun ConnectingScreenPreview() {
    AppPreview {
        ConnectingScreen()
    }
}