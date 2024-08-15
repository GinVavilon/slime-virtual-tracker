package io.github.ginvavilon.slimevr.tracker.presentation

import io.github.ginvavilon.slimevr.tracker.data.settings.HeapSettingRepository
import io.github.ginvavilon.slimevr.tracker.data.settings.SettingRepository
import io.github.ginvavilon.slimevr.tracker.debug.DebugTrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.debug.DebugTrackerProvider
import io.github.ginvavilon.slimevr.tracker.debug.DebugTrackerUi
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.presentation.connected.ConnectedScreenViewModel
import io.github.ginvavilon.slimevr.tracker.presentation.connection.ConnectionViewModel
import io.github.ginvavilon.slimevr.tracker.presentation.error.ErrorScreenViewModel
import io.github.ginvavilon.slimevr.tracker.presentation.tracker.TrackerUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationDi = module {
    viewModelOf(::ConnectionViewModel)
    viewModelOf(::ConnectedScreenViewModel)
    viewModelOf(::ErrorScreenViewModel)
    single<TrackerUi> { DebugTrackerUi(get()) }
}

val previewModule = module {
    single { DebugTrackerProvider() }
    single<SettingRepository> { HeapSettingRepository() }
    single<TrackerConnectionClient> { DebugTrackerConnectionClient(get(), Dispatchers.IO) }
    single<TrackerUi> { DebugTrackerUi(get()) }
}