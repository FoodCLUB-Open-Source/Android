package com.example.foodclub.navigation.graphs

import android.kotlin.foodclub.views.authentication.LogInWithEmail
import android.kotlin.foodclub.views.authentication.MainLogInAndSignUp
import android.kotlin.foodclub.views.authentication.SignUpWithEmailView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.foodclub.views.authentication.ConfirmIdentityView
import com.example.foodclub.views.authentication.ForgotPasswordView

fun NavGraphBuilder.authNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.MainLogInAndSignUp.route
    ) {
        composable(route = AuthScreen.MainLogInAndSignUp.route) {
            MainLogInAndSignUp(navController)
        }
        composable(route = AuthScreen.Login.route) {
            LogInWithEmail(navController)
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpWithEmailView(navController)
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordView(navController)
        }
        composable(route = AuthScreen.ConfirmId.route) {
            ConfirmIdentityView()
        }
    }
}

sealed class AuthScreen(val route: String) {
    object MainLogInAndSignUp : AuthScreen(route = "MENU")
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
    object ConfirmId : AuthScreen(route = "CONFIRM_ID")
}
