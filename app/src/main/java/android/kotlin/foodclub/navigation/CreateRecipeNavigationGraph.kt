package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.home.create.TrimmerViewModel
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeViewModel
import android.kotlin.foodclub.views.home.addIngredients.AddIngredientsView
import android.kotlin.foodclub.views.home.createRecipe.TrimmerView
import android.kotlin.foodclub.views.home.createRecipe.CreateRecipeView
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems

fun NavGraphBuilder.createRecipeNavigationGraph(
    navController: NavHostController,
    setBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        route = HomeOtherRoutes.CreateRecipeView.route,
        startDestination = CreateRecipeScreen.VideoEditor.route
    ) {
        composable(CreateRecipeScreen.Camera.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<CreateRecipeViewModel>(navController)
        }
        composable(CreateRecipeScreen.VideoEditor.route) {
            val viewModel: TrimmerViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()
            viewModel.setOnVideoCreateFunction {
                navController.navigate(CreateRecipeScreen.PostDetails.route + "?videoPath=$it")
            }
            setBottomBarVisibility(false)

            TrimmerView(state = state.value, events = viewModel)
        }
        composable(
            route = CreateRecipeScreen.PostDetails.route + "?videoPath={videoPath}",
            arguments = listOf(navArgument("videoPath") {
                nullable = true
                type = NavType.StringType
            })
        ) { entry ->
            val path = entry.arguments?.getString("videoPath")
            if (path == null) {
                navController.popBackStack()
                return@composable
            }
            val viewModel = entry.sharedHiltViewModel<CreateRecipeViewModel>(navController)
            val state = viewModel.state.collectAsState()
            viewModel.setVideoPath(path)

            setBottomBarVisibility(false)

            CreateRecipeView(
                navController = navController,
                events = viewModel,
                state = state.value,
                onIngredientsSearchBarClick = {
                    navController.navigate(CreateRecipeScreen.AddIngredients.route)
                }
            )
        }
        composable(route = CreateRecipeScreen.AddIngredients.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<CreateRecipeViewModel>(navController)
            val state = viewModel.state.collectAsState()
            val searchResult = viewModel.searchProducts.collectAsLazyPagingItems()

            AddIngredientsView(
                state = state.value.productState,
                searchResult = searchResult,
                events = viewModel,
                backHandler = { navController.popBackStack() }
            )
        }
    }
}

sealed class CreateRecipeScreen(val route: String) {
    data object Camera : CreateRecipeScreen(route = "CREATE_RECIPE_CAMERA")
    data object VideoEditor : CreateRecipeScreen(route = "CREATE_RECIPE_TRIMMER")
    data object PostDetails : CreateRecipeScreen(route = "CREATE_RECIPE_DETAILS")
    data object AddIngredients : CreateRecipeScreen(route = "CREATE_RECIPE_ADD_INGREDIENTS")
}