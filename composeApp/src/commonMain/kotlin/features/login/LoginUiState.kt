package features.account

import services.network.inventory.UserRaw

sealed class LoginUiState {
    data object Loading : LoginUiState()
    data class Success(
        var userId: String ="",
    ) : LoginUiState()
    data class NotConnected(
        var email: String ="",
        var password: String ="",
        var passwordVisible: Boolean = false,
        var withPassword: Boolean = false,
    ) : LoginUiState()
}