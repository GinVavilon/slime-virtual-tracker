package io.github.ginvavilon.slimevr.tracker

import io.github.ginvavilon.slimevr.tracker.debug.DebugTrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val androidDi = module {
    single<TrackerConnectionClient> { DebugTrackerConnectionClient(get(), Dispatchers.IO) }
}
