package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.home.camera.CameraViewModel
import android.kotlin.foodclub.viewModels.home.discover.DiscoverViewModel
import android.kotlin.foodclub.viewModels.home.follow.FollowerFollowingViewModel
import android.kotlin.foodclub.viewModels.home.gallery.GalleryViewModel
import android.kotlin.foodclub.viewModels.home.home.HomeViewModel
import android.kotlin.foodclub.viewModels.home.messaging.MessagingViewModel
import android.kotlin.foodclub.viewModels.home.myBasket.MyBasketViewModel
import android.kotlin.foodclub.viewModels.home.profile.ProfileViewModel
import android.kotlin.foodclub.views.home.CameraPreviewView
import android.kotlin.foodclub.views.home.profile.TakeProfilePhotoView
import android.kotlin.foodclub.views.home.home.foodSNAPS.TakeSnapView
import android.kotlin.foodclub.views.home.camera.CameraView
import android.kotlin.foodclub.views.home.discover.DiscoverView
import android.kotlin.foodclub.views.home.followerFollowing.FollowerView
import android.kotlin.foodclub.views.home.gallery.GalleryView
import android.kotlin.foodclub.views.home.home.feed.HomeView
import android.kotlin.foodclub.views.home.messagingView.MessagingView
import android.kotlin.foodclub.views.home.myBasket.MyBasketView
import android.kotlin.foodclub.views.home.myDigitalPantry.MyDigitalPantryView
import android.kotlin.foodclub.views.home.profile.ProfileView
import android.kotlin.foodclub.views.home.scan.ScanResultView
import android.kotlin.foodclub.views.home.scan.ScanView
import android.kotlin.foodclub.views.home.scan.TopBackBar
import android.kotlin.foodclub.views.home.search.SearchView
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
            val viewModel  = it.sharedHiltViewModel<ProfileViewModel>(navController = navController)
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
                events = viewModel,
                state = state.value
            )

        }
        composable(route = BottomBarScreenObject.Discover.route) {
            val viewModel: MyBasketViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            MyBasketView(
                navController = navController,
                events = viewModel,
                state = state.value
            )
        }
        composable(route = BottomBarScreenObject.Create.route) {
//            CreateView()
        }

        composable(route = BottomBarScreenObject.Play.route) {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val state = viewModel.state.collectAsState()

            DiscoverView(
                navController = navController,
                events = viewModel,
                state = state.value
            )
        }


        composable("ScanView_route") {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val state = viewModel.state.collectAsState()

            TopBackBar(navController = navController)
            {
                ScanView(
                    navController = navController,
                    events = viewModel,
                    state = state.value
                )
            }
        }
        composable("ScanResultView_route") {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val state = viewModel.state.collectAsState()

            ScanResultView(
                navController = navController,
                events = viewModel,
                state = state.value
            )

        }

        composable(route = HomeOtherRoutes.CameraView.route) {
            val stateEncoded = it.arguments?.getString("state") ?: ""
            val viewModel = it.sharedHiltViewModel<CameraViewModel>(navController = navController)
            val state = viewModel.state.collectAsState()

            CameraView(
                events = viewModel,
                navController = navController,
                stateEncoded = stateEncoded,
                state = state.value
            )

        }
        composable(route = HomeOtherRoutes.VideoTrimmerView.route) {
//            CreateView()
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
            val state = viewModel.state.collectAsState()

            GalleryView(
                navController = navController,
                state = state.value,
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
                    events = viewModel,
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
                    events = viewModel,
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
                events = viewModel,
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
                events = viewModel,
                state = state.value
            )
        }
        composable(route = HomeOtherRoutes.TakeProfilePhotoView.route) {
            val viewModel = it.sharedHiltViewModel<ProfileViewModel>(navController = navController)
            val state = viewModel.state.collectAsState()

            TakeProfilePhotoView(
                navController = navController,
                events = viewModel,
                state = state.value
            )
        }
        composable(route = HomeOtherRoutes.TakeSnapPhotoView.route) {
            val viewModel: HomeViewModel = hiltViewModel()

            TakeSnapView(
                events = viewModel,
                navController = navController
            )
        }

        composable(route = HomeOtherRoutes.MessagingView.route){
            val viewModel : MessagingViewModel = hiltViewModel()

            MessagingView(
                navController = navController
            )
        }

    }
}


sealed class HomeOtherRoutes(val route: String) {
    data object SettingsView : HomeOtherRoutes(route = "SETTINGS")
    data object MessagingView: HomeOtherRoutes(route = "MESSAGING_VIEW")
    data object CameraView : HomeOtherRoutes(route = "CAMERA_VIEW/{state}")
    data object CreateRecipeView : HomeOtherRoutes(route = "CREATE_RECIPE_VIEW")
    data object CameraPreviewView : HomeOtherRoutes(route = "CAMERA_PREVIEW_VIEW/{uri}/{state}")
    data object GalleryView : HomeOtherRoutes(route = "GALLERY_VIEW/{state}")
    data object FollowerView : HomeOtherRoutes(route = "FOLLOWER_VIEW")

    data object FollowingView : HomeOtherRoutes(route = "FOLLOWING_VIEW")

    data object MyBasketView : HomeOtherRoutes(route = "BASKET_VIEW")
    data object MyDigitalPantryView : HomeOtherRoutes(route = "MY_DIGITAL_PANTRY_VIEW")
    data object MySearchView : HomeOtherRoutes(route = "SEARCH_VIEW")

    data object VideoTrimmerView : HomeOtherRoutes(route = "VIDEOTRIMMER")
    data object TakeProfilePhotoView : HomeOtherRoutes(route = "TAKE_PROFILE_PHOTO_VIEW")
    data object TakeSnapPhotoView : HomeOtherRoutes(route = "TAKE_SNAP_VIEW")

}

enum class FollowViewType(val title: String) {
    FOLLOWERS("followers"),
    FOLLOWING("following")
}