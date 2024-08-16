package io.github.ginvavilon.slimevr.tracker

import io.github.ginvavilon.slimevr.tracker.data.mlkit.MLKitPoseDetection
import io.github.ginvavilon.slimevr.tracker.data.mlkit.MLPreviewUi
import io.github.ginvavilon.slimevr.tracker.debug.DebugTrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.presentation.tracker.TrackerUi
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidDi = module {
    factory { MLKitPoseDetection(androidContext(), get()) }
    single<TrackerConnectionClient> { DebugTrackerConnectionClient(get(), Dispatchers.IO) }
    single<TrackerUi> { MLPreviewUi(get(), get(), get()) }
}
