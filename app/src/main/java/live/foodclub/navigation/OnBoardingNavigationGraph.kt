package live.foodclub.navigation

import live.foodclub.views.authentication.OnBoardingView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.onBoardingNavigationGraph() {
    navigation(
        route = Graph.ON_BOARDING,
        startDestination = OnBoardingScreen.Menu.route
    ) {
        composable(route = OnBoardingScreen.Menu.route) {
            OnBoardingView()
        }
    }
}

sealed class OnBoardingScreen(val route: String) {
    data object Menu : OnBoardingScreen(route = "MENU")
}
