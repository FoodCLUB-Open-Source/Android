package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.others.AnimatedIcon
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.utils.composables.BackButton
import android.kotlin.foodclub.utils.composables.LikeButton
import android.kotlin.foodclub.utils.composables.PlayPauseButton
import android.kotlin.foodclub.utils.composables.VideoLayout
import android.kotlin.foodclub.utils.composables.VideoScroller
import android.kotlin.foodclub.viewModels.home.profile.ProfileEvents
import android.kotlin.foodclub.views.home.home.HomeBottomSheetIngredients
import android.kotlin.foodclub.views.home.profile.ProfileState
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HeaderImage(modifier: Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.location))
    val progress by animateLottieCompositionAsState(composition = composition)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
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


@OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ShowProfilePosts(
    postId: Long,
    posts: List<VideoModel>,
    events: ProfileEvents,
    state: ProfileState,
    onPostDeleted: () -> Unit,
    onBackPressed: () -> Unit
) {
    var showIngredientSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val infoDialog = remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()

    val controlPoint = remember { mutableStateOf(true) }
    val hasVideoLoaded = remember { mutableStateOf(false) }

    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f

    val triggerIngredientBottomSheetModal: () -> Unit = {
        showIngredientSheet = !showIngredientSheet
    }

    if (screenHeightMinusBottomNavItem <= dimensionResource(id = R.dimen.dim_650)) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.Black
        )
    }

    BackHandler {
        onBackPressed()
    }
    val initialVideo = posts.filter { it.videoId == postId }.getOrNull(0)

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

        if (infoDialog.value) {
            ConfirmDeleteDialog(
                title = stringResource(id = R.string.delete_video),
                desc = stringResource(id = R.string.delete_video_message),
                onDismiss = {
                    infoDialog.value = false
                },
                onConfirm = {
                    infoDialog.value = false
                    events.deleteCurrentPost(posts[pagerState.currentPage].videoId)
                    onPostDeleted()
                }
            )
        }
        if (initialVideo != null) {
            val fling = PagerDefaults.flingBehavior(
                state = pagerState, lowVelocityAnimationSpec = tween(
                    easing = LinearEasing, durationMillis = 300
                )
            )
            if (showIngredientSheet) {
                HomeBottomSheetIngredients(
                    triggerIngredientBottomSheetModal,
                    state.recipe,
                    onAddToBasket = { events.addIngredientsToBasket()}
                )
            }
            VerticalPager(
                state = pagerState,
                flingBehavior = fling,
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

                    VideoScroller(currentVideo, pagerState, vtPager, onSingleTap = {
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

                    if (currentVideo.authorDetails == state.userProfile!!.username) {
                        DeleteButton(
                            alignment = Alignment.TopEnd,
                            onDeleteClicked = { infoDialog.value = true }
                        )
                    }
                    LikeButton(doubleTapState) {}

                    val simpleUserModel = SimpleUserModel(
                        userId = state.myUserId.toInt(),
                        username = currentVideo.authorDetails,
                        profilePictureUrl = state.userProfile.profilePictureUrl
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

        LaunchedEffect(hasVideoLoaded.value) {
            if (initialVideo != null) {
                delay(50)
                controlPoint.value = false
            }
        }
    }
}

@Composable
fun DeleteButton(
    alignment: Alignment,
    onDeleteClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                end = dimensionResource(id = R.dimen.dim_20),
                top = dimensionResource(id = R.dimen.dim_50)
            )
            .fillMaxSize()
            .wrapContentSize(align = alignment)
    ) {
        Button(
            shape = RectangleShape,
            modifier = Modifier
                .border(
                    dimensionResource(id = R.dimen.dim_1),
                    Color.Transparent,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                .align(Alignment.BottomCenter)
                .width(dimensionResource(id = R.dimen.dim_40))
                .height(dimensionResource(id = R.dimen.dim_40)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent
            ), contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_5)),
            onClick = {
                onDeleteClicked()
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = stringResource(id = R.string.delete_video),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_25))
                    .height(dimensionResource(id = R.dimen.dim_25))
            )
        }
    }
}

