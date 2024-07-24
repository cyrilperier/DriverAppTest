package features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.account.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import repositories.user.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.NotConnected())

    val uiState = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            val currentState = _uiState.value as LoginUiState.NotConnected
            val response = userRepository.performLogin(currentState.email,currentState.password,true)
if(response){
    _uiState.value = LoginUiState.Success(
    )
}
        }
    }

    fun createAccount() {
        viewModelScope.launch {

        }
    }

    fun onEmailChanged(email:String){
        viewModelScope.launch {
            val currentState = _uiState.value as LoginUiState.NotConnected
            _uiState.value = currentState.copy(
                email = email
            )
        }
    }

    fun onPasswordChanged(password:String){
        viewModelScope.launch {
            val currentState = _uiState.value as LoginUiState.NotConnected
            _uiState.value = currentState.copy(
                password = password
            )
        }
    }

}

