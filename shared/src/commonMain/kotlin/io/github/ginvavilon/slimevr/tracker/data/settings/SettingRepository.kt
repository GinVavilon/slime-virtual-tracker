package io.github.ginvavilon.slimevr.tracker.data.settings

import io.github.ginvavilon.slimevr.tracker.data.address.MacAddress

interface SettingRepository {

    val trackerMacAddress: MacAddress
    var address: String
    var port: Int

}