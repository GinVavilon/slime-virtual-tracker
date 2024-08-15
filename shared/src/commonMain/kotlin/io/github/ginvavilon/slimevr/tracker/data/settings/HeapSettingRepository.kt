package io.github.ginvavilon.slimevr.tracker.data.settings

import io.github.ginvavilon.slimevr.tracker.data.Const
import io.github.ginvavilon.slimevr.tracker.data.address.MacAddress
import io.github.ginvavilon.slimevr.tracker.data.slimevr.SlimeVrConst

class HeapSettingRepository : SettingRepository {
    override val trackerMacAddress: MacAddress = SlimeVrConst.DEFAULT_MAC_ADDRESS
    override var address: String = Const.DEFAULT_ADDRESS
    override var port: Int = Const.DEFAULT_PORT
}