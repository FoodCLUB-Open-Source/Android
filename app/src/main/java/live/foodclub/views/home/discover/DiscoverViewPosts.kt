package live.foodclub.views.home.discover

import live.foodclub.R
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.others.AnimatedIcon
import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.utils.composables.customComponents.BackButton
import live.foodclub.utils.composables.videoPager.LikeButton
import live.foodclub.utils.composables.videoPager.PlayPauseButton
import live.foodclub.utils.composables.videoPager.VideoLayout
import live.foodclub.utils.composables.videoPager.VideoScroller
import live.foodclub.viewModels.home.discover.DiscoverEvents
import live.foodclub.viewModels.home.discover.DiscoverViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoverViewPosts(
    viewModel: DiscoverViewModel = hiltViewModel(),
    postId: Long,
    posts: List<VideoModel>,
    events: DiscoverEvents,
    state: DiscoverState,
    onBackPressed: () -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f
    if (screenHeightMinusBottomNavItem <= dimensionResource(id = R.dimen.dim_650)) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }
    var showIngredientSheet by remember { mutableStateOf(false) }
    val triggerIngredientBottomSheetModal: () -> Unit = {
        showIngredientSheet = !showIngredientSheet
    }

    val initialVideo = posts.filter { it.videoId == postId }.getOrNull(0)
    val hasVideoLoaded = remember { mutableStateOf(false) }

    BackHandler {
        onBackPressed()
    }


    Column(
        modifier = Modifier
            .height(screenHeightMinusBottomNavItem)
    ) {
        val currentPostIndex = posts.indexOf(initialVideo)
        val pagerState = rememberPagerState(
            initialPage = if (currentPostIndex == -1) 0 else currentPostIndex,
            initialPageOffsetFraction = 0f
        ){
            posts.size
        }

        if (initialVideo != null) {
            val pagerFlingBehaviour = PagerDefaults.flingBehavior(
                state = pagerState, lowVelocityAnimationSpec = tween(
                    easing = LinearEasing, durationMillis = 300
                )
            )
            VerticalPager(
                state = pagerState,
                flingBehavior = pagerFlingBehaviour,
                beyondBoundsPageCount = 1,
                modifier = Modifier
            ) { vtPager ->
                var pauseButtonVisibility by remember { mutableStateOf(false) }
                val doubleTapState by remember {
                    mutableStateOf(
                        AnimatedIcon(R.drawable.liked, 110.dp, localDensity)
                    )
                }
                val currentVideo = posts[vtPager]
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    var isLiked by remember {
                        mutableStateOf(currentVideo.currentViewerInteraction.isLiked)
                    }
                    var isBookmarked by remember {
                        mutableStateOf(
                            currentVideo.currentViewerInteraction.isBookmarked
                        )
                    }
                    VideoScroller(
                        viewModel.exoPlayer,
                        currentVideo,
                        pagerState,
                        vtPager,
                        onSingleTap = {
                            pauseButtonVisibility = it.isPlaying
                            it.playWhenReady = !it.isPlaying
                        },
                        onDoubleTap = { exoPlayer, offset ->
                            coroutineScope.launch {
                                doubleTapState.animate(offset)
                            }
                        },
                        onVideoDispose = {
                            pauseButtonVisibility = false
                        },
                        onVideoGoBackground = { pauseButtonVisibility = false }
                    )

                    hasVideoLoaded.value = true

                    LikeButton(doubleTapState) {}

                    val simpleUserModel = SimpleUserModel(
                        userId = state.username.toInt(),
                        username = currentVideo.authorDetails,
                        profilePictureUrl = null
                    )
                    PlayPauseButton(buttonVisibility = pauseButtonVisibility)

                    VideoLayout(
                        userDetails = simpleUserModel,
                        videoStats = currentVideo.videoStats,
                        likeState = isLiked,
                        bookMarkState = isBookmarked,
                        category = stringResource(id = R.string.meat),
                        opacity = 0.7f,
                        onLikeClick = {
                            isLiked = !isLiked
                            coroutineScope.launch {
                                // TODO like functionality
                            }
                        },
                        onBookmarkClick = {
                            isBookmarked = !isBookmarked
                            coroutineScope.launch {
                                // TODO bookmark functionality
                            }
                        },
                        onInfoClick = triggerIngredientBottomSheetModal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                    Box(modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.dim_40),
                            start = dimensionResource(id = R.dimen.dim_10)
                        )
                    ) {
                        BackButton(onBackPressed, backgroundTransparent = true, Color.White)
                    }
                }
            }
        }
    }
}