package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.utils.composables.VideoPlayer
import android.kotlin.foodclub.viewmodels.home.DeleteRecipeViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
fun ComfirmDeleteDialog(
    title: String?="Delete video?",
    desc: String?="lorem ipsum lorem ipsum lorem ipsum lorem ipsu" +
            " lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsumm",
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .height(450.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                Box(
                    modifier = Modifier
                        .height(490.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = title!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = com.example.foodclub.views.home.montserratFamily,
                            )
                        Spacer(modifier = Modifier.height(8.dp))


                        Text(
                            text = desc!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = com.example.foodclub.views.home.montserratFamily,
                            )
                        Spacer(modifier = Modifier.height(24.dp))


                        Button(
                            onClick = onDismiss,
                            colors= ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#7EC60B"))),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Later",
                                color = Color.White,
                                fontFamily = com.example.foodclub.views.home.montserratFamily,
                                )
                        }
                        ElevatedButton(
                            onClick = onDismiss,
                            colors= ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#7EC60B"))),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                text = "Enable Location",
                                color = Color.White,
                                fontFamily = com.example.foodclub.views.home.montserratFamily,
                                )
                        }


                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            HeaderImage(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopCenter)
                /*.border(
                    border = BorderStroke(width = 5.dp, color = Color.White),
                    shape = CircleShape
                )*/
            )
        }
    }
}




@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun DeleteRecipeView(navController: NavController) {
    val viewModel: DeleteRecipeViewModel = viewModel()
    val videos: List<VideoModel> = viewModel.deleteVideoExemple
    val coroutineScope = rememberCoroutineScope()
    val screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.95f
    val localDensity = LocalDensity.current
    val infoDialog = remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.Black
        )
    }

    Column(
        modifier = Modifier
            .fillMaxHeight().padding(bottom = 40.dp)
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
        if (infoDialog.value) {
            ComfirmDeleteDialog(
                title = "Delete video?",
                desc = "Are you sure you want to delete this video? This action cannot be undone.",
                onDismiss = {
                    infoDialog.value = false
                }
            )
        }
        Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                    VideoPlayer(videos[0], onSingleTap = {
                        pauseButtonVisibility = it.isPlaying
                        it.playWhenReady = !it.isPlaying
                    },
                        onDoubleTap = { exoPlayer, offset ->
                            coroutineScope.launch {
                                videos[0].currentViewerInteraction.isLikedByYou = true
                                val rotationAngle = (-10..10).random()
                                doubleTapState = Triple(offset, true, rotationAngle.toFloat())
                                delay(400)
                                doubleTapState = Triple(offset, false, rotationAngle.toFloat())
                            }
                        },
                        onVideoDispose = { pauseButtonVisibility = false },
                        onVideoGoBackground = { pauseButtonVisibility = false }
                    )
                var isLiked by remember {
                    mutableStateOf(videos[0].currentViewerInteraction.isLikedByYou)
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
                    modifier = Modifier.background(Color.Transparent).padding(start = 10.dp, top = 60.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(1.dp, Color.Transparent, shape = RoundedCornerShape(15.dp))
                            .clip(RoundedCornerShape(15.dp))
                            .align(Alignment.BottomCenter)
                            .width(40.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent
                        ), contentPadding = PaddingValues(5.dp),
                        onClick = { navController.navigateUp() }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = "Back",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier.padding(end = 20.dp, top = 60.dp).align(Alignment.TopEnd),
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(1.dp, Color.Transparent, shape = RoundedCornerShape(15.dp))
                            .clip(RoundedCornerShape(15.dp))
                            .align(Alignment.BottomCenter)
                            .width(40.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent
                        ), contentPadding = PaddingValues(5.dp),
                        onClick = {
                                infoDialog.value = true
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "Back",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp)
                        )
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
                                        .background(Color.Transparent)
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
                                        .background(Color.Transparent)
                                        .blur(radius = 5.dp)
                                ) {}
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize().clickable {
                                        isLiked = !isLiked
                                        videos[0].currentViewerInteraction.isLikedByYou = !isLiked
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