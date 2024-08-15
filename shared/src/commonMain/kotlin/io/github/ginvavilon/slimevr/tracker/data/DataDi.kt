package io.github.ginvavilon.slimevr.tracker.data

import io.github.ginvavilon.slimevr.tracker.data.address.MacAddressProvider
import io.github.ginvavilon.slimevr.tracker.data.address.SettingMacAddressProvider
import io.github.ginvavilon.slimevr.tracker.data.settings.HeapSettingRepository
import io.github.ginvavilon.slimevr.tracker.data.settings.SettingRepository
import org.koin.dsl.module

val dataDi = module {
    single<SettingRepository> { HeapSettingRepository() }
    single<MacAddressProvider> { SettingMacAddressProvider(get()) }
}