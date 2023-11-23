package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.domain.models.others.AnimatedIcon
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.utils.composables.LikeButton
import android.kotlin.foodclub.utils.composables.PlayPauseButton
import android.kotlin.foodclub.utils.composables.VideoLayout
import android.kotlin.foodclub.utils.composables.VideoPlayer
import android.kotlin.foodclub.viewModels.home.ProfileViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
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
fun ConfirmDeleteDialog(
    title: String,
    desc: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(modifier = Modifier.height(450.dp)) {
            Column {
                Spacer(modifier = Modifier.height(130.dp))
                Box(modifier = Modifier
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
                            text = title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = Montserrat,
                            )
                        Spacer(modifier = Modifier.height(8.dp))


                        Text(
                            text = desc,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = Montserrat,
                            )
                        Spacer(modifier = Modifier.height(24.dp))


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
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                text = stringResource(id = R.string.yes),
                                color = Color.White,
                                fontFamily = Montserrat,
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
            )
        }
    }
}



@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun DeleteRecipeView(
    postId: Long,
    viewModel: ProfileViewModel,
    onPostDeleted: () -> Unit,
    onBackPressed: () -> Unit
) {
    val post = viewModel.postData.collectAsState()
    val userId = viewModel.myUserId.collectAsState()
    val userData = viewModel.profileModel.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val infoDialog = remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()

    val controlPoint = remember { mutableStateOf(true) }
    val hasVideoLoaded = remember { mutableStateOf(false) }

    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f

    if (screenHeightMinusBottomNavItem <= 650.dp) {
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

    Column(
        modifier = Modifier
            .height(screenHeightMinusBottomNavItem)
    ) {
        if (infoDialog.value) {
            ConfirmDeleteDialog(
                title = stringResource(id = R.string.delete_video),
                desc = stringResource(id = R.string.delete_video_message),
                onDismiss = {
                    infoDialog.value = false
                },
                onConfirm = {
                    infoDialog.value = false
                    viewModel.deleteCurrentPost(postId)
                    onPostDeleted()
                    }
            )
        }
        if(post.value != null) {
            var pauseButtonVisibility by remember { mutableStateOf(false) }
            val doubleTapState by remember { mutableStateOf(
                AnimatedIcon(R.drawable.liked, 110.dp, localDensity)
            ) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                VideoPlayer(post.value!!, onSingleTap = {
                    pauseButtonVisibility = it.isPlaying
                    it.playWhenReady = !it.isPlaying
                },
                    onDoubleTap = { exoPlayer, offset ->
                        coroutineScope.launch {
                            doubleTapState.animate(offset)
                        }
                    },
                    onVideoDispose = { pauseButtonVisibility = false },
                    onVideoGoBackground = { pauseButtonVisibility = false },
                    controlPoint = controlPoint.value
                )
                var isLiked by remember {
                    mutableStateOf(post.value!!.currentViewerInteraction.isLiked)
                }
                var isBookmarked by remember { mutableStateOf(
                    post.value!!.currentViewerInteraction.isBookmarked)
                }

                hasVideoLoaded.value = true

                if (post.value!!.authorDetails == userData.value!!.username){
                    DeleteButton(
                        alignment = Alignment.TopEnd,
                        onDeleteClicked = { infoDialog.value = true }
                    )
                }
                LikeButton(doubleTapState) {}

                val simpleUserModel = SimpleUserModel(
                    userId = userId.value.toInt(),
                    username = post.value!!.authorDetails,
                    profilePictureUrl = userData.value!!.profilePictureUrl
                    )

                PlayPauseButton(buttonVisibility = pauseButtonVisibility)
                VideoLayout(
                    userDetails = simpleUserModel,
                    videoStats = post.value!!.videoStats,
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
                    onInfoClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }

    //Fix width deformation - recompose
    LaunchedEffect(hasVideoLoaded.value) {
        if(post.value != null) {
            delay(50)
            controlPoint.value = false
        }
    }
}

@Composable
fun DeleteButton(
    alignment: Alignment,
    onDeleteClicked: () -> Unit
){
    Box(
        modifier = Modifier
            .padding(end = 20.dp, top = 50.dp)
            .fillMaxSize()
            .wrapContentSize(align = alignment)
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
                onDeleteClicked()
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = stringResource(id = R.string.delete_video),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        }
    }
}