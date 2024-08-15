package io.github.ginvavilon.slimevr.tracker.presentation.connected

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.ginvavilon.slimevr.tracker.presentation.tracker.TrackerUi
import io.github.ginvavilon.slimevr.tracker.presentation.AppPreview
import io.github.ginvavilon.slimevr.tracker.presentation.utils.stringResource
import io.github.ginvavilon.slimevr.tracker.resources.Res
import io.github.ginvavilon.slimevr.tracker.resources.label_disconnect
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

interface ConnectedScreenContract {

    interface ViewModel {
        val remoteAddress: String

        fun disconnect()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectedScreen(
    viewModel: ConnectedScreenContract.ViewModel = koinViewModel<ConnectedScreenViewModel>(),
    trackerUi: TrackerUi = koinInject()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = viewModel.remoteAddress,
                            modifier = Modifier.weight(1f)
                        )
                        Button(viewModel::disconnect) {
                            Text("Disconnect".stringResource(Res.string.label_disconnect))
                        }
                    }

                }
            )
        }
    ) { paddings ->
        trackerUi.content(Modifier.fillMaxSize().padding(paddings))
    }
}

@Preview
@Composable
fun ConnectedScreenPreview() {
    AppPreview {
        ConnectedScreen(object : ConnectedScreenContract.ViewModel {

            override val remoteAddress: String = "0.0.0.0"

            override fun disconnect() {
                TODO("Not yet implemented")
            }

        }
        )
    }
}