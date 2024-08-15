package io.github.ginvavilon.slimevr.tracker.data.slimevr

import io.github.ginvavilon.slimevr.tracker.data.address.MacAddress

object SlimeVrConst {
    const val FIRMWARE_NAME = "Pose Virtual Tracker"
    const val FIRMWARE_BUILD = 1
    const val HEARTBEAT_DELAY = 800L
    val DEFAULT_MAC_ADDRESS = MacAddress(0x01, 0x02, 0x03, 0x04, 0x05, 0x10)

}