package io.github.ginvavilon.slimevr.tracker.domain

import kotlinx.coroutines.flow.StateFlow

interface TrackerConnectionClient {

    val state: StateFlow<State>

    fun connect(address: String, port: Int)

    fun disconnect()

    fun reset()

    sealed interface State {
        data object Idle : State
        data object Connecting : State
        data class Connected(val address: String) : State
        data object Disconnected : State
        data class Error(val e: Throwable) : State
    }
}