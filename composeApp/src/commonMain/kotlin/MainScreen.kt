import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gestionsci.composeapp.generated.resources.Res
import gestionsci.composeapp.generated.resources.calendar_icon
import gestionsci.composeapp.generated.resources.formular
import gestionsci.composeapp.generated.resources.info_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        NavigationGraph(navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Calendar,
        BottomNavItem.StayForm,
        BottomNavItem.Information
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.route?.let { popUpTo(it) }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.StayForm.route) {
        composable(BottomNavItem.Calendar.route) {

        }
        composable(BottomNavItem.StayForm.route) {

        }
        composable(BottomNavItem.Information.route) {

        }
    }
}

sealed class BottomNavItem(var title: String, var icon: DrawableResource, var route: String) {
    object Calendar : BottomNavItem("Calendar", Res.drawable.calendar_icon, "calendar")
    object StayForm : BottomNavItem("Stay Form", Res.drawable.formular, "stay_form")
    object Information : BottomNavItem("Information", Res.drawable.info_icon, "information")
}