package android.kotlin.foodclub.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import android.kotlin.foodclub.views.authentication.ForgotPasswordView

fun NavGraphBuilder.onBoardingNavigationGraph(navController: NavHostController,
                                              setBottomBarVisibility: (Boolean) -> Unit) {
    navigation(
        route = Graph.ON_BOARDING,
        startDestination = OnBoardingScreen.Menu.route
    ) {
        composable(route = OnBoardingScreen.Menu.route) {
            ForgotPasswordView(navController)
        }
    }
}

sealed class OnBoardingScreen(val route: String) {
    object Menu : OnBoardingScreen(route = "MENU")
}
