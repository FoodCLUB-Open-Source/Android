@file:JvmName("HomeViewKt")

package android.kotlin.foodclub.views.home

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.others.AnimatedIcon
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.utils.composables.LikeButton
import android.kotlin.foodclub.utils.composables.MemoriesItemView
import android.kotlin.foodclub.utils.composables.PlayPauseButton
import android.kotlin.foodclub.utils.composables.VideoLayout
import android.kotlin.foodclub.utils.composables.VideoScroller
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import android.kotlin.foodclub.viewModels.home.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import coil.compose.rememberImagePainter
import okio.ByteString.Companion.encodeUtf8


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheetIngredients(onDismiss: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 240.dp
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var sliderPosition by remember { mutableStateOf(0f) }
    var isSmallScreen by remember { mutableStateOf(false) }

    if (screenHeight <= 440.dp) {
        isSmallScreen = true
    }
    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .height(screenHeight)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.example_recipe),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) 18.sp else 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(
                                1.dp, Color(0xFF3A7CA8), shape = RoundedCornerShape(20.dp)
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .width(80.dp)
                            .height(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF3A7CA8)
                        ),
                        contentPadding = PaddingValues(bottom = 2.dp),
                        onClick = {}
                    ) {
                        Text(
                            text = stringResource(id = R.string.copy_clip),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Montserrat,
                            color = Color(0xFF3A7CA8),
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (isSmallScreen) 0.dp else 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = if (isSmallScreen) 16.dp else 0.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.serving_size),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) 14.sp else 17.sp
                    )
                }
                Box(
                    modifier = Modifier.padding(end = if (isSmallScreen) 10.dp else 0.dp)
                ) {
                    Slider(
                        modifier = Modifier.width(if (isSmallScreen) 150.dp else 200.dp),
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 0f..10f,
                        steps = 4,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF7EC60B),
                            activeTrackColor = Color.Black,
                            inactiveTrackColor = Color.Black
                        ),
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.ingredients),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) 13.sp else 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(if (isSmallScreen) 10.dp else 16.dp))
                Box(modifier = Modifier.padding(end = if (isSmallScreen) 16.dp else 16.dp)) {
                    Text(
                        text = stringResource(id = R.string.clear),
                        color = Color(0xFF7EC60B),
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) 13.sp else 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isSmallScreen) 210.dp else 300.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyColumn {
                    items(6) {
                        HomeIngredient(
                            ingredientTitle = stringResource(id = R.string.example_ingredient),
                            ingredientImage = R.drawable.salad_ingredient
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            1.dp, Color(126, 198, 11), RoundedCornerShape(15.dp)
                        )
                        .clip(RoundedCornerShape(15.dp))
                        .fillMaxWidth(),
                    colors = defaultButtonColors(),
                    contentPadding = PaddingValues(15.dp),
                    onClick = {}
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_my_shopping_list),
                        color = Color.White,
                        fontFamily = Montserrat,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
fun BlurImage(content: @Composable () -> Unit) {
    Box {
        content()
        Box(
            modifier = Modifier
                .fillMaxSize()
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

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    initialPage: Int? = 0,
    navController: NavHostController,
    triggerStoryView: () -> Unit
) {
    var showIngredientSheet by remember { mutableStateOf(false) }

    val viewModel: HomeViewModel = hiltViewModel()
    val localDensity = LocalDensity.current

    val videosState = viewModel.postListData.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f

    if (screenHeightMinusBottomNavItem <= 650.dp) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }

    val systemUiController = rememberSystemUiController()

    val triggerIngredientBottomSheetModal: () -> Unit = {
        showIngredientSheet = !showIngredientSheet
    }

    var showFeedOnUI by remember { mutableStateOf(true) }
    var feedTransparency by remember { mutableFloatStateOf(1f) }
    var snapsTransparency by remember { mutableFloatStateOf(0.7f) }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.White
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
                .alpha(0.4f)
                .background(color = Color(0xFF424242))
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        ) {
            Text(
                modifier = modifier
                    .alpha(feedTransparency)
                    .clickable {
                        showFeedOnUI = true
                        snapsTransparency = 0.7f
                        feedTransparency = 1f
                    },
                text = stringResource(id = R.string.feed),
                fontFamily = Montserrat,
                fontSize = 18.sp,
                style = TextStyle(color = Color.White),
                lineHeight = 21.94.sp,
                fontWeight = if (showFeedOnUI) FontWeight.Bold else FontWeight.Medium
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(0.7f),
                text = stringResource(id = R.string.vertical_line),
                fontFamily = Montserrat,
                fontSize = 18.sp,
                style = TextStyle(color = Color.LightGray),
                lineHeight = 21.94.sp
            )
            Text(
                modifier = modifier
                    .alpha(snapsTransparency)
                    .clickable {
                        feedTransparency = 0.7f
                        snapsTransparency = 1f
                        showFeedOnUI = false
                    },
                text = stringResource(id = R.string.snaps),
                fontFamily = Montserrat,
                fontSize = 18.sp,
                style = TextStyle(color = Color.White),
                lineHeight = 21.94.sp,
                fontWeight = if (!showFeedOnUI) FontWeight.Bold else FontWeight.Medium
            )
        }
    }

    Column(modifier = Modifier.height(screenHeightMinusBottomNavItem)) {
        if (showIngredientSheet) {
            HomeBottomSheetIngredients(triggerIngredientBottomSheetModal)
        }
        if (showFeedOnUI) {
            if (videosState.value.isNotEmpty()) {
                val pagerState = rememberPagerState(
                    initialPage = initialPage ?: 0,
                    initialPageOffsetFraction = 0f
                ) {
                    videosState.value.size
                }

                val fling = PagerDefaults.flingBehavior(
                    state = pagerState, lowVelocityAnimationSpec = tween(
                        easing = LinearEasing, durationMillis = 300
                    )
                )

                var videoViewed by remember { mutableStateOf(false) }

                LaunchedEffect(pagerState.currentPage) { videoViewed = false }
                LaunchedEffect(videoViewed) {
                    if (videoViewed) {
                        viewModel.userViewsPost(videosState.value[pagerState.currentPage].videoId)
                    }
                }

                VerticalPager(
                    state = pagerState,
                    flingBehavior = fling,
                    beyondBoundsPageCount = 1,
                    modifier = modifier
                ) {
                    var pauseButtonVisibility by remember { mutableStateOf(false) }
                    val doubleTapState by remember {
                        mutableStateOf(
                            AnimatedIcon(R.drawable.liked, 110.dp, localDensity)
                        )
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        val currentVideo = videosState.value[it]
                        val authorDetails = SimpleUserModel(
                            userId = 1,
                            username = currentVideo.authorDetails,
                            profilePictureUrl = null
                        )
                        var isLiked by remember {
                            mutableStateOf(currentVideo.currentViewerInteraction.isLiked)
                        }
                        var isBookmarked by remember {
                            mutableStateOf(
                                currentVideo.currentViewerInteraction.isBookmarked
                            )
                        }

                        VideoScroller(currentVideo, pagerState, it, onSingleTap = {
                            pauseButtonVisibility = it.isPlaying
                            it.playWhenReady = !it.isPlaying
                        },
                            onDoubleTap = { exoPlayer, offset ->
                                coroutineScope.launch {
                                    doubleTapState.animate(offset)
                                }
                            },
                            onVideoDispose = {
                                pauseButtonVisibility = false
                                videoViewed = true
                            },
                            onVideoGoBackground = { pauseButtonVisibility = false }
                        )



                        LikeButton(doubleTapState) {
                            isLiked = !isLiked
                            coroutineScope.launch {
                                viewModel.updatePostLikeStatus(currentVideo.videoId, isLiked)
                            }
                        }
                        PlayPauseButton(buttonVisibility = pauseButtonVisibility)
                        VideoLayout(
                            userDetails = authorDetails,
                            videoStats = currentVideo.videoStats,
                            likeState = isLiked,
                            bookMarkState = isBookmarked,
                            category = stringResource(id = R.string.meat),
                            opacity = 0.7f,
                            onLikeClick = {
                                isLiked = !isLiked
                                coroutineScope.launch {
                                    viewModel.updatePostLikeStatus(currentVideo.videoId, isLiked)
                                }
                            },
                            onBookmarkClick = {
                                isBookmarked = !isBookmarked
                                coroutineScope.launch {
                                    viewModel.updatePostBookmarkStatus(
                                        currentVideo.videoId,
                                        isBookmarked
                                    )
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
        } else {
            val memories = viewModel.storyListData.collectAsState()
            var showStories by remember {
                mutableStateOf(false)
            }
            if (showStories) {
                ViewStories()
            } else {
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Spacer(modifier = modifier.size(90.dp))
                    Text(
                        text = stringResource(id = R.string.memories),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontFamily = Montserrat
                        )
                    )

                    Spacer(modifier = modifier.size(12.dp))

                    if (memories.value.isEmpty()) {
                        MemoriesItemView(
                            modifier = Modifier.clickable {
                                showStories = !showStories
                            },
                            painter = painterResource(id = R.drawable.nosnapsfortheday),
                            date = ""
                        )
                    } else {
                        LazyRow() {
                            items(memories.value) {
                                val painter: Painter = rememberImagePainter(data = it.thumbnailLink)
                                MemoriesItemView(
                                    modifier = Modifier.clickable {
                                        showStories = !showStories
                                    },
                                    painter = painter,
                                    date = it.createdAt
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }

                        }
                    }
                    TapToSnapDialog(modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = 12.dp)
                        .clickable {
                            navController.navigate("CAMERA_VIEW/${"story".encodeUtf8()}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TapToSnapDialog(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(18.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.taptosnapbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black, BlendMode.Overlay),
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    radiusX = 50.dp,
                    radiusY = 50.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
        )

        Image(
            painter = painterResource(id = R.drawable.taptosnaphand),
            contentDescription = stringResource(id = R.string.hand),
            modifier = Modifier.align(Alignment.BottomStart)
        )
        Text(
            text =stringResource(id = R.string.tap_to_snap_subheading),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                lineHeight = 26.sp
            ),
            modifier = Modifier.padding(28.dp)
        )
        Text(
            text = stringResource(id = R.string.tap_to_snap),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp
            ),
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomEnd)
        )
    }
}


@Composable
fun HomeIngredient(ingredientTitle: String, ingredientImage: Int) {
    var isSelected by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 240.dp
    var isSmallScreen by remember { mutableStateOf(false) }

    if (screenHeight <= 440.dp) {
        isSmallScreen = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isSmallScreen) 100.dp else 130.dp)
            .border(1.dp, Color(0xFFE8E8E8), RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = ingredientImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(110.dp)
                .width(if (isSmallScreen) 85.dp else 100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Box(
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(30.dp))
                .background(
                    if (isSelected) foodClubGreen
                    else Color(0xFFECECEC)
                )
                .clickable { isSelected = !isSelected }
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.check),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Box(
            modifier = Modifier
                .padding(start = if (isSmallScreen) 90.dp else 110.dp, top = 10.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .width(115.dp)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = ingredientTitle,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    fontWeight = FontWeight.Normal,
                    fontFamily = Montserrat
                )
            }
            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 15.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { }
                    )
                    Text(
                        text = stringResource(id = R.string.weight_placeholder),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = 14.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 15.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(if (isSmallScreen) 10.dp else 20.dp))
}