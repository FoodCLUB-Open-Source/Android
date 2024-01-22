package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.utils.composables.MemoriesItemView
import android.kotlin.foodclub.views.home.home.HomeState
import android.kotlin.foodclub.views.home.home.SnapReactionsView
import android.kotlin.foodclub.views.home.home.SnapStoryView
import android.kotlin.foodclub.views.home.home.TapToSnapDialog
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import okio.ByteString.Companion.encodeUtf8


@OptIn(ExperimentalFoundationApi::class, ExperimentalMotionApi::class)
@Composable
fun MemoriesView(
    modifier: Modifier,
    state : HomeState,
    navController: NavController,
    onShowStoriesChanged: (Boolean) -> Unit,
) {
    var currentMemoriesModel by remember {
        mutableStateOf(MemoriesModel(listOf(), ""))
    }

    val SCREEN_HEIGHT_PERCENTAGE_EXCLUDING_BOTTOM_NAV = 0.94f
    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * SCREEN_HEIGHT_PERCENTAGE_EXCLUDING_BOTTOM_NAV

    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.snapmotion_layout)
            .readBytes()
            .decodeToString()
    }
    val storyListData = state.storyList // use state.videoList to test

    val scrollState = rememberScrollState(initial = 0)
    val snapPagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { storyListData.size }
    )

    val ANIMATION_DURATION_SHORT = 300
    val snapPagerFling = PagerDefaults.flingBehavior(
        state = snapPagerState,
        lowVelocityAnimationSpec = tween(
            easing = LinearEasing,
            durationMillis = ANIMATION_DURATION_SHORT
        ),
    )

    var progress by remember {
        mutableFloatStateOf(0f)
    }
    val isDragged by snapPagerState.interactionSource.collectIsDraggedAsState()

    MotionLayout(
        motionScene = MotionScene(
            content = motionScene
        ),
        progress = if (!snapPagerState.canScrollBackward && snapPagerState.canScrollForward && isDragged) {
            LaunchedEffect(progress) {
                progress = 0f
                scrollState.scrollTo(0)
            }
            progress
        } else (scrollState.value / 100).toFloat(),
        modifier = Modifier
            .height(screenHeightMinusBottomNavItem)
            .fillMaxWidth()


    ) {

        Box(
            modifier = Modifier
                .layoutId(stringResource(id = R.string.parent))
                .fillMaxSize()
        )

        Spacer(
            modifier = modifier
                .size(dimensionResource(id = R.dimen.dim_90))
                .layoutId(stringResource(id = R.string.spacer))
        )
        Text(
            text = stringResource(id = R.string.memories),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontFamily = Montserrat
            ),
            modifier = Modifier.layoutId(stringResource(id = R.string.memories_text))

        )
        Spacer(
            modifier = modifier
                .size(dimensionResource(id = R.dimen.dim_12))
        )


        if (state.memories.isEmpty()) {
            MemoriesItemView(
                modifier = Modifier
                    .clickable { onShowStoriesChanged(true) }
                    .layoutId(stringResource(id = R.string.memories_item_view)),
                painter = painterResource(id = R.drawable.nosnapsfortheday),
                date = "")

        } else {
            LazyRow(
                modifier = Modifier
                    .layoutId(stringResource(id = R.string.memories_item_view))
            ) {
                items(state.memories) {
                    val painter: Painter =
                        rememberAsyncImagePainter(model = it.stories[0].imageUrl)
                    MemoriesItemView(
                        modifier = Modifier.clickable {
                            onShowStoriesChanged(true)
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

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black)
                .layoutId(stringResource(id = R.string.memories_divider))
        )
        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.dim_25))
        )

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

        if (storyListData.isEmpty()) {
            TapToSnapDialog(
                modifier = Modifier
                    .layoutId(stringResource(id = R.string.tap_to_snap_string))
                    .clickable {
                        navController.navigate("CAMERA_VIEW/${"story".encodeUtf8()}")
                    }
                    .aspectRatio(0.9f, true)
                    .background(color = Color.Red)
            )
        } else {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .layoutId(stringResource(id = R.string.stories_view))

            )
            {
                VerticalPager(
                    state = snapPagerState,
                    flingBehavior = snapPagerFling,
                    beyondBoundsPageCount = 1,
                    modifier = Modifier,
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
                            reactions = Reactions.entries.toTypedArray(),
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
                                        storyListData[it].authorDetails,
                                        color = Color.Black,
                                        fontFamily = Montserrat,
                                        fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                                        modifier = Modifier
                                            .padding(dimensionResource(id = R.dimen.dim_2))
                                            .alpha(0.7f)
                                    )
                                }

                                Text(
                                    storyListData[it].createdAt,
                                    color = Color.Black,
                                    fontFamily = Montserrat,
                                    fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
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