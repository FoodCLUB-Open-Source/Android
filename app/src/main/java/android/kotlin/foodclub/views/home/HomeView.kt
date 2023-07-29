@file:JvmName("HomeViewKt")

package com.example.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.utils.composables.VideoPlayer
import android.kotlin.foodclub.views.home.StoryView
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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodclub.viewmodels.home.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp

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


    Column(
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
                Column(
                    modifier = Modifier
                        .fillMaxSize().padding(top = 30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = pauseButtonVisibility,
                        enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
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
                        .padding(20.dp)
                ) {
                    Column {
                        Button(
                            modifier = Modifier.width(78.dp).height(32.dp),
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#D95978")))
                        ) {
                            Text("Meat", fontSize = 12.sp,style = TextStyle(color = Color.White))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.story_user),
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Marc", color = Color.White, fontSize = 18.sp,
                                modifier = Modifier.padding(2.dp))
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp)
                ) {
                    Column {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.End)
                                .width(65.dp).height(65.dp)
                                .clip(shape = RoundedCornerShape(35.dp))
                                .background(Color.Black.copy(alpha = 0.9f)).blur(radiusX = 15.dp, radiusY = 15.dp)
                            ,
                        ) {
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.save),
                                modifier = Modifier.size(25.dp),
                                contentDescription = "like",
                            )
                            Spacer(Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.End)
                                .width(60.dp).height(80.dp)
                                .clip(shape = RoundedCornerShape(30.dp))
                                .background(Color.Black.copy(alpha = 0.9f))
                                ,
                            ) {
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.like),
                                modifier = Modifier.size(25.dp),
                                contentDescription = "like",
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("4.2k", fontSize = 13.sp, color = Color.White)
                            Spacer(Modifier.weight(1f))
                        }


                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#7EC60B"))),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Ingredients", fontSize = 16.sp)
                                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                            }
                        }
                    }
                }

            }
        }

    }
}