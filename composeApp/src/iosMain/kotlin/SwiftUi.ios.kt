import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.interop.UIKitViewController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.MapKit.MKMapView
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MyView() { //Actual allows you to use a specific function for android and for ios

    //displays the Map swift UIKit
    UIKitView(
        modifier = Modifier.fillMaxWidth().height(350.dp),
        factory = { MKMapView() }
    )

    //Can be used in xcode to display a screen, but I can't manage to use it in compose to display a swift screen.
    @OptIn(ExperimentalForeignApi::class)
    fun ComposeEntryPointWithUIViewController(
        createUIViewController: () -> UIViewController
    ): UIViewController =
        ComposeUIViewController {
            Column(
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("How to use SwiftUI inside Compose Multiplatform")
                UIKitViewController(
                    factory = createUIViewController,
                    modifier = Modifier.size(300.dp).border(2.dp, Color.Blue),
                )
            }
        }


}