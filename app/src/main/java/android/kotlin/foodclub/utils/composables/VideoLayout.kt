package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.config.ui.defaultProfileImage
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoStats
import android.kotlin.foodclub.domain.models.others.AnimatedIcon
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage

@Composable
fun LikeButton(animatedIcon: AnimatedIcon, onLikeExecution: () -> Unit) {
    val isAnimatingState = animatedIcon.startAnimation.collectAsState()
    AnimatedVisibility(visible = isAnimatingState.value,
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
            if (animatedIcon.dpOffset != DpOffset.Unspecified) {
                this.offset(
                    x = animatedIcon.dpOffset.x,
                    y = animatedIcon.dpOffset.y
                )
            } else this
        }) {
        Icon(
            painter = painterResource(id = animatedIcon.iconResourceId),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(animatedIcon.iconSize)
        )
    }

    LaunchedEffect(isAnimatingState.value) {
        if (animatedIcon.dpOffset != DpOffset.Unspecified &&
            isAnimatingState.value) {
            onLikeExecution()
        }
    }
}

@Composable
fun PlayPauseButton(buttonVisibility: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = buttonVisibility,
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
}

@Composable
fun VideoLayout(
    userDetails: SimpleUserModel,
    videoStats: VideoStats?,
    modifier: Modifier = Modifier,
    likeState: Boolean? = null,
    bookMarkState: Boolean? = null,
    category: String? = null,
    opacity: Float = 1f,
    onLikeClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onInfoClick: (() -> Unit)? = null,
    onProfileClick: () -> Unit = {},
    onCategoryClick: () -> Unit = {},
) {
    Box(modifier.alpha(opacity)) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(15.dp)
        ) {
            Column {
                //Category button
                if(category != null) {
                    Button(
                        modifier = Modifier
                            .width(60.dp)
                            .height(25.dp),
                        onClick = { onCategoryClick() },
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFD95978))
                    ) {
                        Text(
                            category, fontFamily = Montserrat,
                            fontSize = 12.sp, style = TextStyle(color = Color.White)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                //Profile photo and name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onProfileClick() }
                ) {
                    AsyncImage(
                        model = userDetails.profilePictureUrl ?: defaultProfileImage,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        userDetails.username, color = Color.White,
                        fontFamily = Montserrat, fontSize = 18.sp,
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }

        if(videoStats != null) {
            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(15.dp)) {
                Column {
                    //Bookmark button
                    if(bookMarkState != null) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.End)
                                .size(50.dp)
                        ) {
                            Box(modifier = Modifier
                                .size(55.dp)
                                .clickable { onBookmarkClick() }) {
                                Box(
                                    modifier = Modifier
                                        .size(55.dp)
                                        .clip(RoundedCornerShape(35.dp))
                                        .background(Color.Black.copy(alpha = 0.5f))
                                        .blur(radius = 5.dp)
                                )

                                val maxBookmarkSize = 32.dp
                                val bookmarkIconSize by animateDpAsState(
                                    targetValue = if (bookMarkState) 22.dp else 21.dp,
                                    animationSpec = keyframes {
                                        durationMillis = 400
                                        14.dp.at(50)
                                        maxBookmarkSize.at(190)
                                        16.dp.at(330)
                                        22.dp.at(400)
                                            .with(FastOutLinearInEasing)
                                    }, label = ""
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.save),
                                    tint = if (bookMarkState) foodClubGreen else Color.White,
                                    modifier = Modifier
                                        .size(bookmarkIconSize)
                                        .align(Alignment.Center)
                                        .zIndex(1f),
                                    contentDescription = "bookmark"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    //Like button
                    if(likeState != null) {
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
                                )
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(30.dp))
                                        .clickable { onLikeClick() }
                                ) {
                                    val maxSize = 32.dp
                                    val iconSize by animateDpAsState(
                                        targetValue = if (likeState) 22.dp else 21.dp,
                                        animationSpec = keyframes {
                                            durationMillis = 400
                                            14.dp.at(50)
                                            maxSize.at(190)
                                            16.dp.at(330)
                                            22.dp.at(400)
                                                .with(FastOutLinearInEasing)
                                        }, label = ""
                                    )

                                    Icon(
                                        painter = painterResource(id = R.drawable.like),
                                        contentDescription = null,
                                        tint = if (likeState) foodClubGreen else Color.White,
                                        modifier = Modifier.size(iconSize)
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = videoStats.displayLike,
                                        fontSize = 13.sp,
                                        fontFamily = Montserrat,
                                        color = if (likeState) foodClubGreen else Color.White
                                    )
                                }
                            }
                            Spacer(Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    //Info button
                    if(onInfoClick != null) {
                        Button(
                            onClick = { onInfoClick() },
                            colors = defaultButtonColors(),
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(35.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Info",
                                    fontFamily = Montserrat,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(35.dp))
                    }
                }
            }
        }

    }

}