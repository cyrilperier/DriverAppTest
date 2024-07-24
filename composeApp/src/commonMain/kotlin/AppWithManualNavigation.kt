import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import navigation.Navigator
import navigation.Screen
import org.koin.compose.koinInject
import theme.MDWApplicationTheme
import theme.MDWGenericTopAppBar

val LocalSnackBarHostState = compositionLocalOf<SnackbarHostState> {
    error("No Snackbar Host State")
}

@Composable
fun AppWithManualNavigation(
    navigator: Navigator = koinInject()
) {
    val navigationState = navigator.navigationState.collectAsState()
    var isOnSplashscreen by remember { mutableStateOf(true) }

    LaunchedEffect(navigationState.value) {
        isOnSplashscreen = false
    }

    val snackBarHostState = remember { SnackbarHostState() }

    MDWApplicationTheme(isOnSplashscreen = isOnSplashscreen) {
        CompositionLocalProvider(
            values = arrayOf(
                LocalSnackBarHostState provides snackBarHostState
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackBarHostState) },
                    topBar = {
                        when (navigationState.value) {
                            Screen.Splashscreen -> {
                                // No top bar for splashscreen
                            }

                            else -> {
                                MDWGenericTopAppBar(
                                    currentScreen = navigationState.value,
                                    onBackPressed = { navigator.navigateBack() }
                                )
                            }
                        }
                    },
                ) {
                    when (navigationState.value) {
                        Screen.Splashscreen -> {

                        }
                        Screen.Stay -> {

                        }
                    }
                }
            }
        }
    }
}