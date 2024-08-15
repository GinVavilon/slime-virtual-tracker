package io.github.ginvavilon.slimevr.tracker.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.ginvavilon.slimevr.tracker.data.dataDi
import io.github.ginvavilon.slimevr.tracker.domain.TrackerConnectionClient
import io.github.ginvavilon.slimevr.tracker.domain.domainDi
import io.github.ginvavilon.slimevr.tracker.presentation.Navigation.navigateToState
import io.github.ginvavilon.slimevr.tracker.presentation.connected.ConnectedScreen
import io.github.ginvavilon.slimevr.tracker.presentation.connection.ConnectingScreen
import io.github.ginvavilon.slimevr.tracker.presentation.connection.ConnectionScreen
import io.github.ginvavilon.slimevr.tracker.presentation.error.ErrorScreen
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.KoinAppDeclaration


@Composable
fun KoinApp(koinAppDeclaration: KoinAppDeclaration = {}) {

    KoinApplication(application = {
        modules(presentationDi, dataDi, domainDi)
        koinAppDeclaration()
    }) {

        App()
    }
}


@Composable
fun App() {

    val navigator: NavHostController = rememberNavController()
    val trackerConnectionClient = koinInject<TrackerConnectionClient>()
    val state: TrackerConnectionClient.State by trackerConnectionClient.state.collectAsState()

    LaunchedEffect(state) {
        navigator.navigateToState(state)
    }

    AppScreen {
        NavHost(
            navController = navigator,
            startDestination = Navigation.START,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable(Navigation.CONNECTION) {
                ConnectionScreen()
            }
            composable(Navigation.CONNECTING) {
                ConnectingScreen()
            }
            composable(Navigation.CONNECTED) {
                ConnectedScreen()
            }
            composable(Navigation.ERROR) {
                ErrorScreen()
            }
        }
    }

}
