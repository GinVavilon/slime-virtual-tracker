package io.github.ginvavilon.slimevr.tracker.presentation.connected

import androidx.lifecycle.ViewModel
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient.State

class ConnectedScreenViewModel(private val trackerConnectionClient: TrackerConnectionClient) :
    ViewModel(), ConnectedScreenContract.ViewModel {

    override val remoteAddress: String
        get() = trackerConnectionClient.state.value.let { state ->
            if (state is State.Connected){
                state.address
            } else {
                state.toString()
            }
        }

    override fun disconnect() {
        trackerConnectionClient.disconnect()
    }

}