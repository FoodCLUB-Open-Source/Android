package android.kotlin.foodclub.navigation.graphs

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewmodels.home.SettingsViewModel
import android.kotlin.foodclub.views.home.ChangePasswordSettings
import android.kotlin.foodclub.views.home.EditProfileSetting
import android.kotlin.foodclub.views.home.PrivacySetting
import android.kotlin.foodclub.views.home.SettingsView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.settingsNavigationGraph(navController: NavHostController) {
    navigation(
        route = HomeOtherRoutes.SettingsView.route,
        startDestination = SettingsScreen.Main.route
    ) {
        composable(SettingsScreen.Main.route) {entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)
            SettingsView(navController = navController)
        }
        composable(SettingsScreen.EditProfile.route) {entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)
            EditProfileSetting(navController = navController)
        }
        composable(SettingsScreen.Privacy.route) {entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)
            PrivacySetting(navController = navController)
        }
        composable(SettingsScreen.ChangePassword.route) {entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)

            ChangePasswordSettings(null) { oldPassword, newPassword -> {} }
        }
    }
}

sealed class SettingsScreen(val route: String) {
    object Main : SettingsScreen(route = "SETTINGS_MENU")
    object Privacy : SettingsScreen(route = "SETTINGS_PRIVACY")
    object EditProfile : SettingsScreen(route = "SETTINGS_EDIT_PROFILE")
    object ChangePassword : SettingsScreen(route = "SETTINGS_CHANGE_PASS")
}