package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.home.CameraViewModel
import android.kotlin.foodclub.viewModels.home.DiscoverViewModel
import android.kotlin.foodclub.views.home.CameraPreviewView
import android.kotlin.foodclub.views.home.CameraView
import android.kotlin.foodclub.views.home.CreateView
import android.kotlin.foodclub.views.home.DiscoverView
import android.kotlin.foodclub.views.home.FollowerView
import android.kotlin.foodclub.views.home.GalleryView
import android.kotlin.foodclub.views.home.HomeView
import android.kotlin.foodclub.views.home.MyBasketView
import android.kotlin.foodclub.views.home.MyDigitalPantryView
import android.kotlin.foodclub.views.home.ProfileView
import android.kotlin.foodclub.views.home.ScanResultView
import android.kotlin.foodclub.views.home.ScanView
import android.kotlin.foodclub.views.home.SearchView
import android.kotlin.foodclub.views.home.TakeProfilePhotoView
import android.kotlin.foodclub.views.home.topbackbar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import android.kotlin.foodclub.viewModels.home.FollowerFollowingViewModel
import android.kotlin.foodclub.viewModels.home.GalleryViewModel
import android.kotlin.foodclub.viewModels.home.HomeViewModel
import android.kotlin.foodclub.viewModels.home.MyBasketViewModel
import android.kotlin.foodclub.viewModels.home.ProfileViewModel
import android.kotlin.foodclub.views.home.TakeSnapView
import androidx.hilt.navigation.compose.hiltViewModel



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.homeNavigationGraph(
    navController: NavHostController,
    showSheet: Boolean,
    triggerBottomSheetModal: () -> Unit,
    triggerStory: () -> Unit,
    setBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        route = Graph.HOME,
        startDestination = BottomBarScreenObject.Home.route
    ) {
        settingsNavigationGraph(navController)
        createRecipeNavigationGraph(navController, setBottomBarVisibility)

        composable(route = BottomBarScreenObject.Home.route) {
            setBottomBarVisibility(true)
            val viewModel: HomeViewModel = hiltViewModel()

            HomeView(
                navController = navController,
                viewModel = viewModel,
                triggerStoryView = triggerStory
            )
        }
        composable(
            route = BottomBarScreenObject.Profile.route + "?userId={userId}",
            arguments = listOf(navArgument("userId") {
                defaultValue = 0L
                type = NavType.LongType
            })
        ) {
            val viewModel : ProfileViewModel = hiltViewModel()
            val userId = it.arguments?.getLong("userId")
            if (userId == null) {
                navController.popBackStack()
                return@composable
            }

            ProfileView(
                navController = navController,
                userId = userId,
                viewModel = viewModel
            )

        }
        composable(route = BottomBarScreenObject.Discover.route) {
            val viewModel: MyBasketViewModel = hiltViewModel()

            MyBasketView(viewModel = viewModel)
        }
        composable(route = BottomBarScreenObject.Create.route) {
            CreateView()
        }

        composable(route = BottomBarScreenObject.Play.route) {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)

            DiscoverView(
                navController = navController,
                viewModel = viewModel
            )
        }


        composable("ScanView_route") {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            topbackbar(navController = navController)
            {
                ScanView(navController = navController,viewModel=viewModel)
            }
        }
        composable("ScanResultView_route") {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)

            ScanResultView(navController = navController,viewModel=viewModel)

        }

        composable(route = HomeOtherRoutes.CameraView.route) {
            val state = it.arguments?.getString("state") ?: ""
            val viewModel = it.sharedHiltViewModel<CameraViewModel>(navController = navController)

            CameraView(
              viewModel = viewModel,
              navController = navController,
              stateEncoded = state
            )

        }
        composable(route = HomeOtherRoutes.VideoTrimmerView.route) {
            CreateView()
        }
        composable(route = HomeOtherRoutes.CameraPreviewView.route) { backStackEntry ->
            val uri = backStackEntry.arguments?.getString("uri") ?: ""
            val state = backStackEntry.arguments?.getString("state") ?: ""

            CameraPreviewView(
                uri = uri,
                navController = navController,
                state = state
            )
        }

        composable(route = HomeOtherRoutes.GalleryView.route) {
            val state = it.arguments?.getString("state") ?: ""
            val viewModel: GalleryViewModel = hiltViewModel()

            GalleryView(
                navController = navController,
                viewModel = viewModel,
                stateEncoded = state
            )
        }

        composable(route = HomeOtherRoutes.FollowerView.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") { nullable = true }
            )
        ) {
            val viewModel: FollowerFollowingViewModel = hiltViewModel()

            it.arguments?.getString("userId")?.let { it1 ->
                FollowerView(
                    navController = navController,
                    viewModel = viewModel,
                    viewType = FollowViewType.FOLLOWERS.title,
                    userId = it1.toLong()
                )
            }
        }

        composable(route = HomeOtherRoutes.FollowingView.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") { nullable = true }
            )
        ) {
            val viewModel: FollowerFollowingViewModel = hiltViewModel()

            it.arguments?.getString("userId")?.let { it1 ->
                FollowerView(
                    navController = navController,
                    viewModel = viewModel,
                    viewType = FollowViewType.FOLLOWING.title,
                    userId = it1.toLong()
                )
            }
        }

        composable(route = HomeOtherRoutes.MyBasketView.route) {
            val viewModel: MyBasketViewModel = hiltViewModel()

            MyBasketView(viewModel = viewModel)
        }

        composable(route = HomeOtherRoutes.MySearchView.route) {
            SearchView(navController = navController)
        }
        composable(route = HomeOtherRoutes.MyDigitalPantryView.route) {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)

            MyDigitalPantryView(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(route = HomeOtherRoutes.TakeProfilePhotoView.route) {
            val viewModel: ProfileViewModel = hiltViewModel()

            TakeProfilePhotoView(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(route = HomeOtherRoutes.TakeSnapPhotoView.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            TakeSnapView(viewModel = viewModel, navController = navController)
        }

    }
}


sealed class HomeOtherRoutes(val route: String) {
    object DeleteRecipeView : HomeOtherRoutes(route = "DELETE_RECIPE")
    object SettingsView : HomeOtherRoutes(route = "SETTINGS")
    object CameraView : HomeOtherRoutes(route = "CAMERA_VIEW/{state}")
    object CreateRecipeView : HomeOtherRoutes(route = "CREATE_RECIPE_VIEW")
    object CameraPreviewView : HomeOtherRoutes(route = "CAMERA_PREVIEW_VIEW/{uri}/{state}")
    object GalleryView : HomeOtherRoutes(route = "GALLERY_VIEW/{state}")
    object FollowerView : HomeOtherRoutes(route = "FOLLOWER_VIEW")

    object FollowingView : HomeOtherRoutes(route = "FOLLOWING_VIEW")

    object MyBasketView : HomeOtherRoutes(route = "BASKET_VIEW")
    object MyDigitalPantryView : HomeOtherRoutes(route = "MY_DIGITAL_PANTRY_VIEW")
    object MySearchView : HomeOtherRoutes(route = "SEARCH_VIEW")

    object VideoTrimmerView : HomeOtherRoutes(route = "VIDEOTRIMMER")
    object TakeProfilePhotoView : HomeOtherRoutes(route = "TAKE_PROFILE_PHOTO_VIEW")
    object TakeSnapPhotoView : HomeOtherRoutes(route = "TAKE_SNAP_VIEW")
    object ViewStories : HomeOtherRoutes(route = "VIEWSTORIES")

}

enum class FollowViewType(val title: String) {
    FOLLOWERS("followers"),
    FOLLOWING("following")
}