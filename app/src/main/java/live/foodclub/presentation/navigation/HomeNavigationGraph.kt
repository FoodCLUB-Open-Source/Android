package live.foodclub.presentation.navigation

import live.foodclub.config.ui.BottomBarScreenObject
import live.foodclub.utils.composables.sharedHiltViewModel
import live.foodclub.presentation.ui.home.discover.DiscoverViewModel
import live.foodclub.presentation.ui.home.follow.FollowerFollowingViewModel
import live.foodclub.presentation.ui.home.gallery.GalleryViewModel
import live.foodclub.presentation.ui.home.home.HomeViewModel
import live.foodclub.presentation.ui.home.messaging.MessagingViewModel
import live.foodclub.presentation.ui.home.myBasket.MyBasketViewModel
import live.foodclub.presentation.ui.home.profile.ProfileViewModel
import live.foodclub.presentation.ui.home.search.SearchViewModel
import live.foodclub.presentation.ui.composables.CameraPreviewView
import live.foodclub.presentation.ui.home.createRecipe.components.AddIngredientsView
import live.foodclub.presentation.ui.home.discover.DiscoverView
import live.foodclub.presentation.ui.home.follow.FollowerView
import live.foodclub.presentation.ui.home.gallery.GalleryView
import live.foodclub.presentation.ui.home.home.HomeView
import live.foodclub.presentation.ui.home.home.foodSNAPS.TakeSnapView
import live.foodclub.presentation.ui.home.messaging.MessagingView
import live.foodclub.presentation.ui.home.myBasket.MyBasketView
import live.foodclub.presentation.ui.home.profile.ProfileView
import live.foodclub.presentation.ui.home.profile.composables.TakeProfilePhotoView
import live.foodclub.presentation.ui.home.scan.ScanResultView
import live.foodclub.presentation.ui.home.scan.ScanView
import live.foodclub.presentation.ui.home.scan.TopBackBar
import live.foodclub.presentation.ui.home.search.NewSearchView
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
            val posts = viewModel.postsPagingFlow.collectAsLazyPagingItems()

            HomeView(
                events = viewModel,
                navController = navController,
                state = state.value,
                posts = posts,
                onProfileNavigated = {
                    navController.navigate(BottomBarScreenObject.Profile.route + "?userId=$it")
                }
            )
        }
        composable(
            route = BottomBarScreenObject.Profile.route + "?userId={userId}",
            arguments = listOf(navArgument("userId") {
                defaultValue = 0L
                type = NavType.LongType
            })
        ) {
            val viewModel = it.sharedHiltViewModel<ProfileViewModel>(navController = navController)
            val userPosts = viewModel.userPostsPagingFlow.collectAsLazyPagingItems()
            val userBookmarks = viewModel.userBookmarksPagingFlow.collectAsLazyPagingItems()
            val state = viewModel.state.collectAsState()
            val userId = it.arguments?.getLong("userId")
            if (userId == null) {
                navController.popBackStack()
                return@composable
            }

            LaunchedEffect(Unit) { viewModel.setUser(userId) }

            ProfileView(
                onNavigate = { path, options -> navController.navigate(path) { options() } },
                profilePosts = userPosts,
                bookmarkedPosts = userBookmarks,
                events = viewModel,
                state = state.value
            )

        }
        composable(route = BottomBarScreenObject.Discover.route) {
            val viewModel: MyBasketViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()
            val searchResult = viewModel.searchProducts.collectAsLazyPagingItems()

            MyBasketView(
                events = viewModel,
                state = state.value,
                searchResult = searchResult
            )
        }
        composable(route = BottomBarScreenObject.Create.route) {
//            CreateView()
        }

        composable(route = BottomBarScreenObject.Play.route) {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val state = viewModel.state.collectAsState()
            val productState = viewModel.productState.collectAsState()
            val categoryPosts = viewModel.categoryPostsFlow.collectAsLazyPagingItems()

            DiscoverView(
                onNavigate = { path, options -> navController.navigate(path) { options() } },
                events = viewModel,
                state = state.value,
                productState = productState.value,
                productsEvents = viewModel,
                categoryPosts = categoryPosts
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
                discoverEvents = viewModel,
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
            val searchResult = viewModel.searchProducts.collectAsLazyPagingItems()

            MyBasketView(
                events = viewModel,
                state = state.value,
                searchResult = searchResult
            )
        }

        composable(route = HomeOtherRoutes.MySearchView.route) {
            val viewModel = it.sharedHiltViewModel<SearchViewModel>(navController = navController)
            val state = viewModel.state.collectAsState()

            NewSearchView(navController = navController, events = viewModel, state = state.value)
        }

        composable(route = HomeOtherRoutes.AddIngredientsView.route) {
            val viewModel = it.sharedHiltViewModel<DiscoverViewModel>(navController)
            val searchResult = viewModel.searchProducts.collectAsLazyPagingItems()
            val state = viewModel.productState.collectAsState()

            AddIngredientsView(
                state = state.value,
                events = viewModel,
                searchResult = searchResult,
                backHandler = { navController.popBackStack() })
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

        composable(route = HomeOtherRoutes.MessagingView.route) {
            val viewModel: MessagingViewModel = hiltViewModel()
            val contactsState = viewModel.contactsState.collectAsState()
            val chatState = viewModel.chatState.collectAsState()
            MessagingView(
                contactsState = contactsState.value,
                navController = navController,
                events = viewModel,
                chatState = chatState.value
            )
        }

    }
}


sealed class HomeOtherRoutes(val route: String) {
    data object SettingsView : HomeOtherRoutes(route = "SETTINGS")
    data object MessagingView : HomeOtherRoutes(route = "MESSAGING_VIEW")
    data object CameraView : HomeOtherRoutes(route = "CAMERA_VIEW/{state}")
    data object CreateRecipeView : HomeOtherRoutes(route = "CREATE_RECIPE_VIEW")
    data object CameraPreviewView : HomeOtherRoutes(route = "CAMERA_PREVIEW_VIEW/{uri}/{state}")
    data object GalleryView : HomeOtherRoutes(route = "GALLERY_VIEW/{state}")
    data object FollowerView : HomeOtherRoutes(route = "FOLLOWER_VIEW")

    data object FollowingView : HomeOtherRoutes(route = "FOLLOWING_VIEW")

    data object MyBasketView : HomeOtherRoutes(route = "BASKET_VIEW")
    data object AddIngredientsView : HomeOtherRoutes(route = "ADD_INGREDIENTS")
    data object MySearchView : HomeOtherRoutes(route = "SEARCH_VIEW")

    data object VideoTrimmerView : HomeOtherRoutes(route = "VIDEOTRIMMER")
    data object TakeProfilePhotoView : HomeOtherRoutes(route = "TAKE_PROFILE_PHOTO_VIEW")
    data object TakeSnapPhotoView : HomeOtherRoutes(route = "TAKE_SNAP_VIEW")

}

enum class FollowViewType(val title: String) {
    FOLLOWERS("followers"),
    FOLLOWING("following")
}