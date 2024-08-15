package io.github.ginvavilon.slimevr.tracker.domain.slimevr

import io.github.ginvavilon.slimevr.tracker.data.slimevr.HardwareInfo
import io.github.ginvavilon.slimevr.tracker.data.slimevr.TrackerInfo
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.LocalSocketHost
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.ReceivePacket
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.RemoteConnection
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.SendPacket
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withTimeout

class GroupTrackerConnection(
    private val localSocketHost: LocalSocketHost,
    private val hardwareInfo: HardwareInfo,
    private val defaultTrackerInfo: TrackerInfo,
) {

    private var _connection: RemoteConnection? = null

    val isJoined
        get() = _connection != null

    suspend fun heartbeat() {
        _connection?.send(SendPacket.Heartbeat)
    }


    suspend fun join(address: String, port: Int): Result<String?> {
        return runCatching {
            coroutineScope {
                val listener = async {
                    withTimeout(30_000) {
                        listenForHandshake()
                    }
                }


                val remoteConnection = localSocketHost.open(address, port)

                remoteConnection.send { packetId ->
                    SendPacket.Handshake(
                        packetId,
                        hardwareInfo.firmwareName,
                        hardwareInfo.firmwareBuild,
                        hardwareInfo.macAddress,
                        defaultTrackerInfo.imuType,
                        hardwareInfo.boardType,
                        hardwareInfo.mcuType,
                    )
                }

                val foundedAddress = listener.await() ?: address

                _connection = if (address == foundedAddress) {
                    remoteConnection
                } else {
                    localSocketHost.open(foundedAddress, port)
                }

                foundedAddress
            }
        }
    }

    private suspend fun listenForHandshake(): String? {
        while (true) {
            when (val receive = localSocketHost.receive()) {

                is ReceivePacket.HeyOVR -> return receive.address
                ReceivePacket.Unknown -> {
                }
            }

        }
    }

    fun createChild(trackerId: Byte, info: TrackerInfo): TrackerConnection {
        return TrackerConnection(_connection!!, trackerId, info)
    }

    fun createChild(trackerId: Byte): TrackerConnection {
        return createChild(trackerId, defaultTrackerInfo)
    }

}