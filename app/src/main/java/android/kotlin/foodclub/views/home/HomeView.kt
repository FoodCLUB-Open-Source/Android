@file:JvmName("HomeViewKt")

package com.example.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.utils.composables.VideoScroller
import android.kotlin.foodclub.views.home.StoriesContainerView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalDensity
import coil.compose.AsyncImage
import coil.request.ImageRequest


val montserratFamily = FontFamily(Font(R.font.montserratregular, FontWeight.Normal))

@Composable
fun BlurImage(content: @Composable () -> Unit) {
    Box {
        content()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .drawWithContent {
                    val path = Path()
                    path.addRoundRect(
                        RoundRect(
                            left = size.width / 1.25f, top = size.height / 1.425f,
                            right = size.width / 1.05f, bottom = size.height / 1.27f,
                            topLeftCornerRadius = CornerRadius(100f, 100f),
                            topRightCornerRadius = CornerRadius(100f, 100f),
                            bottomLeftCornerRadius = CornerRadius(100f, 100f),
                            bottomRightCornerRadius = CornerRadius(100f, 100f),
                        ),
                    )
                    clipPath(path) {
                        this@drawWithContent.drawContent()
                    }
                }
                .blur(
                    radiusX = 10.dp, radiusY = 10.dp
                )
        ) {
            content()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .drawWithContent {
                    val path = Path()
                    path.addRoundRect(
                        RoundRect(
                            left = size.width / 1.05f, top = size.height / 1.255f,
                            right = size.width / 1.25f, bottom = size.height / 1.11f,
                            topLeftCornerRadius = CornerRadius(100f, 100f),
                            topRightCornerRadius = CornerRadius(100f, 100f),
                            bottomLeftCornerRadius = CornerRadius(100f, 100f),
                            bottomRightCornerRadius = CornerRadius(100f, 100f),
                        ),
                    )
                    clipPath(path) {
                        this@drawWithContent.drawContent()
                    }
                }
                .blur(
                    radiusX = 10.dp, radiusY = 10.dp
                )
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    initialPage: Int? = 0,
    navController: NavHostController,
)
{
    val viewModel: HomeViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    val localDensity = LocalDensity.current

    val videos: List<VideoModel> = viewModel.videosList
    val coroutineScope = rememberCoroutineScope()

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.95f
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
    Box(modifier = Modifier.padding(top = 60.dp).zIndex(1f)) {
        StoriesContainerView(stories = listOf(
            R.drawable.story_user,
            R.drawable.story_user,
            R.drawable.story_user,
            R.drawable.story_user
        ), navController)
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
                //BlurImage{
                    VideoScroller(videos[it], pagerState, it, onSingleTap = {
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
                //}


                var isLiked by remember {
                    mutableStateOf(videos[it].currentViewerInteraction.isLikedByYou)
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
                                    doubleTapState.first.x.toInt().toDp().plus(-iconSize.div(2))
                                }, y = localDensity.run {
                                    doubleTapState.first.y.toInt().toDp().plus(-iconSize.div(2))
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(65.dp)
                                .height(65.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(65.dp)
                                    .height(65.dp)
                            ) {
                                Box(
                                    modifier = Modifier.width(65.dp)
                                        .height(65.dp)
                                        .clip(RoundedCornerShape(35.dp))
                                        .background(Color.Black.copy(alpha = 0.8f))
                                        .blur(radius = 5.dp)
                                ) {}
                                Image(
                                    painter = painterResource(id = R.drawable.save),
                                    modifier = Modifier
                                        .size(25.dp)
                                        .align(Alignment.Center)
                                        .zIndex(1f),
                                    contentDescription = "save"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.End)
                                .width(60.dp).height(80.dp),
                            ) {
                            Spacer(Modifier.weight(1f))
                            Box(
                                modifier = Modifier.width(60.dp).height(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier.width(60.dp).height(80.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(Color.Black.copy(alpha = 0.8f))
                                        .blur(radius = 5.dp)
                                ) {}
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize().clickable {
                                        isLiked = !isLiked
                                        videos[it].currentViewerInteraction.isLikedByYou = !isLiked
                                    }
                                ) {
                                    val maxSize = 32.dp
                                    val iconSize by animateDpAsState(targetValue = if (isLiked) 22.dp else 21.dp,
                                        animationSpec = keyframes {
                                            durationMillis = 400
                                            14.dp.at(50)
                                            maxSize.at(190)
                                            16.dp.at(330)
                                            22.dp.at(400).with(FastOutLinearInEasing)
                                        })

                                    LaunchedEffect(key1 = doubleTapState) {
                                        if (doubleTapState.first != Offset.Unspecified && doubleTapState.second) {
                                            isLiked = doubleTapState.second
                                        }
                                    }
                                    Icon(
                                        painter = painterResource(id = R.drawable.like),
                                        contentDescription = null,
                                        tint = if (isLiked) Color(android.graphics.Color.parseColor("#7EC60B")) else Color.White,
                                        modifier = Modifier.size(iconSize)
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text("4.2k", fontSize = 13.sp, color = if (isLiked) Color(android.graphics.Color.parseColor("#7EC60B")) else Color.White)
                                }
                            }
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