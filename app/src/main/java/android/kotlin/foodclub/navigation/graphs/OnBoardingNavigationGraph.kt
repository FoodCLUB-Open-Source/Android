package com.example.foodclub.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.foodclub.views.authentication.ConfirmIdentityView
import com.example.foodclub.views.authentication.ForgotPasswordView
import com.example.foodclub.views.authentication.LoginView
import com.example.foodclub.views.authentication.SignupView
import com.example.foodclub.views.onboarding.MenuView

fun NavGraphBuilder.onBoardingNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.ON_BOARDING,
        startDestination = OnBoardingScreen.Menu.route
    ) {
        composable(route = OnBoardingScreen.Menu.route) {
            MenuView(navController)
        }
    }
}

sealed class OnBoardingScreen(val route: String) {
    object Menu : OnBoardingScreen(route = "MENU")
}
