package android.kotlin.foodclub.views.home.home.feed

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.others.AnimatedIcon
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.utils.composables.videoPager.LikeButton
import android.kotlin.foodclub.utils.composables.videoPager.PlayPauseButton
import android.kotlin.foodclub.utils.composables.videoPager.VideoLayout
import android.kotlin.foodclub.utils.composables.videoPager.VideoScroller
import android.kotlin.foodclub.viewModels.home.home.HomeEvents
import android.kotlin.foodclub.views.VideoPagerLoadingSkeleton
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun VideoPager(
    exoPlayer: ExoPlayer,
    videoList: List<VideoModel>,
    initialPage: Int?,
    events: HomeEvents,
    modifier: Modifier,
    localDensity: Density,
    onInfoClick: () -> Unit,
    coroutineScope: CoroutineScope
) {
    if (videoList.isNotEmpty()) {
        val pagerState = rememberPagerState(
            initialPage = initialPage ?: 0,
            initialPageOffsetFraction = 0f
        ){
            videoList.size
        }

        val fling = PagerDefaults.flingBehavior(
            state = pagerState, lowVelocityAnimationSpec = tween(
                easing = LinearEasing, durationMillis = 300
            )
        )

        var videoViewed by remember { mutableStateOf(false) }

        LaunchedEffect(pagerState.currentPage) { videoViewed = false }
        LaunchedEffect(videoViewed) {
            if (videoViewed) {
                events.userViewsPost(videoList[pagerState.currentPage].videoId)
            }
        }

        VerticalPager(
            state = pagerState,
            flingBehavior = fling,
            beyondBoundsPageCount = 1,
            modifier = modifier
        ) { index->
            var pauseButtonVisibility by remember { mutableStateOf(false) }
            val doubleTapState by remember {
                mutableStateOf(
                    AnimatedIcon(R.drawable.liked, 110.dp, localDensity)
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                val currentVideo = videoList[index]
                val authorDetails = SimpleUserModel(
                    userId = 1,
                    username = currentVideo.authorDetails,
                    profilePictureUrl = null
                )
                var isLiked by remember {
                    mutableStateOf(currentVideo.currentViewerInteraction.isLiked)
                }
                var isBookmarked by remember {
                    mutableStateOf(
                        currentVideo.currentViewerInteraction.isBookmarked
                    )
                }

                VideoScroller(
                    exoPlayer = exoPlayer,
                    currentVideo,
                    pagerState,
                    index,
                    onSingleTap = {
                        pauseButtonVisibility = it.isPlaying
                        it.playWhenReady = !it.isPlaying
                    },
                    onDoubleTap = { _, offset ->
                        coroutineScope.launch {
                            doubleTapState.animate(offset)
                        }
                    },
                    onVideoDispose = {
                        pauseButtonVisibility = false
                        videoViewed = true
                    },
                    onVideoGoBackground = { pauseButtonVisibility = false }
                )

                LikeButton(doubleTapState) {
                    isLiked = true
                    coroutineScope.launch {
                        events.updatePostLikeStatus(currentVideo.videoId, isLiked)
                    }
                }
                PlayPauseButton(buttonVisibility = pauseButtonVisibility)

                VideoLayout(
                    userDetails = authorDetails,
                    videoStats = currentVideo.videoStats,
                    likeState = isLiked,
                    bookMarkState = isBookmarked,
                    category = stringResource(id = R.string.meat),
                    opacity = 0.7f,
                    onLikeClick = {
                        isLiked = !isLiked
                        coroutineScope.launch {
                            events.updatePostLikeStatus(currentVideo.videoId, isLiked)
                        }
                    },
                    onBookmarkClick = {
                        isBookmarked = !isBookmarked
                        coroutineScope.launch {
                            events.updatePostBookmarkStatus(
                                currentVideo.videoId,
                                isBookmarked
                            )
                        }
                    },
                    onInfoClick = {events.getRecipe(197); onInfoClick()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
    else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            VideoPagerLoadingSkeleton()
        }
    }
}