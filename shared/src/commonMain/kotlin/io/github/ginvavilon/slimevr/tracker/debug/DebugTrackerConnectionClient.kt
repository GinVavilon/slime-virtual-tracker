package io.github.ginvavilon.slimevr.tracker.debug

import io.github.ginvavilon.slimevr.tracker.data.settings.SettingRepository
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DebugTrackerConnectionClient(
    private val settingRepository: SettingRepository,
    coroutineContext: CoroutineContext
) : TrackerConnectionClient {

    private val coroutineScope = CoroutineScope(coroutineContext)
    private val _state = MutableStateFlow<State>(State.Idle)
    override val state: StateFlow<State> = _state.asStateFlow()

    override fun connect(address: String, port: Int) {
        settingRepository.address = address
        settingRepository.port = port
        _state.value = State.Connecting


        coroutineScope.launch {
            delay(1_000)
            _state.value = State.Connected("Debug")
        }
    }

    override fun disconnect() {
        _state.value = State.Disconnected
    }

    override fun reset() {
        _state.value = State.Idle
    }
}