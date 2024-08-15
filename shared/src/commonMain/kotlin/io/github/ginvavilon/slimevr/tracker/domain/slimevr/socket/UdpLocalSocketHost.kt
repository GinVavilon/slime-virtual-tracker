package io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket

import co.touchlab.stately.concurrency.AtomicLong
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.BoundDatagramSocket
import io.ktor.network.sockets.InetSocketAddress
import io.ktor.network.sockets.UnixSocketAddress
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.isClosed
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.readText
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class UdpLocalSocketHost(
    private val hostName: String,
    private val port: Int,
    private val coroutineContext: CoroutineContext
) : LocalSocketHost {

    private val packetId = AtomicLong(-1)

    private var socket: BoundDatagramSocket? = null
    private var supervisorJob = Job()

    override fun close() {
        stop()
    }

    override val isStarted: Boolean
        get() = !(socket?.isClosed ?: true)

    override fun start() {
        socket = aSocket(SelectorManager(coroutineContext + supervisorJob)).udp()
            .bind(InetSocketAddress(this.hostName, this.port)) {
                broadcast = true
            }
    }

    override fun stop() {
        socket?.dispose()
        supervisorJob.cancel()
        supervisorJob = Job()
    }

    override fun open(address: String, port: Int): RemoteConnection {
        return SocketRemoteConnection(socket!!, InetSocketAddress(address, port), packetId)
    }


    override suspend fun receive(): ReceivePacket {
        println("... wait ${socket!!.localAddress}")
        val received = socket!!.receive()

        val receivedMessage = try {
            received.packet.readText(Charsets.UTF_8)
        } catch (e: CharacterCodingException) {
            ""
        }
        println("<<< $receivedMessage")

        if (receivedMessage.contains("Hey OVR =D 5")) {

            val address = received.address.let { address ->
                when (address) {
                    is InetSocketAddress -> address.hostname
                    is UnixSocketAddress -> address.path
                    else -> null
                }
            }

            return ReceivePacket.HeyOVR(address)
        } else {
            return ReceivePacket.Unknown
        }
    }

}