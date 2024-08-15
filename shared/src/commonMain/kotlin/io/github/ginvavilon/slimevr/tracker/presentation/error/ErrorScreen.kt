package io.github.ginvavilon.slimevr.tracker.presentation.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.ginvavilon.slimevr.tracker.presentation.AppPreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

interface ErrorScreenContract {

    interface ViewModel {
        val error: String
        fun reset()

    }
}

@Composable
fun ErrorScreen(viewModel: ErrorScreenContract.ViewModel = koinViewModel<ErrorScreenViewModel>()) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Error: ${viewModel.error}!")
            Button(viewModel::reset) {
                Text("Reset")
            }
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    AppPreview {
        ErrorScreen(object : ErrorScreenContract.ViewModel {
            override val error: String = "Preview"

            override fun reset() {
                TODO("Not yet implemented")
            }

        })
    }
}