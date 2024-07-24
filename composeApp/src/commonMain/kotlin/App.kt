
import androidx.compose.runtime.*
import features.login.LoginComposable
import theme.MDWApplicationTheme


@Composable
fun App(

) {
    MDWApplicationTheme(false) {
        LoginComposable()
    }
}