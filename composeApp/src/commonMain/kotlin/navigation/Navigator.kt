package navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import theme.skyBlue_20
import theme.skyBlue_80

class Navigator {
    private val _navigationState: MutableStateFlow<Screen> = MutableStateFlow(Screen.Stay)
    val navigationState = _navigationState.asStateFlow()

    fun navigateTo(screen: Screen) {
        _navigationState.value = screen
    }

    fun navigateBack() {
        when (_navigationState.value) {
            Screen.Splashscreen -> return
            Screen.Stay -> return
        }
    }
}