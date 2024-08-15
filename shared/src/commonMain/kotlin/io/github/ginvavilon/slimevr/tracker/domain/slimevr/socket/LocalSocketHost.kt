package io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket

interface LocalSocketHost : AutoCloseable {

    val isStarted: Boolean

    fun start()

    fun stop()

    fun open(address: String, port: Int): RemoteConnection

    suspend fun receive(): ReceivePacket
}
