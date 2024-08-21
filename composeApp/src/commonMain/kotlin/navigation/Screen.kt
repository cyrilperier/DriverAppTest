package navigation


import driverapp.composeapp.generated.resources.Res
import driverapp.composeapp.generated.resources.app_name
import driverapp.composeapp.generated.resources.home_bottombar_icon
import driverapp.composeapp.generated.resources.home_bottombar_icon_selected
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class Screen(
    val bottomBarIcon: DrawableResource? = null,
    val bottomBarSelectedIcon: DrawableResource? = null,
    val bottomBarLabel: StringResource? = null,
    val topBarIcon: DrawableResource? = null,
    val topBarLabel: StringResource? = null,
    val customTopBarLabel: String? = null,
    val parentRootScreen: Screen? = null
) {
    data object Splashscreen : Screen()

    data object Stay : Screen(
        bottomBarIcon = Res.drawable.home_bottombar_icon,
        bottomBarSelectedIcon = Res.drawable.home_bottombar_icon_selected,
        bottomBarLabel = Res.string.app_name
    )

}