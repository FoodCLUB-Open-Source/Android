@file:JvmName("HomeViewKt")

package android.kotlin.foodclub.views.home.home.feed

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.snapsTopbar
import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.utils.helpers.fadingEdge
import android.kotlin.foodclub.viewModels.home.home.HomeEvents
import android.kotlin.foodclub.views.home.home.foodSNAPS.FoodSNAPSView
import android.kotlin.foodclub.viewModels.home.home.HomeViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    events: HomeEvents,
    initialPage: Int? = 0,
    navController: NavHostController,
    triggerStoryView: () -> Unit,
    state: HomeState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var showIngredientSheet by remember { mutableStateOf(false) }
    val localDensity = LocalDensity.current

    val coroutineScope = rememberCoroutineScope()

    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f

    if (screenHeightMinusBottomNavItem <= dimensionResource(id = R.dimen.dim_650)) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }

    val systemUiController = rememberSystemUiController()

    val triggerIngredientBottomSheetModal: () -> Unit = {
        showIngredientSheet = !showIngredientSheet
    }
    val pagerState = rememberPagerState(

        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { 2 }
    )
    val exoPlayer = remember(context) { viewModel.exoPlayer }


    BackHandler {

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
                    if (pagerState.currentPage == 0 || (pagerState.currentPage == 1 && !state.showMemoriesReel)) {
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
                    color = if (pagerState.currentPage == 0 || (pagerState.currentPage == 1 && !state.showMemoriesReel)) {
                        Color.Black
                    } else {
                        snapsTopbar
                    }
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            if (state.showMemories && pagerState.currentPage == 1) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(
                            start = dimensionResource(id = R.dimen.dim_22),
                            bottom = dimensionResource(id = R.dimen.dim_18)
                        )
                        .clickable { events.toggleShowMemories(show = false) }
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
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = 0,
                                    animationSpec = tween(1, easing = LinearEasing)
                                )
                            }
                        },
                    text = stringResource(id = R.string.feed),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    style = TextStyle(color = if (pagerState.currentPage == 0) Color.White else Color.LightGray),
                    lineHeight = dimensionResource(id = R.dimen.fon_21_94).value.sp,
                    fontWeight = if (pagerState.currentPage == 0) FontWeight.Bold else FontWeight.Medium
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
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = 1,
                                    animationSpec = tween(1, easing = LinearEasing)
                                )

                            }


                        },
                    text = stringResource(id = R.string.snaps),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    style = TextStyle(color = if (pagerState.currentPage == 1) Color.White else Color.LightGray),
                    lineHeight = dimensionResource(id = R.dimen.fon_21_94).value.sp,
                    fontWeight = if (pagerState.currentPage == 1) FontWeight.Bold else FontWeight.Medium
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
                onAddToBasket = { events.addIngredientsToBasket() }

            )
        }
        HorizontalPager(
            state = pagerState,
        ) { currentPage ->
            when (currentPage) {
                0 -> {
                    if (state.showMemories) {
                        events.toggleShowMemories(show = false)
                    }
                    if (state.showMemoriesReel){
                        events.toggleShowMemoriesReel(show = true)
                    }
                    VideoPager(
                        exoPlayer = exoPlayer,
                        videoList = state.videoList,
                        initialPage = initialPage,
                        events = events,
                        modifier = modifier,
                        localDensity = localDensity,
                        onInfoClick = triggerIngredientBottomSheetModal,
                        coroutineScope = coroutineScope
                    )
                }

                1 -> {
                    FoodSNAPSView(
                        state = state,
                        onShowMemoriesChanged = { newShowMemoriesValue ->
                           events.toggleShowMemories(show = newShowMemoriesValue)
                        },
                        toggleShowMemoriesReel = events::toggleShowMemoriesReel,
                        pagerState = pagerState,
                        coroutineScope = coroutineScope,
                        navController = navController,
                        selectReaction = { events. selectReaction(it)},
                        clearSelectedReaction = {events.selectReaction(Reactions.ALL)}
                    )
                }
            }
        }
    }
}
