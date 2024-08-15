package io.github.ginvavilon.slimevr.tracker.domain

import io.github.ginvavilon.slimevr.tracker.data.tracker.TrackerProvider
import io.github.ginvavilon.slimevr.tracker.debug.DebugTrackerProvider
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.PoseTrackerInfoProvider
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.SlimeVrClient
import io.github.ginvavilon.slimevr.tracker.data.slimevr.TrackerInfoProvider
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.LocalSocketHost
import io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket.UdpLocalSocketHost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.bind
import org.koin.dsl.module

val domainDi = module {
    factory<LocalSocketHost> {
        UdpLocalSocketHost("0.0.0.0", 21221, Dispatchers.IO)
    }
    single<DebugTrackerProvider> {
        DebugTrackerProvider()
    }.bind(TrackerProvider::class)

    factory<TrackerInfoProvider> { PoseTrackerInfoProvider() }
    single { SlimeVrClient(get(), get(), get(), get(), Dispatchers.IO) }
        .bind(TrackerConnectionClient::class)

//    single { DebugTrackerConnectionClient(get(), Dispatchers.IO) }
//        .bind(TrackerConnectionClient::class)
}