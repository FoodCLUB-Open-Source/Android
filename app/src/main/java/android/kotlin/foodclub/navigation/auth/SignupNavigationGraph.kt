package android.kotlin.foodclub.navigation.auth

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.authentication.SignupWithEmailViewModel
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
        composable(route = SignUpScreen.SignUpPage1.route) {entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()
            val repeatedEmail = viewModel.repeatedEmail.collectAsState()

            SignUpWithEmailView(
                onValuesUpdate = { email, pass ->
                    viewModel.saveEmailPasswordData(email, pass)
                    if(repeatedEmail.value != email) {
                        navController.navigate(route = SignUpScreen.SignUpPage2.route)
                    } else {
                        navController.navigate(route = SignUpScreen.SignUpPage3.route)
                    }

                },
                onBackButtonClick = { navController.popBackStack() },
                userSignUpInformation = userSignUpInformation
            )
        }
        composable(route = SignUpScreen.SignUpPage2.route) {entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()
            val error = viewModel.error.collectAsState()
            val repeatedEmail = viewModel.repeatedEmail.collectAsState()

            ConfirmEmailView(
                onValuesUpdate = {
                    navController.navigate(route = SignUpScreen.SignUpPage3.route) {
                        popUpTo(route = SignUpScreen.SignUpPage2.route) { inclusive = true }
                    }
                },
                saveData = { viewModel.saveRepeatedEmail(it) },
                onBackButtonClick = { navController.popBackStack() },
                userSignUpInformation = userSignUpInformation,
                repeatedEmailState = repeatedEmail,
                error = error.value
            )
        }
        composable(route = SignUpScreen.SignUpPage3.route) {entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()
            val error = viewModel.error.collectAsState()

            CreateFullNameView(
                onValuesUpdate = { navController.navigate(SignUpScreen.SignUpPage4.route) },
                saveData = { viewModel.saveFullName(it) },
                onBackButtonClick = {
                    navController.navigate(route = SignUpScreen.SignUpPage1.route) {
                        popUpTo(route = SignUpScreen.SignUpPage1.route) { inclusive = true }
                    } },
                userSignUpInformation = userSignUpInformation,
                error = error.value
            )
        }
        composable(route = SignUpScreen.SignUpPage4.route) {entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()
            val error = viewModel.error.collectAsState()

            UsernameView(
                onValuesUpdate = { viewModel.signUpUser(navController) },
                saveData = { viewModel.saveUsername(it) },
                onBackButtonClick = { navController.popBackStack() },
                userSignUpInformation = userSignUpInformation,
                error = error.value
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