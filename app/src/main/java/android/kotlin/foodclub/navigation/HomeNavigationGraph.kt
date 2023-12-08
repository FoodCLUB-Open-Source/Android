package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.home.CameraViewModel
import android.kotlin.foodclub.views.home.CameraPreviewView
import android.kotlin.foodclub.views.home.camera.CameraView
import android.kotlin.foodclub.views.home.CreateView
import android.kotlin.foodclub.views.home.discover.DiscoverView
import android.kotlin.foodclub.views.home.followerFollowing.FollowerView
import android.kotlin.foodclub.views.home.GalleryView
import android.kotlin.foodclub.views.home.home.HomeView
import android.kotlin.foodclub.views.home.myBasket.MyBasketView
import android.kotlin.foodclub.views.home.myDigitalPantry.MyDigitalPantryView
import android.kotlin.foodclub.views.home.profile.ProfileView
import android.kotlin.foodclub.views.home.scan.ScanResultView
import android.kotlin.foodclub.views.home.scan.ScanView
import android.kotlin.foodclub.views.home.search.SearchView
import android.kotlin.foodclub.views.home.TakeProfilePhotoView
import android.kotlin.foodclub.views.home.TakeSnapView
import android.kotlin.foodclub.views.home.scan.topbackbar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import android.kotlin.foodclub.viewModels.home.DiscoverViewModel
import android.kotlin.foodclub.viewModels.home.FollowerFollowingViewModel
import android.kotlin.foodclub.viewModels.home.GalleryViewModel
import android.kotlin.foodclub.viewModels.home.home.HomeViewModel
import android.kotlin.foodclub.viewModels.home.MyBasketViewModel
import android.kotlin.foodclub.viewModels.home.ProfileViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState


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
            val state = viewModel.state.collectAsState()

            HomeView(
                navController = navController,
                events = viewModel,
                triggerStoryView = triggerStory,
                state = state.value
            )
        }
        composable(
            route = BottomBarScreenObject.Profile.route + "?userId={userId}",
            arguments = listOf(navArgument("userId") {
                defaultValue = 0L
                type = NavType.LongType
            })
        ) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()
            val userId = it.arguments?.getLong("userId")
            if (userId == null) {
                navController.popBackStack()
                return@composable
            }
            LaunchedEffect(Unit) {viewModel.setUser(userId)}


            ProfileView(
                navController = navController,
                userId = userId,
                viewModel = viewModel,
                state = state.value
            )

        }
        composable(route = BottomBarScreenObject.Discover.route) {
            val viewModel: MyBasketViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            MyBasketView(
                navController = navController,
                viewModel = viewModel,
                state = state.value
            )
        }
        composable(route = BottomBarScreenObject.Create.route) {
            CreateView()
        }

        composable(route = BottomBarScreenObject.Play.route) {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val state = viewModel.state.collectAsState()

            DiscoverView(
                navController = navController,
                viewModel = viewModel,
                state = state.value
            )
        }


        composable("ScanView_route") {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val state = viewModel.state.collectAsState()

            topbackbar(navController = navController)
            {
                ScanView(
                    navController = navController,
                    viewModel = viewModel,
                    state = state.value
                )
            }
        }
        composable("ScanResultView_route") {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val state = viewModel.state.collectAsState()

            ScanResultView(
                navController = navController,
                viewModel = viewModel,
                state = state.value
            )

        }

        composable(route = HomeOtherRoutes.CameraView.route) {
            val stateEncoded = it.arguments?.getString("state") ?: ""
            val viewModel = it.sharedHiltViewModel<CameraViewModel>(navController = navController)
            val state = viewModel.state.collectAsState()

            CameraView(
                viewModel = viewModel,
                navController = navController,
                stateEncoded = stateEncoded,
                state = state.value
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
            val stateEncoded = it.arguments?.getString("state") ?: ""
            val viewModel: GalleryViewModel = hiltViewModel()

            GalleryView(
                navController = navController,
                viewModel = viewModel,
                stateEncoded = stateEncoded
            )
        }

        composable(route = HomeOtherRoutes.FollowerView.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") { nullable = true }
            )
        ) {
            val viewModel: FollowerFollowingViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            it.arguments?.getString("userId")?.let { it1 ->
                FollowerView(
                    navController = navController,
                    viewModel = viewModel,
                    viewType = FollowViewType.FOLLOWERS.title,
                    state = state.value,
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
            val state = viewModel.state.collectAsState()

            it.arguments?.getString("userId")?.let { it1 ->
                FollowerView(
                    navController = navController,
                    viewModel = viewModel,
                    viewType = FollowViewType.FOLLOWING.title,
                    state = state.value,
                    userId = it1.toLong()
                )
            }
        }

        composable(route = HomeOtherRoutes.MyBasketView.route) {
            val viewModel: MyBasketViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            MyBasketView(
                navController = navController,
                viewModel = viewModel,
                state = state.value
            )
        }

        composable(route = HomeOtherRoutes.MySearchView.route) {
            SearchView(navController = navController)
        }
        composable(route = HomeOtherRoutes.MyDigitalPantryView.route) {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val state = viewModel.state.collectAsState()

            MyDigitalPantryView(
                navController = navController,
                viewModel = viewModel,
                state = state.value
            )
        }
        composable(route = HomeOtherRoutes.TakeProfilePhotoView.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            TakeProfilePhotoView(
                navController = navController,
                viewModel = viewModel,
                state = state.value
            )
        }
        composable(route = HomeOtherRoutes.TakeSnapPhotoView.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            TakeSnapView(viewModel = viewModel, navController = navController)
        }

    }
}


sealed class HomeOtherRoutes(val route: String) {
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

}

enum class FollowViewType(val title: String) {
    FOLLOWERS("followers"),
    FOLLOWING("following")
}