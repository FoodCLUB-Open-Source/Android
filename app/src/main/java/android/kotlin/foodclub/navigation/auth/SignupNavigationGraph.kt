package android.kotlin.foodclub.navigation.auth

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewmodels.authentication.SignupWithEmailViewModel
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
        startDestination = "signup_page_1"
    ) {
        composable("signup_page_1") {entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()
            val repeatedEmail = viewModel.repeatedEmail.collectAsState()

            SignUpWithEmailView(
                onValuesUpdate = { email, pass ->
                    viewModel.saveEmailPasswordData(email, pass)
                    if(repeatedEmail.value != email) {
                        navController.navigate("signup_page_2")
                    } else {
                        navController.navigate("signup_page_3")
                    }

                },
                onBackButtonClick = { navController.popBackStack() },
                userSignUpInformation = userSignUpInformation
            )
        }
        composable("signup_page_2") {entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()
            val error = viewModel.error.collectAsState()
            val repeatedEmail = viewModel.repeatedEmail.collectAsState()

            ConfirmEmailView(
                onValuesUpdate = {
                    viewModel.saveRepeatedEmail(it)
                    navController.navigate("signup_page_3") { popUpTo("signup_page_2") { inclusive = true } }
                },
                onBackButtonClick = {
                    navController.popBackStack() },
                userSignUpInformation = userSignUpInformation,
                repeatedEmailState = repeatedEmail,
                error = error.value
            )
        }
        composable("signup_page_3") {entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
            val userSignUpInformation = viewModel.userSignUpInformation.collectAsState()
            val error = viewModel.error.collectAsState()

            CreateFullNameView(
                onValuesUpdate = {
                    viewModel.saveFullName(it)
                    navController.navigate("signup_page_4")
                },
                onBackButtonClick = {
                    viewModel.saveFullName(it)
                    navController.navigate("signup_page_1") { popUpTo("signup_page_1") { inclusive = true } } },
                userSignUpInformation = userSignUpInformation,
                error = error.value
            )
        }
        composable("signup_page_4") {entry ->
            val viewModel = entry.sharedHiltViewModel<SignupWithEmailViewModel>(navController)
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
}