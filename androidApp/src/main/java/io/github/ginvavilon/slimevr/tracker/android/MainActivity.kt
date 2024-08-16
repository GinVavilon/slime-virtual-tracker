package io.github.ginvavilon.slimevr.tracker.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.ginvavilon.slimevr.tracker.androidDi
import io.github.ginvavilon.slimevr.tracker.presentation.App
import io.github.ginvavilon.slimevr.tracker.presentation.KoinApp
import io.github.ginvavilon.slimevr.tracker.presentation.AppPreview
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinApp {
                androidContext(this@MainActivity)
                modules(androidDi)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    AppPreview {
        App()
    }
}
