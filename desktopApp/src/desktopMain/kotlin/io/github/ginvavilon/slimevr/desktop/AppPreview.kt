package io.github.ginvavilon.slimevr.desktop

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun Test(){
    Text("AAA2223")
}

@Preview
@androidx.compose.desktop.ui.tooling.preview.Preview
@Composable
fun AppPreview(){
    MaterialTheme {
        Text("AAA")
        Surface {
            Test()
        }
    }
}