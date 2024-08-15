package io.github.ginvavilon.slimevr.tracker.presentation.connection

import io.github.ginvavilon.slimevr.tracker.resources.Res
import io.github.ginvavilon.slimevr.tracker.resources.label_address
import io.github.ginvavilon.slimevr.tracker.resources.label_connect
import io.github.ginvavilon.slimevr.tracker.resources.label_port
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.ginvavilon.slimevr.tracker.presentation.AppPreview
import io.github.ginvavilon.slimevr.tracker.presentation.utils.InputValue
import io.github.ginvavilon.slimevr.tracker.presentation.utils.TextInput
import io.github.ginvavilon.slimevr.tracker.presentation.utils.inputValueOf
import io.github.ginvavilon.slimevr.tracker.presentation.utils.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


interface ConnectionContract {

    interface ViewModel {
        val address: InputValue<String>
        val port: InputValue<String>
        fun connect()
        fun resetPort()
        fun useBroadcast()
    }
}

@Composable
fun ConnectionScreen(viewModel: ConnectionContract.ViewModel = koinViewModel<ConnectionViewModel>()) {

    Box(
        Modifier.fillMaxSize(),
        Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextInput(
                value = viewModel.address,
                singleLine = true,
                label = { Text("Address".stringResource(Res.string.label_address)) },
                suffix = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        "Broadcast",
                        Modifier.clickable(onClick = viewModel::useBroadcast)
                    )
                }
            )

            TextInput(
                value = viewModel.port,
                singleLine = true,
                label = { Text("Port".stringResource(Res.string.label_port)) },
                suffix = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        "Refresh",
                        Modifier.clickable(onClick = viewModel::resetPort)
                    )
                },

                )
            Button(viewModel::connect) {
                Text("Connect".stringResource(Res.string.label_connect))
            }
        }
    }
}


@Composable
@Preview
fun ConnectionScreenPreview() {
    AppPreview {

        ConnectionScreen(object : ConnectionContract.ViewModel {
            override val address = inputValueOf("255.255.255.255")
            override val port = inputValueOf("6969")

            override fun connect() {
                TODO("Not yet implemented")
            }

            override fun resetPort() {
                TODO("Not yet implemented")
            }

            override fun useBroadcast() {
                TODO("Not yet implemented")
            }

        })
    }
}
