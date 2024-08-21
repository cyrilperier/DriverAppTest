package features.login

import MainScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import features.account.LoginUiState
import driverapp.composeapp.generated.resources.Res
import driverapp.composeapp.generated.resources.archerLogoText_light
import driverapp.composeapp.generated.resources.archerLogo_light
import driverapp.composeapp.generated.resources.continue_with_password
import driverapp.composeapp.generated.resources.email_or_phone
import driverapp.composeapp.generated.resources.password
import driverapp.composeapp.generated.resources.passwordVisibilityOff
import driverapp.composeapp.generated.resources.passwordVisibilityOn
import driverapp.composeapp.generated.resources.send_a_code_button_text
import driverapp.composeapp.generated.resources.sign_in_button_text
import driverapp.composeapp.generated.resources.sign_in_to_archer
import kotlinx.coroutines.launch
import navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import theme.MDWApplicationTheme
import utils.Api
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
            .padding(16.dp)
    ) {
        TopLeftImage()
        Spacer(modifier = Modifier.height(16.dp))
        CenterImageWithText()
        Spacer(modifier = Modifier.height(16.dp))
        EmailOrPhoneTextField(viewModel,uiState)
        Spacer(modifier = Modifier.height(16.dp))
        if(uiState.withPassword){
            PasswordTextField(viewModel, uiState.passwordVisible,uiState) { viewModel.switchPasswordVisibility() }
            Spacer(modifier = Modifier.height(16.dp))
        }
        SignInButton(viewModel,uiState)
        Spacer(modifier = Modifier.height(8.dp))
        SignInSwitchButtons(viewModel,uiState)
        Spacer(modifier = Modifier.height(16.dp))
        ApiListOrText(uiState)
    }
}

@Composable
fun TopLeftImage() {
    Image(
        painter = painterResource( Res.drawable.archerLogoText_light),
        contentDescription = null,
        modifier = Modifier.size(40.dp)
    )
}

@Composable
fun CenterImageWithText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Image(
            painter = painterResource( Res.drawable.archerLogo_light),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.sign_in_to_archer),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun EmailOrPhoneTextField(viewModel: LoginViewModel,uiState: LoginUiState.NotConnected,) {
    TextField(
        value = uiState.email,
        onValueChange = { viewModel.onEmailChanged(it) },
        label = { Text(stringResource(Res.string.email_or_phone)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun PasswordTextField(viewModel: LoginViewModel, passwordVisible: Boolean,uiState: LoginUiState.NotConnected, onTogglePasswordVisibility: () -> Unit) {
    TextField(
        value = uiState.password,
        onValueChange = { viewModel.onPasswordChanged(it) },
        label = { Text(stringResource(Res.string.password)) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) Res.drawable.passwordVisibilityOn else Res.drawable.passwordVisibilityOff
            IconButton(onClick = onTogglePasswordVisibility) {
                Icon(painter =  painterResource(image), contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun SignInButton(viewModel: LoginViewModel,uiState: LoginUiState.NotConnected) {
    Button(
        onClick = { if (uiState.withPassword) { viewModel.login() } else {
            //Implement send a code
        } },
        enabled = viewModel.isSignInButtonEnabled(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(if (uiState.withPassword) Res.string.sign_in_button_text  else Res.string.send_a_code_button_text))
    }
}

@Composable
fun SignInSwitchButtons(viewModel: LoginViewModel,uiState: LoginUiState.NotConnected) {

        Button(onClick = { viewModel.switchSignInMethod() }) {
            Text(text = stringResource(if (uiState.withPassword)  Res.string.continue_with_password else Res.string.send_a_code_button_text))
        }

}

@Composable
fun ApiListOrText(uiState: LoginUiState) {

    if(SharedState.endpoints.endPoint.size > 1){
        ApiList(apiList = SharedState.endpoints.endPoint) { selectedApi ->
            // Action when an endpoint are selected
        }
    } else {
        Text(text = SharedState.endpoints.endPoint.first().name)
    }
}

@Composable
fun ApiList(apiList: List<Api>, onApiSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }) {
            Text(text = "Host: $onApiSelected")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            apiList.forEach { api ->
                DropdownMenuItem(
                    text = { Text(text = api.name) },
                    onClick = {
                        coroutineScope.launch {
                          //Action
                            expanded = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}