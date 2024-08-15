package io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket

sealed interface ReceivePacket {
    data class HeyOVR(val address: String?) : ReceivePacket
    data object Unknown : ReceivePacket
}
