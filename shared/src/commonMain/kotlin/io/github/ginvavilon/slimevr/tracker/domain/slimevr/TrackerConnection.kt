package io.github.ginvavilon.slimevr.tracker.domain.slimevr

import io.github.ginvavilon.slimevr.tracker.data.tracker.TrackerData
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.DataType
import io.github.ginvavilon.slimevr.tracker.data.math.Quaternion
import io.github.ginvavilon.slimevr.tracker.data.slimevr.TrackerInfo
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.TrackerStatus
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.RemoteConnection
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.SendPacket

class TrackerConnection(
    private val remoteConnection: RemoteConnection,
    private val trackerId: Byte,
    private val trackerInfo: TrackerInfo,
) {

    suspend fun register() {
        remoteConnection.send { packetId ->
            SendPacket.SensorInfo(
                packetId,
                trackerId,
                TrackerStatus.OK,
                trackerInfo.imuType,
            )
        }
    }

    suspend fun setRotation(quaternion: TrackerData.Quaternion) {
        remoteConnection.send { packetId ->
            SendPacket.Rotation(
                packetId,
                trackerId,
                DataType.NORMAL,
                quaternion.asProtocol()
            )
        }
    }

}

private fun TrackerData.Quaternion.asProtocol() =
    Quaternion(w, x, y, z)

