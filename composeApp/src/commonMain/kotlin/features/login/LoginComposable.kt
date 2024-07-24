package features.login

import MainScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import features.account.LoginUiState
import navigation.Navigator
import org.koin.compose.koinInject
import theme.MDWApplicationTheme
import utils.SharedState


@Composable
fun LoginComposable(
    navigator: Navigator = koinInject(),
    viewModel: LoginViewModel = koinInject(),
) {
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {

        LoginUiState.Loading -> {}
        is LoginUiState.Success ->  MDWApplicationTheme(false) {
            SharedState.userId = (uiState.value as LoginUiState.Success).userId
            MainScreen()
        }

        is LoginUiState.NotConnected -> LoginNotConnected(
            uiState = uiState.value as LoginUiState.NotConnected,
            viewModel = viewModel
        )
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginNotConnected(
    modifier: Modifier = Modifier,
    uiState: LoginUiState.NotConnected,
    viewModel: LoginViewModel = koinInject(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = uiState.email ?: "",
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text( "Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.password ?: "",
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.login() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { viewModel.createAccount() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Create an Account", color = Color.Gray)
        }
    }
}









