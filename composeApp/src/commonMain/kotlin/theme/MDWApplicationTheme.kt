package theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun MDWApplicationTheme(
    isOnSplashscreen: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(
        primary = coolGrey_100,
        onPrimary = white,
        secondary = blue_100,
        onSecondary = white,
        tertiary = skyBlue_40,
        onTertiary = black,
        background = (if (isOnSplashscreen) skyBlue_80 else coolGrey_10)
    )
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        typography = typography
    )
}