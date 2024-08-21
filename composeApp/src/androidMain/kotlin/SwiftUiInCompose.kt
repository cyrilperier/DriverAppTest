import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun MyView() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Organiser les éléments en colonne
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Bonjour, le monde!",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Ceci est un écran simple en Jetpack Compose.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}