package android.kotlin.foodclub.utils.composables.videoPager

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.others.AnimatedIcon
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.utils.composables.LikeButton
import android.kotlin.foodclub.utils.composables.PlayPauseButton
import android.kotlin.foodclub.utils.composables.VideoLayout
import android.kotlin.foodclub.utils.composables.VideoScroller
import android.kotlin.foodclub.utils.composables.BackButton
import android.kotlin.foodclub.views.VideoPagerLoadingSkeleton
import android.kotlin.foodclub.views.home.profile.DeleteButton
import android.kotlin.foodclub.views.home.profile.HeaderImage
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun VideoPager(
    exoPlayer: ExoPlayer,
    videoList: LazyPagingItems<VideoModel>,
    initialPage: Int?,
    events: VideoPagerEvents,
    state: VideoPagerState,
    modifier: Modifier,
    localDensity: Density,
    coroutineScope: CoroutineScope,
    onBackPressed: () -> Unit = {}
) {
    var showIngredientSheet by remember { mutableStateOf(false) }
    val triggerIngredientBottomSheetModal: () -> Unit = {
        showIngredientSheet = !showIngredientSheet
    }

    if (showIngredientSheet) {
        RecipeBottomSheet(
            onDismiss = triggerIngredientBottomSheetModal,
            recipe = state.recipe,
            onAddToBasket = { events.addIngredientsToBasket() }

        )
    }
    if (videoList.itemCount > 0) {
        val pagerState = rememberPagerState(
            initialPage = initialPage ?: 0,
            initialPageOffsetFraction = 0f
        ){
            videoList.itemCount
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
                val currentViewedVideo = videoList[pagerState.currentPage]
                if(currentViewedVideo != null) events.userViewsPost(currentViewedVideo.videoId)
            }
        }


        VerticalPager(
            state = pagerState,
            flingBehavior = fling,
            beyondBoundsPageCount = 1,
            modifier = modifier
        ) { index->
            val currentVideo = videoList[index]

            if(currentVideo != null) {
                val deleteDialog = remember { mutableStateOf(false) }
                if (deleteDialog.value) {
                    ConfirmDeleteDialog(
                        title = stringResource(id = R.string.delete_video),
                        desc = stringResource(id = R.string.delete_video_message),
                        onDismiss = {
                            deleteDialog.value = false
                        },
                        onConfirm = {
                            deleteDialog.value = false
                            events.deletePost(currentVideo.videoId)
                        }
                    )
                }

                var pauseButtonVisibility by remember { mutableStateOf(false) }
                val doubleTapState by remember {
                    mutableStateOf(
                        AnimatedIcon(R.drawable.liked, 110.dp, localDensity)
                    )
                }

                Box(modifier = Modifier.fillMaxSize()) {

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

                    if (/*there should be author id*/0L == state.browsingUserId) {
                        DeleteButton(
                            alignment = Alignment.TopEnd,
                            onDeleteClicked = { deleteDialog.value = true }
                        )
                    }
                    LikeButton(doubleTapState) {
                        isLiked = !isLiked
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
                        onInfoClick = {
                            events.getRecipe(197)
                            triggerIngredientBottomSheetModal()
                                      },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                    if(!state.isHomeView) {
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

@Composable
fun ConfirmDeleteDialog(
    title: String,
    desc: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_450))) {
            Column {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_130)))
                Box(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dim_490))
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                dimensionResource(id = R.dimen.dim_25),
                                dimensionResource(id = R.dimen.dim_10),
                                dimensionResource(id = R.dimen.dim_25),
                                dimensionResource(id = R.dimen.dim_10)
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_16)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_24)))

                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.dim_10))
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = Montserrat,
                            maxLines = integerResource(id=R.integer.int_2),
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_8)))


                        Text(
                            text = desc,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(
                                    top = dimensionResource(id = R.dimen.dim_10),
                                    start = dimensionResource(id = R.dimen.dim_25),
                                    end = dimensionResource(id = R.dimen.dim_25)
                                )
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = Montserrat,
                            maxLines = integerResource(id=R.integer.int_250),
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_24)))


                        Button(
                            onClick = onDismiss,
                            colors = defaultButtonColors(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.no),
                                color = Color.White,
                                fontFamily = Montserrat,
                            )
                        }
                        ElevatedButton(
                            onClick = onConfirm,
                            colors = defaultButtonColors(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)))
                        ) {
                            Text(
                                text = stringResource(id = R.string.yes),
                                color = Color.White,
                                fontFamily = Montserrat,
                            )
                        }


                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_8)))
                    }
                }
            }
            HeaderImage(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_200))
                    .align(Alignment.TopCenter)
            )
        }
    }
}