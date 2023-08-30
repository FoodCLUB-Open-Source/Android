package com.example.foodclub.navigation.graphs

import android.kotlin.foodclub.views.authentication.EmailSentView
import android.kotlin.foodclub.views.authentication.LogInWithEmail
import android.kotlin.foodclub.views.authentication.MainLogInAndSignUp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.foodclub.views.authentication.ConfirmIdentityView
import com.example.foodclub.views.authentication.ForgotPasswordView
import com.example.foodclub.views.onboarding.MenuView

fun NavGraphBuilder.onBoardingNavigationGraph(navController: NavHostController) {
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
