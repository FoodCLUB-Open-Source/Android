package live.foodclub.utils.composables.videoPager

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.defaultButtonColors
import live.foodclub.config.ui.defaultProfileImage
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.others.AnimatedIcon
import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.utils.composables.shimmerBrush
import live.foodclub.utils.helpers.checkInternetConnectivity
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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.em
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
            .padding(top = dimensionResource(id = R.dimen.dim_30)),
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
                modifier = Modifier.size(dimensionResource(id = R.dimen.dim_36))
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
 * @param opacity Optional opacity which changes transparency level of the whole layout.
 * @param onLikeClick Executes when like button is clicked
 * @param onBookmarkClick Executes when bookmark button is clicked
 * @param onInfoClick Executes when Info button is clicked
 * @param onProfileClick Executes when profile photo or name is clicked
 */
@Composable
fun VideoLayout(
    userDetails: SimpleUserModel,
    modifier: Modifier = Modifier,
    videoStats: VideoStats? = null,
    likeState: Boolean? = null,
    bookMarkState: Boolean? = null,
    title: String? = null,
    opacity: Float = 1f,
    onLikeClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onInfoClick: (() -> Unit)? = null,
    onProfileClick: (Long) -> Unit = {},
) {
    val context = LocalContext.current
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))

    val brush = shimmerBrush()
    Box(modifier.alpha(opacity)) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(dimensionResource(id = R.dimen.dim_15))
        ) {
            VideoCategorySection(
                brush,
                isInternetConnected,
                title = title,
                onProfileClick = onProfileClick,
                userDetails = userDetails
            )
        }

        if (videoStats != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        top = dimensionResource(id = R.dimen.dim_15),
                        end = dimensionResource(id = R.dimen.dim_15),
                        bottom = dimensionResource(id = R.dimen.dim_40),
                        start = dimensionResource(id = R.dimen.dim_15),
                    )
            ) {
                VideoStats(
                    brush,
                    isInternetConnected,
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
    brush: Brush,
    isInternetConnected: Boolean,
    title: String?,
    onProfileClick: (Long) -> Unit,
    userDetails: SimpleUserModel
) {

    Column {
        if (isInternetConnected) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onProfileClick(userDetails.userId) }
            ) {
                AsyncImage(
                    model = userDetails.profilePictureUrl ?: defaultProfileImage,
                    contentDescription = stringResource(id = R.string.profile_picture),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_35))
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))
                Text(
                    userDetails.username,
                    color = Color.White,
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_2))
                )
            }

            if (title != null) {
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dim_10))
                )

                Text(
                    text = if (title.length > 30) {
                        title.substring(0, 30) + "..."
                    } else title,
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.dim_48).value.sp,
                    style = TextStyle(color = Color.White),
                    letterSpacing = (-0.04).em,
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_230)),
                )
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onProfileClick(userDetails.userId) }
            ) {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_35))
                        .clip(CircleShape)
                        .background(brush)

                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))
                Box(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_2))
                        .background(brush)
                        .width(dimensionResource(id = R.dimen.dim_50))
                        .height(dimensionResource(id = R.dimen.dim_15))

                )
            }
        }
    }

}

@Composable
private fun VideoStats(
    brush: Brush,
    isInternetConnected: Boolean,
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
                    .size(dimensionResource(id = R.dimen.dim_50))
            ) {
                BookMarkButton(onBookmarkClick, bookMarkState)
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
        }

        if (likeState != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.End)
                    .width(dimensionResource(id = R.dimen.dim_50))
                    .height(dimensionResource(id = R.dimen.dim_80)),
            ) {
                VideoLikeButton(brush, isInternetConnected, videoStats, likeState, onLikeClick)
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

            InfoButton(brush, isInternetConnected, onInfoClick)
        }
    }
}

@Composable
fun BookMarkButton(
    onBookmarkClick: () -> Unit,
    bookMarkState: Boolean
) {
    val durationms1 = dimensionResource(id = R.dimen.dim_14)
    val durationms2 = dimensionResource(id = R.dimen.dim_16)
    val durationms3 = dimensionResource(id = R.dimen.dim_22)

    Box(modifier = Modifier
        .size(dimensionResource(id = R.dimen.dim_55))
        .clickable { onBookmarkClick() }) {
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dim_55))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_35)))
                .background(Color.Black.copy(alpha = 0.5f))
                .blur(radius = dimensionResource(id = R.dimen.dim_5))
        )

        val maxBookmarkSize = dimensionResource(id = R.dimen.dim_32)
        val bookmarkIconSize by animateDpAsState(
            targetValue = if (bookMarkState) dimensionResource(id = R.dimen.dim_22) else dimensionResource(
                id = R.dimen.dim_21
            ),
            animationSpec = keyframes {
                durationMillis = 400
                durationms1.at(50)
                maxBookmarkSize.at(190)
                durationms2.at(330)
                durationms3.at(400)
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun VideoLikeButton(
    brush: Brush,
    isInternetConnected: Boolean,
    videoStats: VideoStats,
    likeState: Boolean,
    onLikeClick: () -> Unit
) {
    val durationms1 = dimensionResource(id = R.dimen.dim_14)
    val durationms2 = dimensionResource(id = R.dimen.dim_16)
    val durationms3 = dimensionResource(id = R.dimen.dim_22)

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(showBottomSheet)

    val userList: List<SimpleUserModel> = List(videoStats.displayLike.toInt()) { index ->
        SimpleUserModel(
            userId = index.toLong() + 1,
            username = "User $index",
            profilePictureUrl = "null"
        )
    }

    if (showBottomSheet) {
        LikesBottomSheet(
            videoStats,
            bottomSheetState,
            userList,
        ) { showBottomSheet = false }

    }
    Column {
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
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_30)))
                    .background(Color.Black.copy(alpha = 0.5f))
                    .blur(radius = dimensionResource(id = R.dimen.dim_5))
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_30)))
                    .combinedClickable(
                        onClick = {
                            onLikeClick()
                        },
                        onLongClick = {
                            showBottomSheet = !showBottomSheet
                        },
                    ),
            ) {
                val maxSize = dimensionResource(id = R.dimen.dim_32)
                val iconSize by animateDpAsState(
                    targetValue = if (likeState) dimensionResource(id = R.dimen.dim_22) else dimensionResource(
                        id = R.dimen.dim_21
                    ),
                    animationSpec = keyframes {
                        durationMillis = 400
                        durationms1.at(50)
                        maxSize.at(190)
                        durationms2.at(330)
                        durationms3.at(400)
                            .with(FastOutLinearInEasing)
                    }, label = ""
                )
                Icon(
                    painter = painterResource(id = R.drawable.like),
                    contentDescription = null,
                    tint = if (likeState) foodClubGreen else Color.White,
                    modifier = Modifier.size(iconSize)
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_3)))
                if (isInternetConnected) {
                    Text(
                        text = videoStats.displayLike,
                        fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                        fontFamily = Montserrat,
                        color = if (likeState) foodClubGreen else Color.White
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(iconSize)
                            .background(brush)
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun InfoButton(brush: Brush, isInternetConnected: Boolean, onInfoClick: (() -> Unit)?) {

    if (onInfoClick != null) {
        Button(
            onClick = { onInfoClick() },
            colors = defaultButtonColors(),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)),
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.dim_120))
                .height(dimensionResource(id = R.dimen.dim_35)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isInternetConnected) {
                    Text(
                        text = stringResource(id = R.string.info),
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dim_101))
                            .height(dimensionResource(id = R.dimen.dim_18))
                            .background(brush)
                    )
                }
            }
        }
    } else {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_35)))
    }
}
