package io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket

interface RemoteConnection {

    suspend fun send(packet: (packetId: Long) -> SendPacket)
    suspend fun send(packet: SendPacket)
}