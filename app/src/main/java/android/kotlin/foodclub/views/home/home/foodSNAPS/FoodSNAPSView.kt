package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.utils.composables.MemoriesItemView
import android.kotlin.foodclub.views.home.home.HomeState
import android.kotlin.foodclub.views.home.home.SnapReactionsView
import android.kotlin.foodclub.views.home.home.SnapStoryView
import android.kotlin.foodclub.views.home.home.TapToSnapDialog
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okio.ByteString.Companion.encodeUtf8

@OptIn(ExperimentalFoundationApi::class, ExperimentalMotionApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun FoodSNAPSView(
    state: HomeState,
    modifier: Modifier = Modifier,
    onShowStoriesChanged: (Boolean) -> Unit,
    showStories: Boolean,
    pagerState: PagerState,
    coroutineScope:CoroutineScope,
    navController: NavHostController
) {
    var currentMemoriesModel by remember {
        mutableStateOf(MemoriesModel(listOf(), ""))
    }

    val SCREEN_HEIGHT_PERCENTAGE_EXCLUDING_BOTTOM_NAV = 0.94f
    var screenHeightMinusBottomNavItem =
        LocalConfiguration.current.screenHeightDp.dp * SCREEN_HEIGHT_PERCENTAGE_EXCLUDING_BOTTOM_NAV

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

    var visible by remember { mutableStateOf(true) }

    BackHandler {
        if (showStories) {
            onShowStoriesChanged(!showStories)
        } else {
            coroutineScope.launch {
                pagerState.animateScrollToPage(
                    page = 0,
                    animationSpec = tween(1, easing = LinearEasing)
                )
            }
        }
    }

    if (showStories) {
        SnapsView(memoriesModel = currentMemoriesModel)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AnimatedVisibility(visible = visible) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(color = Color.Red)
                )
            }


            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .background(color = Color.Magenta)
                .clickable {
                    visible = !visible
                }
                .swipeable(
                    state = rememberSwipeableState(initialValue = 0),
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to 0,
                        screenHeightMinusBottomNavItem.value to 1
                    ),
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    enabled = true,
                    reverseDirection = false,
                    resistance = null,
                    velocityThreshold = 0.dp,
                )
            )



        }
    }
}

