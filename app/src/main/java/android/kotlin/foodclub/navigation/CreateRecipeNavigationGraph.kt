package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.home.CreateRecipeViewModel
import android.kotlin.foodclub.viewModels.home.SettingsViewModel
import android.kotlin.foodclub.views.home.CreateRecipeView
import android.kotlin.foodclub.views.settings.ChangePasswordSettings
import android.kotlin.foodclub.views.settings.EditProfileSetting
import android.kotlin.foodclub.views.settings.PrivacySetting
import android.kotlin.foodclub.views.settings.SettingsView
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.createRecipeNavigationGraph(
    navController: NavHostController, setBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        route = HomeOtherRoutes.CreateRecipeView.route,
        startDestination = CreateRecipeScreen.PostDetails.route
    ) {
        composable(CreateRecipeScreen.Camera.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<CreateRecipeViewModel>(navController)
        }
        composable(CreateRecipeScreen.VideoEditor.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<CreateRecipeViewModel>(navController)
        }
        composable(CreateRecipeScreen.PostDetails.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<CreateRecipeViewModel>(navController)
            setBottomBarVisibility(false)
            CreateRecipeView(navController = navController, viewModel = viewModel)
        }
    }
}

sealed class CreateRecipeScreen(val route: String) {
    object Camera : CreateRecipeScreen(route = "CREATE_RECIPE_CAMERA")
    object VideoEditor : CreateRecipeScreen(route = "CREATE_RECIPE_TRIMMER")
    object PostDetails : CreateRecipeScreen(route = "CREATE_RECIPE_DETAILS")
}