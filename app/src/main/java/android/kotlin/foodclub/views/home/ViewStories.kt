package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.di.SharedPreferencesModule.provideSharedPreferences
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.utils.composables.VideoScroller
import android.kotlin.foodclub.viewModels.home.HomeViewModel
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
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.content.Context
import android.content.SharedPreferences
import android.kotlin.foodclub.views.home.home.HomeState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
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

    if (screenHeightMinusBottomNavItem <= 650.dp) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }
    if (videosState.isNotEmpty()) {
        val storyPagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ){
            videosState.size
        }
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
            modifier = Modifier.height(screenHeightMinusBottomNavItem)
        ) {
            var pauseButtonVisibility by remember { mutableStateOf(false) }
            var doubleTapState by remember {
                mutableStateOf(
                    Triple(
                        Offset.Unspecified, //offset
                        false, //double tap anim start
                        0f //rotation angle
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
                    val iconSize = 110.dp
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
                        .padding(top = 30.dp),
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
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(15.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 15.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.story_user),
                                contentDescription = stringResource(id = R.string.profile_image),
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(CircleShape)
                                    .alpha(0.7f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                videosState[it].authorDetails, color = Color.White,
                                fontFamily = Montserrat, fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .alpha(0.7f)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(15.dp)
                ) {
                    Column {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(50.dp)
                                .height(50.dp)
                        ) {
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(50.dp)
                                .height(80.dp),
                        ) {
                            Spacer(Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(80.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(Color.Black.copy(alpha = 0.5f))
                                        .blur(radius = 5.dp)
                                        .alpha(0.7f)
                                ) {}
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(30.dp))
                                        .alpha(0.7f)
                                        .clickable {
                                            isLiked = !isLiked
                                            videosState[0].currentViewerInteraction.isLiked =
                                                !isLiked
                                        }
                                ) {
                                    val maxSize = 32.dp
                                    val iconSize by animateDpAsState(
                                        targetValue = if (isLiked) 22.dp else 21.dp,
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
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = videosState[it].videoStats.displayLike,
                                        fontSize = 13.sp,
                                        fontFamily = Montserrat,
                                        color = if (isLiked) foodClubGreen else Color.White
                                    )
                                }
                            }
                            Spacer(Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}