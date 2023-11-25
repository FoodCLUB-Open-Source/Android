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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage

/**
 * Like Button
 *
 * Like Button is actually animated Like Button which should show after double click on the video.
 * It is displayed in the location defined in [AnimatedIcon] which should be defined by location
 * of the mouse when double click is performed.
 *
 * @param animatedIcon [AnimatedIcon] containing all information including where the user clicked
 * and current state of the button (if the button should be shown or not)
 * @param onLikeExecution Executes when the animation finishes
 */
@Composable
fun LikeButton(
    animatedIcon: AnimatedIcon,
    onLikeExecution: () -> Unit
) {
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
            isAnimatingState.value
        ) {
            onLikeExecution()
        }
    }
}

/**
 * Play Pause Button
 *
 * Play Pause Button is button which should be displayed on Video state change. So when the video
 * is paused it should show up and otherwise - disappear
 *
 * @param buttonVisibility Boolean which determines if the button should be visible
 */
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

/**
 * Video layout
 *
 * Video layout including Info, category, user details (username and profile photo), like button
 * with number of likes and bookmark button. These components may be optional except user details
 *
 * @param userDetails [SimpleUserModel] containing all information about user to display
 * (username, profile picture url and id used to navigate to user profile after click)
 * @param modifier Optional [Modifier] to properly put the Layout as Box on the screen
 * @param videoStats Optional [VideoStats] object which is necessary in order to enable
 * functionalities like Bookmark or Like
 * @param likeState Optional boolean which determines if the like button should be shown.
 * If not provided, like button will not be displayed. True and false determine state of the button
 * @param bookMarkState Optional boolean which determines if the bookmark button should be shown.
 * If not provided, bookmark button will not be displayed. True and false determine state of the
 * button
 * @param category Optional category name. If not provided, no category would be displayed.
 * @param opacity Optional opacity which changes transparency level of the whole layout.
 * @param onLikeClick Executes when like button is clicked
 * @param onBookmarkClick Executes when bookmark button is clicked
 * @param onInfoClick Executes when Info button is clicked
 * @param onProfileClick Executes when profile photo or name is clicked
 * @param onCategoryClick Executes when category is clicked
 */
@Composable
fun VideoLayout(
    userDetails: SimpleUserModel,
    modifier: Modifier = Modifier,
    videoStats: VideoStats? = null,
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
            VideoCategorySection(
                category = category,
                onCategoryClick = onCategoryClick,
                onProfileClick = onProfileClick,
                userDetails = userDetails
            )
        }

        if (videoStats != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(15.dp)
            ) {
                VideoStats(
                    videoStats = videoStats,
                    likeState = likeState,
                    bookMarkState = bookMarkState,
                    onLikeClick = onLikeClick,
                    onBookmarkClick = onBookmarkClick,
                    onInfoClick = onInfoClick
                )
            }
        }

    }

}

@Composable
private fun VideoCategorySection(
    category: String?,
    onCategoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    userDetails: SimpleUserModel
) {
    Column {
        if (category != null) {
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onProfileClick() }
        ) {
            AsyncImage(
                model = userDetails.profilePictureUrl ?: defaultProfileImage,
                contentDescription = stringResource(id = R.string.profile_picture),
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

@Composable
private fun VideoStats(
    videoStats: VideoStats,
    likeState: Boolean? = null,
    bookMarkState: Boolean? = null,
    onLikeClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onInfoClick: (() -> Unit)? = null,
) {
    Column {
        if (bookMarkState != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.End)
                    .size(50.dp)
            ) {
                BookMarkButton(onBookmarkClick, bookMarkState)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (likeState != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.End)
                    .width(50.dp)
                    .height(80.dp),
            ) {
                VideoLikeButton(videoStats, likeState, onLikeClick)
            }
            Spacer(modifier = Modifier.height(10.dp))

            InfoButton(onInfoClick)
        }
    }
}

@Composable
fun BookMarkButton(
    onBookmarkClick: () -> Unit,
    bookMarkState: Boolean
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
            contentDescription = stringResource(id = R.string.bookmark)
        )
    }
}

@Composable
fun VideoLikeButton(
    videoStats: VideoStats,
    likeState: Boolean,
    onLikeClick: () -> Unit
) {
    Column {
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
}

@Composable
private fun InfoButton(onInfoClick: (() -> Unit)?) {
    if (onInfoClick != null) {
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
                    text = stringResource(id = R.string.info),
                    fontFamily = Montserrat,
                    fontSize = 14.sp
                )
            }
        }
    } else {
        Spacer(modifier = Modifier.height(35.dp))
    }
}
