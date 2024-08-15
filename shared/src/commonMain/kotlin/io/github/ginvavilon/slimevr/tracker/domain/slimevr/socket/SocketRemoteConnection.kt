package io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket

import co.touchlab.stately.concurrency.AtomicLong
import io.ktor.network.sockets.BoundDatagramSocket
import io.ktor.network.sockets.Datagram
import io.ktor.network.sockets.SocketAddress
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully

class SocketRemoteConnection(
    private val socket: BoundDatagramSocket,
    private val socketAddress: SocketAddress,
    private val packetId: AtomicLong
) : RemoteConnection {


    override suspend fun send(packet: (packetId: Long) -> SendPacket) {
        val sendPacket = packet.invoke(packetId.incrementAndGet())
        send(sendPacket)
    }

    override suspend fun send(packet: SendPacket) {
        println(">>> ${socket.localAddress} -> $socketAddress) : $packet")
        socket.send(
            Datagram(
                buildPacket { writeFully(packet.byteArray) },
                socketAddress
            )
        )
    }

}