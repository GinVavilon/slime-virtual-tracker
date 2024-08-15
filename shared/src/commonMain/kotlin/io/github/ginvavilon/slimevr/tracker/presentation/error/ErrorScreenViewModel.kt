package io.github.ginvavilon.slimevr.tracker.presentation.error

import androidx.lifecycle.ViewModel
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient

class ErrorScreenViewModel(private val trackerConnectionClient: TrackerConnectionClient) :
    ViewModel(
    ), ErrorScreenContract.ViewModel {

    override val error: String = trackerConnectionClient.state.value.let {
        when (it) {
            is TrackerConnectionClient.State.Error -> it.e.message ?: "Unknown"
            else -> "Wrong state"
        }
    }

    override fun reset() {
        trackerConnectionClient.reset()
    }

}