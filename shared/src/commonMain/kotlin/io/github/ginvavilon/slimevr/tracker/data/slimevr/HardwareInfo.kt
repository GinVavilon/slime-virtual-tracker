package io.github.ginvavilon.slimevr.tracker.data.slimevr

import io.github.ginvavilon.slimevr.tracker.data.address.MacAddress
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.BoardType
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.MCUType

data class HardwareInfo(
    val firmwareName: String,
    val firmwareBuild: Int,
    val macAddress: MacAddress,
    val boardType: BoardType,
    val mcuType: MCUType,
) {
    companion object {
        val DEFAULT: HardwareInfo = HardwareInfo(
            firmwareName = SlimeVrConst.FIRMWARE_NAME,
            firmwareBuild = SlimeVrConst.FIRMWARE_BUILD,
            macAddress = SlimeVrConst.DEFAULT_MAC_ADDRESS,
            boardType = BoardType.UNKNOWN,
            mcuType = MCUType.UNKNOWN
        )
    }
}