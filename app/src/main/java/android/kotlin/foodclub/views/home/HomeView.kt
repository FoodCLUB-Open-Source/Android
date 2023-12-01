@file:JvmName("HomeViewKt")

package android.kotlin.foodclub.views.home

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.config.ui.light_blue
import android.kotlin.foodclub.domain.models.others.AnimatedIcon
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.utils.composables.LikeButton
import android.kotlin.foodclub.utils.composables.MemoriesItemView
import android.kotlin.foodclub.utils.composables.PlayPauseButton
import android.kotlin.foodclub.utils.composables.VideoLayout
import android.kotlin.foodclub.utils.composables.VideoScroller
import android.kotlin.foodclub.viewModels.home.HomeViewModel
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import okio.ByteString.Companion.encodeUtf8


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheetIngredients(onDismiss: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_240)
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var sliderPosition by remember { mutableStateOf(0f) }
    var isSmallScreen by remember { mutableStateOf(false) }

    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
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
                .padding(start = dimensionResource(id = R.dimen.dim_16), end = dimensionResource(id = R.dimen.dim_16))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = dimensionResource(id = R.dimen.dim_16))
                ) {
                    Text(
                        text = stringResource(id = R.string.example_recipe),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.fon_18).value.sp else dimensionResource(id = R.dimen.fon_22).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width( dimensionResource(id = R.dimen.dim_16)))
                Box(
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.dim_16), bottom = dimensionResource(id = R.dimen.dim_16))
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(
                                dimensionResource(id = R.dimen.dim_1), light_blue, shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
                            .width(dimensionResource(id = R.dimen.dim_80))
                            .height( dimensionResource(id = R.dimen.dim_30)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = light_blue
                        ),
                        contentPadding = PaddingValues(bottom =dimensionResource(id = R.dimen.dim_2)),
                        onClick = {}
                    ) {
                        Text(
                            text = stringResource(id = R.string.copy_clip),
                            fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Montserrat,
                            color = light_blue,
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (isSmallScreen) dimensionResource(id = R.dimen.dim_0) else dimensionResource(id = R.dimen.dim_16)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = if (isSmallScreen) dimensionResource(id = R.dimen.dim_16) else dimensionResource(id = R.dimen.dim_0))
                ) {
                    Text(
                        text = stringResource(id = R.string.serving_size),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.fon_14).value.sp else dimensionResource(id = R.dimen.fon_17).value.sp
                    )
                }
                Box(
                    modifier = Modifier.padding(end = if (isSmallScreen) dimensionResource(id = R.dimen.dim_10) else dimensionResource(id = R.dimen.dim_0))
                ) {
                    Slider(
                        modifier = Modifier.width(if (isSmallScreen) dimensionResource(id = R.dimen.dim_150) else dimensionResource(id = R.dimen.dim_200)),
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 0f..10f,
                        steps = 4,
                        colors = SliderDefaults.colors(
                            thumbColor = foodClubGreen,
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
                        .padding(start = dimensionResource(id = R.dimen.dim_16))
                ) {
                    Text(
                        text = stringResource(id = R.string.ingredients),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.fon_13).value.sp else dimensionResource(id = R.dimen.fon_16).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(if (isSmallScreen) dimensionResource(id = R.dimen.dim_10) else dimensionResource(id = R.dimen.dim_16)))
                Box(modifier = Modifier.padding(end = if (isSmallScreen) dimensionResource(id = R.dimen.dim_16) else dimensionResource(id = R.dimen.dim_16))) {
                    Text(
                        text = stringResource(id = R.string.clear),
                        color = foodClubGreen,
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.fon_13).value.sp else dimensionResource(id = R.dimen.fon_16).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_16)))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isSmallScreen) dimensionResource(id = R.dimen.dim_210) else dimensionResource(id = R.dimen.dim_300)),
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
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            dimensionResource(id = R.dimen.dim_1), Color(126, 198, 11), RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                        )
                        .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                        .fillMaxWidth(),
                    colors = defaultButtonColors(),
                    contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
                    onClick = {}
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_my_shopping_list),
                        color = Color.White,
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
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
                    radiusX = dimensionResource(id = R.dimen.dim_10), radiusY = dimensionResource(id = R.dimen.dim_10)
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
                    radiusX = dimensionResource(id = R.dimen.dim_10), radiusY = dimensionResource(id = R.dimen.dim_10)
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
    viewModel: HomeViewModel,
    initialPage: Int? = 0,
    navController: NavHostController,
    triggerStoryView: () -> Unit
) {
    var showIngredientSheet by remember { mutableStateOf(false) }
    val localDensity = LocalDensity.current

    val videosState = viewModel.postListData.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f

    if (screenHeightMinusBottomNavItem <= dimensionResource(id = R.dimen.dim_650)) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }
    val memories = viewModel.memoryListData.collectAsState()
    var showStories by remember {
        mutableStateOf(false)
    }
    var currentMemoriesModel by remember{
        mutableStateOf(MemoriesModel(listOf(),""))
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
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dim_95))
                .alpha(0.4f)
                .background(color = Color(0xFF424242)))
            if(showStories){
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = "",
                    modifier = Modifier.
                    align(Alignment.BottomStart)
                        .alpha(feedTransparency)
                        .padding(start = dimensionResource(id = R.dimen.dim_22), bottom = dimensionResource(id = R.dimen.dim_18))
                        .clickable {
                            showStories=!showStories
                        }
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = dimensionResource(id = R.dimen.dim_10))
            ) {
                Text(
                    modifier = modifier
                        .alpha(feedTransparency)
                        .clickable {
                            showFeedOnUI = true
                            snapsTransparency = 0.7f
                            feedTransparency = 1f
                        },
                    text = "Feed",
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    style = TextStyle(color = Color.White),
                    lineHeight = 21.94.sp,
                    fontWeight = if (showFeedOnUI) FontWeight.Bold else FontWeight.Medium
                )
                Text(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_8))
                        .alpha(0.7f),
                    text = "|",
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
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
                    text = "Snaps",
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    style = TextStyle(color = Color.White),
                    lineHeight = 21.94.sp,
                    fontWeight = if (!showFeedOnUI) FontWeight.Bold else FontWeight.Medium
                )
            }
    }

    Column(modifier = Modifier
        .height(screenHeightMinusBottomNavItem)
        .padding(bottom = 10.dp)
    ) {
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
                    val iconSize = dimensionResource(id = R.dimen.dim_110)
                    val doubleTapState by remember {
                        mutableStateOf(
                            AnimatedIcon(R.drawable.liked, iconSize = iconSize, localDensity)
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
        }else{

           if(showStories){
               SnapsView(memoriesModel = currentMemoriesModel, modifier = Modifier)
           }
            else{
               Column(
                   modifier = Modifier
                       .background(color = Color.White)
                       .fillMaxSize()
                       .padding(dimensionResource(id = R.dimen.dim_24))
               ) {
                   Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.dim_90)))
                   Text(
                       text="Memories",
                       style = TextStyle(
                           fontWeight = FontWeight.Bold,
                           color = Color.Black,
                           fontSize = dimensionResource(id = R.dimen.fon_24).value.sp,
                           fontFamily = Montserrat
                       )
                   )
                    Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.dim_12)))

                   if(memories.value.isEmpty()){
                       MemoriesItemView(
                           modifier = Modifier.clickable {
                               showStories=!showStories
                           },
                           painter = painterResource(id = R.drawable.nosnapsfortheday),
                           date = "")
                   }
                   else{
                       LazyRow(){
                           items(memories.value){
                                   val painter: Painter =  rememberImagePainter(data = it.stories[0].imageUrl)
                               MemoriesItemView(
                                   modifier = Modifier.clickable {
                                       showStories=!showStories
                                       currentMemoriesModel = it
                                   },
                                   painter = painter,
                                   date = it.dateTime)
                               Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_12)))
                           }
                        }
                    }
                    TapToSnapDialog(modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = dimensionResource(id = R.dimen.dim_12))
                        .clickable {
                            navController.navigate("TAKE_SNAP_VIEW")
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
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_18)))
            .border(width =dimensionResource(id = R.dimen.dim_1), color = Color.Black, shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_18)))
    ) {
        Image(
            painter = painterResource(id = R.drawable.taptosnapbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black, BlendMode.Overlay),
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    radiusX = dimensionResource(id = R.dimen.dim_50),
                    radiusY = dimensionResource(id = R.dimen.dim_50),
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
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                lineHeight = 26.sp
            ),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_28))
        )
        Text(
            text = stringResource(id = R.string.tap_to_snap),
            style = TextStyle(
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                lineHeight = dimensionResource(id = R.dimen.fon_24).value.sp
            ),
            modifier = Modifier
                .padding( dimensionResource(id = R.dimen.dim_32))
                .align(Alignment.BottomEnd)
        )
    }
}


@Composable
fun HomeIngredient(ingredientTitle: String, ingredientImage: Int) {
    var isSelected by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_240)
    var isSmallScreen by remember { mutableStateOf(false) }

    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isSmallScreen) dimensionResource(id = R.dimen.dim_100) else dimensionResource(id = R.dimen.dim_130))
            .border(dimensionResource(id = R.dimen.dim_1), Color(0xFFE8E8E8), RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
            .background(Color.White)
            .padding(dimensionResource(id = R.dimen.dim_10))
    ) {
        Image(
            painter = painterResource(id = ingredientImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dim_110))
                .width(if (isSmallScreen) dimensionResource(id = R.dimen.dim_85) else dimensionResource(id = R.dimen.dim_100))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
        )
        Box(
            modifier = Modifier
                .size( dimensionResource(id = R.dimen.dim_35))
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_30)))
                .background(
                    if (isSelected) foodClubGreen
                    else Color(0xFFECECEC)
                )
                .clickable { isSelected = !isSelected }
                .padding(dimensionResource(id = R.dimen.dim_4)),
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
                .padding(start = if (isSmallScreen) dimensionResource(id = R.dimen.dim_90) else dimensionResource(id = R.dimen.dim_110), top = dimensionResource(id = R.dimen.dim_10))
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_115))
                    .padding(start = dimensionResource(id = R.dimen.dim_10))
            ) {
                Text(
                    text = ingredientTitle,
                    lineHeight = dimensionResource(id = R.dimen.fon_18).value.sp,
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
                            .size(dimensionResource(id = R.dimen.dim_50))
                            .padding(end = dimensionResource(id = R.dimen.dim_15))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
                            .clickable { }
                    )
                    Text(
                        text = stringResource(id = R.string.weight_placeholder),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_50))
                            .padding(start = dimensionResource(id = R.dimen.dim_15))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
                            .clickable { }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(if (isSmallScreen) dimensionResource(id = R.dimen.dim_10) else 20.dp))
}