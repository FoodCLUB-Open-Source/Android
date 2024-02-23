package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.home.create.TrimmerViewModel
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeViewModel
import android.kotlin.foodclub.views.home.createRecipe.TrimmerView
import android.kotlin.foodclub.views.home.createRecipe.CreateRecipeView
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.createRecipeNavigationGraph(
    navController: NavHostController,
    setBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        route = HomeOtherRoutes.CreateRecipeView.route,
        startDestination = CreateRecipeScreen.PostDetails.route
    ) {
        composable(CreateRecipeScreen.Camera.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<CreateRecipeViewModel>(navController)
        }
        composable(CreateRecipeScreen.VideoEditor.route) { entry ->
            val viewModel: TrimmerViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()
            viewModel.setOnVideoCreateFunction {
                navController.navigate(CreateRecipeScreen.PostDetails.route)
            }
            setBottomBarVisibility(false)

            TrimmerView(state = state.value, events = viewModel)
        }
        composable(CreateRecipeScreen.PostDetails.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<CreateRecipeViewModel>(navController)
            val state = viewModel.state.collectAsState()

            setBottomBarVisibility(false)

            CreateRecipeView(
                navController = navController,
                events = viewModel,
                state = state.value
            )
        }
    }
}

sealed class CreateRecipeScreen(val route: String) {
    data object Camera : CreateRecipeScreen(route = "CREATE_RECIPE_CAMERA")
    data object VideoEditor : CreateRecipeScreen(route = "CREATE_RECIPE_TRIMMER")
    data object PostDetails : CreateRecipeScreen(route = "CREATE_RECIPE_DETAILS")
}