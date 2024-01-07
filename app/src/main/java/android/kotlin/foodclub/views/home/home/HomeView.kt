@file:JvmName("HomeViewKt")

package android.kotlin.foodclub.views.home.home

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.viewModels.home.home.HomeEvents
import android.kotlin.foodclub.utils.composables.MemoriesItemView
import android.kotlin.foodclub.utils.helpers.fadingEdge
import android.kotlin.foodclub.views.home.SnapsView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import okio.ByteString.Companion.encodeUtf8


@OptIn(ExperimentalFoundationApi::class, ExperimentalMotionApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    events: HomeEvents,
    initialPage: Int? = 0,
    navController: NavHostController,
    triggerStoryView: () -> Unit,
    state: HomeState
) {
    var showIngredientSheet by remember { mutableStateOf(false) }
    val localDensity = LocalDensity.current

    val coroutineScope = rememberCoroutineScope()

    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f

    if (screenHeightMinusBottomNavItem <= dimensionResource(id = R.dimen.dim_650)) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }
    var showStories by remember {
        mutableStateOf(false)
    }
    var currentMemoriesModel by remember {
        mutableStateOf(MemoriesModel(listOf(), ""))
    }
    val systemUiController = rememberSystemUiController()

    val triggerIngredientBottomSheetModal: () -> Unit = {
        showIngredientSheet = !showIngredientSheet
    }
    val storyListData = state.storyList

    var showFeedOnUI by remember { mutableStateOf(true) }
    var feedTransparency by remember { mutableStateOf(1f) }
    var snapsTransparency by remember { mutableStateOf(0.7f) }

    BackHandler {
        if (showStories){
            showStories = !showStories
        }
        if (!showFeedOnUI) {
            showFeedOnUI = true
            snapsTransparency = 0.7f
            feedTransparency = 1f
        }
    }

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
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dim_95))
                .then(
                    if (showFeedOnUI) {
                        Modifier
                            .fadingEdge(
                                Brush.verticalGradient(
                                    0.5f to Color.Black,
                                    1f to Color.Transparent,
                                    tileMode = TileMode.Mirror
                                )
                            )
                            .alpha(0.4f)
                    } else Modifier
                )
                .background(
                    color = if (showFeedOnUI) {
                        Color.Black
                    } else {
                        Color(0xFF424242)
                    }
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            if (showStories) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .alpha(feedTransparency)
                        .padding(
                            start = dimensionResource(id = R.dimen.dim_22),
                            bottom = dimensionResource(id = R.dimen.dim_18)
                        )
                        .clickable { showStories = !showStories }
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
                    text = stringResource(id = R.string.feed),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    style = TextStyle(color = Color.White),
                    lineHeight = dimensionResource(id = R.dimen.fon_21_94).value.sp,
                    fontWeight = if (showFeedOnUI) FontWeight.Bold else FontWeight.Medium
                )
                Text(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_8))
                        .alpha(0.7f),
                    text = stringResource(id = R.string.pipe_symbol),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    style = TextStyle(color = Color.LightGray),
                    lineHeight = dimensionResource(id = R.dimen.fon_21_94).value.sp
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
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    style = TextStyle(color = Color.White),
                    lineHeight = dimensionResource(id = R.dimen.fon_21_94).value.sp,
                    fontWeight = if (!showFeedOnUI) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }

    Column(
        modifier = Modifier.height(screenHeightMinusBottomNavItem)
    ) {
        if (showIngredientSheet) {
            HomeBottomSheetIngredients(
                onDismiss = triggerIngredientBottomSheetModal,
                recipe = state.recipe,
                onAddToBasket = { events.addIngredientsToBasket()}

            )
        }
        if (showFeedOnUI) {
            VideoPager(
                videoList = state.videoList,
                initialPage = initialPage,
                events = events,
                modifier = modifier,
                localDensity = localDensity,
                onInfoClick = triggerIngredientBottomSheetModal,
                coroutineScope = coroutineScope
            )
        } else {
            val context = LocalContext.current
            val motionScene = remember {
                context.resources
                    .openRawResource(R.raw.snapmotion_layout)
                    .readBytes()
                    .decodeToString()
            }

            val scrollState = rememberScrollState(initial = 0)
            val snapPagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
            )

            val snapPagerFling = PagerDefaults.flingBehavior(
                state = snapPagerState, lowVelocityAnimationSpec = tween(
                    easing = LinearEasing, durationMillis = 300
                ),

                )
            if(showStories){
                SnapsView(memoriesModel = currentMemoriesModel, modifier = Modifier)
            }
            else{
                var progress by remember{
                    mutableStateOf(0f)
                }
                val isDragged by snapPagerState.interactionSource.collectIsDraggedAsState()

                MotionLayout(
                    motionScene = MotionScene(
                        content = motionScene
                    ),
                    progress = if(!snapPagerState.canScrollBackward && snapPagerState.canScrollForward && isDragged){
                        LaunchedEffect(progress) {
                            progress = 0f
                            scrollState.scrollTo(0)
                        }
                        progress
                    } else (scrollState.value/100).toFloat(),
                    modifier = Modifier
                        .height(screenHeightMinusBottomNavItem)
                        .fillMaxWidth()

                ) {
                    Box(modifier = Modifier
                        .layoutId(stringResource(id = R.string.parent))
                        .fillMaxSize())

                    Spacer(
                        modifier = modifier
                            .size(dimensionResource(id = R.dimen.dim_90))
                            .layoutId(stringResource(id = R.string.spacer))
                    )
                    Text(
                        text= stringResource(id = R.string.memories),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                            fontFamily = Montserrat
                        ),
                        modifier = Modifier.layoutId(stringResource(id = R.string.memories_text))

                    )
                    Spacer(
                        modifier = modifier.size(dimensionResource(id = R.dimen.dim_12))
                    )


                    if(state.memories.isEmpty()){
                        MemoriesItemView(
                            modifier = Modifier
                                .clickable {
                                    showStories = !showStories
                                }
                                .layoutId(stringResource(id = R.string.memories_item_view))

                            ,
                            painter = painterResource(id = R.drawable.nosnapsfortheday),
                            date = "")

                    }
                    else{
                        LazyRow(
                            modifier = Modifier
                                .layoutId(stringResource(id = R.string.memories_item_view))
                        ){
                            items(state.memories){
                                val painter: Painter =  rememberImagePainter(data = it.stories[0].imageUrl)
                                MemoriesItemView(
                                    modifier = Modifier.clickable {
                                        showStories=!showStories
                                        currentMemoriesModel = it
                                    },
                                    painter = painter,
                                    date = it.dateTime
                                )
                                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_12)))

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_5)))
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black)
                        .layoutId(stringResource(id = R.string.memories_divider))
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_25)))
                    Text(
                        text = stringResource(id = R.string.today),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                            fontFamily = Montserrat,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .layoutId(stringResource(id = R.string.today_text))
                    )

                    if(storyListData.isEmpty()){
                        TapToSnapDialog(modifier =
                        Modifier
                            .layoutId(stringResource(id = R.string.tap_to_snap_string))
                            .clickable {
                                navController.navigate("CAMERA_VIEW/${"story".encodeUtf8()}")
                            }
                            .aspectRatio(0.9f, true)
                        )
                    }
                    else {

                        SnapStoryView(
                            storyListData = storyListData,
                            modifier = Modifier
                                .scrollable(
                                    state = scrollState,
                                    reverseDirection = true,
                                    orientation = Orientation.Vertical,
                                )
                                .layoutId(stringResource(id = R.string.snap_story_view))
                        )
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .layoutId(stringResource(id = R.string.stories_view))

                        )
                        {
                            VerticalPager(
                                state = snapPagerState,
                                flingBehavior = snapPagerFling,
                                beyondBoundsPageCount = 1,
                                modifier = Modifier,
                                pageCount = storyListData.size
                            ) {
                                Box {
                                    AsyncImage(
                                        model = storyListData[it].thumbnailLink,
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    SnapReactionsView(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .padding(bottom = dimensionResource(id = R.dimen.dim_150)),
                                        reactions = Reactions.values(),
                                        painter = rememberAsyncImagePainter(
                                            model = storyListData[it].thumbnailLink
                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(dimensionResource(id = R.dimen.dim_15))
                                    ) {

                                        Column(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_15))
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.story_user),
                                                    contentDescription = stringResource(id = R.string.profile_image),
                                                    modifier = Modifier
                                                        .size(dimensionResource(id = R.dimen.dim_35))
                                                        .clip(CircleShape)
                                                        .alpha(0.7f)
                                                )
                                                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))
                                                Text(
                                                    storyListData[it].authorDetails, color = Color.Black,
                                                    fontFamily = Montserrat, fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                                                    modifier = Modifier
                                                        .padding(dimensionResource(id = R.dimen.dim_2))
                                                        .alpha(0.7f)
                                                )
                                            }

                                            Text(
                                                storyListData[it].createdAt, color = Color.Black,
                                                fontFamily = Montserrat, fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier
                                                    .padding(dimensionResource(id = R.dimen.dim_2))
                                                    .alpha(0.7f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}