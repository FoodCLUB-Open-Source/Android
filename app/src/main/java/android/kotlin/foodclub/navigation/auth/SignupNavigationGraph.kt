package android.kotlin.foodclub.navigation.auth

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.authentication.signUpWithEmail.SignUpEmailEvents
import android.kotlin.foodclub.viewModels.authentication.signUpWithEmail.SignupWithEmailViewModel
import android.kotlin.foodclub.views.authentication.signup.ConfirmEmailView
import android.kotlin.foodclub.views.authentication.signup.CreateFullNameView
import android.kotlin.foodclub.views.authentication.signup.SignUpWithEmailView
import android.kotlin.foodclub.views.authentication.signup.UsernameView
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.signupNavigationGraph(navController: NavHostController) {
    navigation(
        route = AuthScreen.SignUp.route,
        startDestination = SignUpScreen.SignUpPage1.route
    ) {
        composable(route = SignUpScreen.SignUpPage1.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val state = viewModel.state.collectAsState()
            val events : SignUpEmailEvents = viewModel

            SignUpWithEmailView(
                onValuesUpdate = { email, pass ->
                    events.saveEmailPasswordData(email, pass)
                    if (state.value.repeatedEmail != email) {
                        navController.navigate(route = SignUpScreen.SignUpPage2.route)
                    } else {
                        navController.navigate(route = SignUpScreen.SignUpPage3.route)
                    }

                },
                onBackButtonClick = { navController.popBackStack() },
                userSignUpInformation = state.value.userSignUpInformation
            )
        }
        composable(route = SignUpScreen.SignUpPage2.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val state = viewModel.state.collectAsState()
            val events : SignUpEmailEvents = viewModel

            ConfirmEmailView(
                onValuesUpdate = {
                    navController.navigate(route = SignUpScreen.SignUpPage3.route) {
                        popUpTo(route = SignUpScreen.SignUpPage2.route) { inclusive = true }
                    }
                },
                saveData = { events.saveRepeatedEmail(it) },
                onBackButtonClick = { navController.popBackStack() },
                userSignUpInformation = state.value.userSignUpInformation,
                repeatedEmailState = state.value.repeatedEmail,
                error = state.value.error
            )
        }
        composable(route = SignUpScreen.SignUpPage3.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val state = viewModel.state.collectAsState()
            val events : SignUpEmailEvents = viewModel


            CreateFullNameView(
                onValuesUpdate = { navController.navigate(SignUpScreen.SignUpPage4.route) },
                saveData = { events.saveFullName(it) },
                onBackButtonClick = {
                    navController.navigate(route = SignUpScreen.SignUpPage1.route) {
                        popUpTo(route = SignUpScreen.SignUpPage1.route) { inclusive = true }
                    }
                },
                userSignUpInformation = state.value.userSignUpInformation,
                error = state.value.error
            )
        }
        composable(route = SignUpScreen.SignUpPage4.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val state = viewModel.state.collectAsState()
            val events : SignUpEmailEvents = viewModel

            UsernameView(
                onValuesUpdate = { events.signUpUser(navController) },
                saveData = { events.saveUsername(it) },
                onBackButtonClick = { navController.popBackStack() },
                userSignUpInformation = state.value.userSignUpInformation,
                error = state.value.error
            )
        }
    }
}

sealed class SignUpScreen(val route: String) {
    object SignUpPage1 : SignUpScreen(route = "signup_page_1")
    object SignUpPage2 : SignUpScreen(route = "signup_page_2")
    object SignUpPage3 : SignUpScreen(route = "signup_page_3")
    object SignUpPage4 : SignUpScreen(route = "signup_page_4")
}