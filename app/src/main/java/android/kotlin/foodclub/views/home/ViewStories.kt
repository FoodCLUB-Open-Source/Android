package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.di.SharedPreferencesModule.provideSharedPreferences
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.utils.composables.VideoScroller
import android.kotlin.foodclub.viewModels.home.home.HomeViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.kotlin.foodclub.views.home.home.HomeState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewStories(
    viewModel: HomeViewModel,
    state : HomeState,
) {
    // green screen issue is not happening when we use postListData instead of storyListData as below:
    // val videosState = viewModel.postListData.collectAsState()
    val videosState = state.storyList

    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    val context = LocalContext.current
    val sessionCache = SessionCache(provideSharedPreferences(context))

    val userId = sessionCache.getActiveSession()?.sessionUser?.userId ?: return

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.White
        )
    }
    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f

    if (screenHeightMinusBottomNavItem <= dimensionResource(id = R.dimen.dim_650)) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }
    if (videosState.isNotEmpty()) {
        val storyPagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        )
        val storyFling = PagerDefaults.flingBehavior(
            state = storyPagerState, lowVelocityAnimationSpec = tween(
                easing = LinearEasing, durationMillis = 300
            )
        )
        var videoViewed by remember { mutableStateOf(false) }

        LaunchedEffect(storyPagerState.currentPage) { videoViewed = false }
        LaunchedEffect(videoViewed) {
            if(videoViewed) {
                viewModel.userViewsStory(videosState[storyPagerState.currentPage].videoId)
            }
        }

        VerticalPager(
            state = storyPagerState,
            flingBehavior = storyFling,
            beyondBoundsPageCount = 1,
            modifier = Modifier.height(screenHeightMinusBottomNavItem),
            pageCount = videosState.size
        ) {
            var pauseButtonVisibility by remember { mutableStateOf(false) }
            var doubleTapState by remember {
                mutableStateOf(
                    Triple(
                        Offset.Unspecified,
                        false,
                        0f
                    )
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                VideoScroller(videosState[it], storyPagerState, it, onSingleTap = {
                    pauseButtonVisibility = it.isPlaying
                    it.playWhenReady = !it.isPlaying
                },
                    onDoubleTap = { exoPlayer, offset ->
                        coroutineScope.launch {
                            videosState[it].currentViewerInteraction.isLiked =
                                true
                            val rotationAngle = (-10..10).random()
                            doubleTapState = Triple(offset, true, rotationAngle.toFloat())
                            delay(400)
                            doubleTapState = Triple(offset, false, rotationAngle.toFloat())
                        }
                    },
                    onVideoDispose = {
                        pauseButtonVisibility = false
                        videoViewed = true
                    },
                    onVideoGoBackground = { pauseButtonVisibility = false }
                )


                var isLiked by remember {
                    mutableStateOf(videosState[it].currentViewerInteraction.isLiked)
                }

                Column() {
                    val iconSize = dimensionResource(id = R.dimen.dim_110)
                    AnimatedVisibility(visible = doubleTapState.second,
                        enter = scaleIn(
                            spring(Spring.DampingRatioMediumBouncy),
                            initialScale = 1.3f
                        ),
                        exit = scaleOut(
                            tween(600), targetScale = 1.58f
                        ) + fadeOut(tween(600)) + slideOutVertically(
                            tween(600)
                        ),
                        modifier = Modifier.run {
                            if (doubleTapState.first != Offset.Unspecified) {
                                this.offset(x = localDensity.run {
                                    doubleTapState.first.x.toInt().toDp()
                                        .plus(-iconSize.div(2))
                                }, y = localDensity.run {
                                    doubleTapState.first.y.toInt().toDp()
                                        .plus(-iconSize.div(2))
                                })
                            } else this
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.liked),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(iconSize)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = dimensionResource(id = R.dimen.dim_30)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = pauseButtonVisibility,
                        enter = scaleIn(
                            spring(Spring.DampingRatioMediumBouncy),
                            initialScale = 1.5f
                        ),
                        exit = scaleOut(tween(150)),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.pause_video_button),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size( dimensionResource(id = R.dimen.dim_36))
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(dimensionResource(id = R.dimen.dim_15))
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_15))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.story_user),
                                contentDescription = stringResource(id = R.string.profile_image),
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.dim_35))
                                    .clip(CircleShape)
                                    .alpha(0.7f)
                            )
                            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))
                            Text(
                                text = videosState[it].authorDetails,
                                color = Color.White,
                                fontFamily = Montserrat,
                                fontSize = dimensionResource(id = R.dimen.dim_18).value.sp,
                                modifier = Modifier
                                    .padding(dimensionResource(id = R.dimen.dim_2))
                                    .alpha(0.7f)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding( dimensionResource(id = R.dimen.dim_15))
                ) {
                    Column {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(dimensionResource(id = R.dimen.dim_50))
                                .height(dimensionResource(id = R.dimen.dim_50))
                        ) {
                        }
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(dimensionResource(id = R.dimen.dim_50))
                                .height(dimensionResource(id = R.dimen.dim_80)),
                        ) {
                            Spacer(Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .width(dimensionResource(id = R.dimen.dim_50))
                                    .height(dimensionResource(id = R.dimen.dim_80)),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(dimensionResource(id = R.dimen.dim_50))
                                        .height(dimensionResource(id = R.dimen.dim_80))
                                        .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_30)))
                                        .background(Color.Black.copy(alpha = 0.5f))
                                        .blur(radius =dimensionResource(id = R.dimen.dim_5))
                                        .alpha(0.7f)
                                ) {}
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_30)))
                                        .alpha(0.7f)
                                        .clickable {
                                            isLiked = !isLiked
                                            videosState[0].currentViewerInteraction.isLiked =
                                                !isLiked
                                        }
                                ) {
                                    val maxSize =  dimensionResource(id = R.dimen.dim_32)
                                    val iconSize by animateDpAsState(
                                        targetValue = if (isLiked) dimensionResource(id = R.dimen.dim_22) else dimensionResource(id = R.dimen.dim_21),
                                        animationSpec = keyframes {
                                            durationMillis = 400
                                            14.dp.at(50)
                                            maxSize.at(190)
                                            16.dp.at(330)
                                            22.dp.at(400).with(FastOutLinearInEasing)
                                        }, label = ""
                                    )

                                    LaunchedEffect(key1 = doubleTapState) {
                                        if (doubleTapState.first != Offset.Unspecified && doubleTapState.second) {
                                            isLiked = doubleTapState.second
                                        }
                                    }
                                    Icon(
                                        painter = painterResource(id = R.drawable.like),
                                        contentDescription = null,
                                        tint = if (isLiked) foodClubGreen else Color.White,
                                        modifier = Modifier
                                            .size(iconSize)
                                            .alpha(0.7f)
                                    )
                                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_3)))
                                    Text(
                                        fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                                        text = videosState[it].videoStats.displayLike,
                                        fontFamily = Montserrat,
                                        color = if (isLiked) foodClubGreen else Color.White
                                    )
                                }
                            }
                            Spacer(Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
                    }
                }
            }
        }
    }
}