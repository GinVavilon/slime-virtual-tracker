package io.github.ginvavilon.slimevr.tracker.presentation.connection

import androidx.lifecycle.ViewModel
import io.github.ginvavilon.slimevr.tracker.data.Const
import io.github.ginvavilon.slimevr.tracker.data.settings.SettingRepository
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.utils.ipv4Regex
import io.github.ginvavilon.slimevr.tracker.utils.isInt
import io.github.ginvavilon.slimevr.tracker.presentation.utils.inputValueOf

class ConnectionViewModel(
    private val settingRepository: SettingRepository,
    private val trackerConnectionClient: TrackerConnectionClient,
) : ViewModel(), ConnectionContract.ViewModel {

    override val address = inputValueOf(settingRepository.address, ipv4Regex)
    override val port = inputValueOf(settingRepository.port.toString()) { it.isInt() }

    override fun connect() {
        trackerConnectionClient.connect(address.value, port.value.toInt())
    }

    override fun resetPort() {
        port.update(Const.DEFAULT_PORT.toString())
    }

    override fun useBroadcast() {
        address.update(Const.BROADCAST_ADDRESS)
    }

}