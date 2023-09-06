package com.example.foodclub.navigation.graphs

import android.kotlin.foodclub.utils.composables.sharedViewModel
import android.kotlin.foodclub.utils.helpers.SessionCache
import android.kotlin.foodclub.views.authentication.LogInWithEmail
import android.kotlin.foodclub.views.authentication.MainLogInAndSignUp
import android.kotlin.foodclub.views.authentication.SignUpWithEmailView
import android.kotlin.foodclub.views.authentication.SignupVerification
import android.kotlin.foodclub.views.authentication.UsernameView
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import android.kotlin.foodclub.viewmodels.authentication.SignupWithEmailViewModel
import com.example.foodclub.views.authentication.ConfirmIdentityView
import com.example.foodclub.views.authentication.ForgotPasswordView

fun NavGraphBuilder.authNavigationGraph(navController: NavHostController, sessionCache: SessionCache) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.MainLogInAndSignUp.route
    ) {
        // Sign up process
        navigation(
            route = AuthScreen.SignUp.route,
            startDestination = "signup_page_1"
        ) {
            composable("signup_page_1") {entry ->
                val viewModel = entry.sharedViewModel<SignupWithEmailViewModel>(navController)
                val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()

                SignUpWithEmailView(
                    onValuesUpdate = { email, pass ->
                        viewModel.saveEmailPasswordData(email, pass)
                        navController.navigate("signup_page_2")
                    },
                    onBackButtonClick = {navController.popBackStack()},
                    userSignUpInformation = userSignUpInformation
                )
            }
            composable("signup_page_2") {entry ->
                val viewModel = entry.sharedViewModel<SignupWithEmailViewModel>(navController)
                val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()
                val error = viewModel.error.collectAsState()

                UsernameView(
                    onValuesUpdate = {
                        viewModel.saveUsername(it)
                        viewModel.signUpUser(navController)
                    },
                    onBackButtonClick = {
                        viewModel.saveUsername(it)
                        navController.popBackStack() },
                    userSignUpInformation = userSignUpInformation,
                    error = error.value
                )
            }
        }

        composable(route = AuthScreen.MainLogInAndSignUp.route) {
            MainLogInAndSignUp(navController)
        }
        composable(route = AuthScreen.Login.route) {
            LogInWithEmail(navController, sessionCache)
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordView(navController)
        }
        composable(route = AuthScreen.ConfirmId.route) {
            ConfirmIdentityView()
        }
        composable(route = AuthScreen.VerifySignup.route + "/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) {backStackEntry ->
            SignupVerification(navController, backStackEntry.arguments?.getString("username"))
        }
    }
}

sealed class AuthScreen(val route: String) {
    object MainLogInAndSignUp : AuthScreen(route = "MENU")
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
    object ConfirmId : AuthScreen(route = "CONFIRM_ID")
    object VerifySignup : AuthScreen(route = "VERIFY_SIGN_UP")
}
