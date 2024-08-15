package io.github.ginvavilon.slimevr.tracker.domain.slimevr

import io.github.ginvavilon.slimevr.tracker.data.address.MacAddressProvider
import io.github.ginvavilon.slimevr.tracker.data.slimevr.HardwareInfo
import io.github.ginvavilon.slimevr.tracker.data.slimevr.TrackerInfoProvider
import io.github.ginvavilon.slimevr.tracker.data.slimevr.TrackerInfo
import io.github.ginvavilon.slimevr.tracker.data.tracker.TrackerData
import io.github.ginvavilon.slimevr.tracker.data.tracker.TrackerProvider
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient.State
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.LocalSocketHost
import io.github.ginvavilon.slimevr.tracker.data.slimevr.SlimeVrConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SlimeVrClient(
    private val localSocketHost: LocalSocketHost,
    private val trackerProvider: TrackerProvider,
    private val trackerInfoProvider: TrackerInfoProvider,
    private val macAddressProvider: MacAddressProvider,
    coroutineContext: CoroutineContext,
) : TrackerConnectionClient {

    private val _state = MutableStateFlow<State>(State.Idle)
    private var connectedData: ConnectedData? = null
    private val coroutineScope = CoroutineScope(coroutineContext + SupervisorJob())
    override val state: StateFlow<State> = _state.asStateFlow()


    override fun connect(address: String, port: Int) {
        coroutineScope.launch {
            try {
                _state.value = State.Connecting
                if (!localSocketHost.isStarted) {
                    localSocketHost.start()
                }

                val hardwareInfo = HardwareInfo.DEFAULT
                    .copy(macAddress = macAddressProvider.macAddress)

                val group =
                    GroupTrackerConnection(
                        localSocketHost,
                        hardwareInfo,
                        TrackerInfo.DEFAULT
                    )
                val result = group.join(address, port)

                val connectedAddress = result.getOrThrow()

                val data = ConnectedData(
                    group,
                    localSocketHost,
                    trackerProvider,
                    trackerInfoProvider,
                    coroutineScope.coroutineContext
                )
                connectedData = data
                data.startJobs()
                _state.value = State.Connected(connectedAddress ?: address)
            } catch (e: Throwable) {
                doDisconnect()
                _state.value = State.Error(e)
            }

        }

    }

    override fun disconnect() {
        doDisconnect()
        _state.value = State.Disconnected
    }

    override fun reset() {
        doDisconnect()
        _state.value = State.Idle
    }

    private fun doDisconnect() {
        connectedData?.close()
        connectedData = null
        //localSocketHost.stop()
    }

    class ConnectedData(
        private val connectedGroup: GroupTrackerConnection,
        private val localSocketHost: LocalSocketHost,
        private val trackerProvider: TrackerProvider,
        private val trackerInfoProvider: TrackerInfoProvider,
        coroutineContext: CoroutineContext,
    ) {

        private val scope = CoroutineScope(coroutineContext + Job())
        private val cache = mutableMapOf<String, TrackerConnection>()


        suspend fun startJobs() {
            startHeartbeat()
            startListen()
        }

        private suspend fun startHeartbeat() {

            scope.launch {
                while (isActive) {
                    if (localSocketHost.isStarted) {
                        connectedGroup.heartbeat()
                    }
                    delay(SlimeVrConst.HEARTBEAT_DELAY)
                }
            }
        }

        private fun startListen() {
            scope.launch {
                trackerProvider.tracker.collect { tracker ->
                    val connection = cache.getOrPut(tracker.id) {
                        val index = trackerInfoProvider.getTrackerIndex(tracker)
                        val info = trackerInfoProvider.getTrackerInfo(tracker)

                        connectedGroup.createChild(index, info)
                            .apply { register() }
                    }

                    when (tracker.data) {
                        is TrackerData.Quaternion -> connection.setRotation(tracker.data)
                        TrackerData.None -> {}
                    }

                }
            }
        }

        fun close() {
            scope.cancel()
        }

    }

}