package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.home.camera.CameraViewModel
import android.kotlin.foodclub.viewModels.home.trimmer.TrimmerViewModel
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeViewModel
import android.kotlin.foodclub.views.home.camera.CameraView
import android.kotlin.foodclub.views.home.addIngredients.AddIngredientsView
import android.kotlin.foodclub.views.home.createRecipe.TrimmerView
import android.kotlin.foodclub.views.home.createRecipe.CreateRecipeView
import android.kotlin.foodclub.views.home.createRecipe.TrimmerView
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.createRecipeNavigationGraph(
    navController: NavHostController,
    setBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        route = HomeOtherRoutes.CreateRecipeView.route,
        startDestination = CreateRecipeScreen.CameraView.route
    ) {
        composable(route = CreateRecipeScreen.CameraView.route) {
            val viewModel: CameraViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()
            setBottomBarVisibility(false)
            CameraView(
                events = viewModel,
                navController = navController,
                state = state.value
            )

        }
        composable(CreateRecipeScreen.VideoEditor.route) { entry ->
            val viewModel: TrimmerViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()
            viewModel.setOnVideoCreateFunction {
                navController.navigate(CreateRecipeScreen.PostDetails.route + "?videoPath=$it")
            }
            setBottomBarVisibility(false)
            val videoUris = navController.previousBackStackEntry?.savedStateHandle?.get<MutableMap<Int, Uri>>("videoUris")
            LaunchedEffect(videoUris){
                viewModel.setVideoUris(videoUris)
            }
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
    data object CameraView : CreateRecipeScreen(route = "CREATE_RECIPE_CAMERA")
    data object AddIngredients : CreateRecipeScreen(route = "CREATE_RECIPE_ADD_INGREDIENTS")
}