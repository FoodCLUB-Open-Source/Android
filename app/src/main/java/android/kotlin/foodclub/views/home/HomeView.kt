@file:JvmName("HomeViewKt")

package com.example.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.utils.composables.VideoPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodclub.viewmodels.home.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    initialPage: Int? = 0,)
{
    val viewModel: HomeViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."

    val videos: List<VideoModel> = viewModel.videosList
    val coroutineScope = rememberCoroutineScope()

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.925f
    val pagerState = rememberPagerState(initialPage = initialPage ?: 0)

    val fling = PagerDefaults.flingBehavior(
        state = pagerState, lowVelocityAnimationSpec = tween(
            easing = LinearEasing, durationMillis = 300
        )
    )

    Box(
        modifier = Modifier
            .height(screenHeightMinusBottomNavItem)
    ) {

        VerticalPager(
            pageCount = 4,
            state = pagerState,
            flingBehavior = fling,
            beyondBoundsPageCount = 1,
            modifier = modifier
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                VideoPlayer(videos[it], pagerState, it, onSingleTap = {
                    pauseButtonVisibility = it.isPlaying
                    it.playWhenReady = !it.isPlaying
                },
                    onDoubleTap = { exoPlayer, offset ->
                        coroutineScope.launch {
                            videos[it].currentViewerInteraction.isLikedByYou = true
                            val rotationAngle = (-10..10).random()
                            doubleTapState = Triple(offset, true, rotationAngle.toFloat())
                            delay(400)
                            doubleTapState = Triple(offset, false, rotationAngle.toFloat())
                        }
                    },
                    onVideoDispose = { pauseButtonVisibility = false },
                    onVideoGoBackground = { pauseButtonVisibility = false }

                )
                AnimatedVisibility(
                    visible = pauseButtonVisibility,
                    enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
                    exit = scaleOut(tween(150)),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pause_video_button),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}