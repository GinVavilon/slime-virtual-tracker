package io.github.ginvavilon.slimevr.tracker.data.address

import io.github.ginvavilon.slimevr.tracker.data.settings.SettingRepository

class SettingMacAddressProvider(private val settingRepository: SettingRepository) : MacAddressProvider{
    override val macAddress: MacAddress
        get() = settingRepository.trackerMacAddress

}