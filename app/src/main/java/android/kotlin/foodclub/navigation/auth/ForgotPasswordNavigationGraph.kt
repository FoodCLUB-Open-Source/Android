package android.kotlin.foodclub.navigation.auth

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.authentication.ForgotPasswordViewModel
import android.kotlin.foodclub.views.authentication.forgotPassword.ChangePasswordView
import android.kotlin.foodclub.views.authentication.forgotPassword.EmailSentView
import android.kotlin.foodclub.views.authentication.forgotPassword.ForgotPasswordView
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.forgotPasswordNavigationGraph(navController: NavHostController) {
    navigation(
        route = AuthScreen.Forgot.route,
        startDestination = "forgot_password_page_1"
    ) {
        composable("forgot_password_page_1") {entry ->
            val viewModel = entry.sharedHiltViewModel<ForgotPasswordViewModel>(navController)
            val errorOccurred = viewModel.errorOccurred.collectAsState()
            val message = viewModel.message.collectAsState()
            val email = viewModel.email.collectAsState()

            ForgotPasswordView(
                onValuesUpdate = {
                    viewModel.sendCode(it){
                        navController.navigate("forgot_password_page_2")
                    } },
                onBackButtonClick = { navController.navigate(AuthScreen.Login.route) {
                    popUpTo(AuthScreen.Login.route) { inclusive = true } }
                                    },
                email = email.value,
                errorOccurred = errorOccurred,
                message = message
            )
        }
        composable("forgot_password_page_2") {entry ->
            val viewModel = entry.sharedHiltViewModel<ForgotPasswordViewModel>(navController)
            val errorOccurred = viewModel.errorOccurred.collectAsState()
            val message = viewModel.message.collectAsState()
            val email = viewModel.email.collectAsState()

            ChangePasswordView(
                onValuesUpdate = { code, password ->
                    viewModel.changePassword(code, password) {
                        navController.navigate("forgot_password_page_3")
                    }
                },
                onBackButtonClick = { navController.popBackStack() },
                email = email.value,
                errorOccurred = errorOccurred,
                message = message
            )
        }
        composable("forgot_password_page_3") {
            EmailSentView(
                onClick = { navController.navigate(AuthScreen.Login.route) {
                    popUpTo(AuthScreen.Login.route) { inclusive = true } } },
                onBackButtonClick = { navController.navigate(AuthScreen.Login.route) {
                    popUpTo(AuthScreen.Login.route) { inclusive = true } } }
            )
        }
    }
}