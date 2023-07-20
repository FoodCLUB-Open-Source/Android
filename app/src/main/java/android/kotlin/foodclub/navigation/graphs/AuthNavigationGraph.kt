package com.example.foodclub.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.foodclub.views.authentication.ConfirmIdentityView
import com.example.foodclub.views.authentication.ForgotPasswordView
import com.example.foodclub.views.authentication.LoginView
import com.example.foodclub.views.authentication.SignupView

fun NavGraphBuilder.authNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginView(navController)
        }
        composable(route = AuthScreen.SignUp.route) {
            SignupView()
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordView()
        }
        composable(route = AuthScreen.ConfirmId.route) {
            ConfirmIdentityView()
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
    object ConfirmId : AuthScreen(route = "CONFIRM_ID")
}
