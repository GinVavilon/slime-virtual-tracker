package io.github.ginvavilon.slimevr.tracker.presentation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient

object Navigation {
    const val CONNECTION = "/connection"
    const val CONNECTING = "/connecting"
    const val CONNECTED = "/connected"
    const val ERROR = "/error"
    const val START = CONNECTION

    fun NavController.navigateToState(state: TrackerConnectionClient.State) {
        val route = getRoute(state)
        navigate(
            route,
            NavOptions.Builder()
                .setPopUpTo(route, false)
                .setLaunchSingleTop(true)
                .build(),
            getExtras(state),
        )
    }

    private fun getExtras(state: TrackerConnectionClient.State): Navigator.Extras? {
        return when (state) {
            is TrackerConnectionClient.State.Error -> {
                null
            }
            else -> null
        }
    }


    private fun getRoute(state: TrackerConnectionClient.State): String {
        return when (state) {
            is TrackerConnectionClient.State.Connected -> CONNECTED
            is TrackerConnectionClient.State.Connecting -> CONNECTING
            is TrackerConnectionClient.State.Disconnected -> CONNECTION
            is TrackerConnectionClient.State.Idle -> CONNECTION
            is TrackerConnectionClient.State.Error -> ERROR
        }
    }

}

