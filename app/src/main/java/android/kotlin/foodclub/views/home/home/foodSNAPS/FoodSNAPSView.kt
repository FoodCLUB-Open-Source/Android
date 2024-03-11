package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.home.VideoStats
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.views.home.home.feed.HomeState
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodSNAPSView(
    state: HomeState,
    modifier: Modifier = Modifier,
    //onShowMemoriesChanged: (Boolean) -> Unit,
    //toggleShowMemoriesReel: (Boolean) -> Unit,
    pagerState: PagerState,
    navController: NavHostController,
    selectReaction: (Reactions) -> Unit,
    clearSelectedReaction: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var currentMemoriesModel by remember {
        mutableStateOf(MemoriesModel(listOf(), ""))
    }

    val dummyListData = listOf(
        VideoModel(
            videoId = 4L,
            authorDetails = "Steve",
            videoStats = VideoStats(
                like = 100,
                comment = 50,
                share = 30,
                favourite = 10,
            ),
            videoLink = "https://is1-ssl.mzstatic.com/image/thumb/PurpleSource126/v4/1b/16/72/1b16723f-4b1d-3924-a508-6b3ebe8092d2/81d86fc8-7554-4462-94a8-332a1105378e_Screenshot2iPhone.jpg/750x750bb.jpeg",
            thumbnailLink = "https://is1-ssl.mzstatic.com/image/thumb/PurpleSource126/v4/1b/16/72/1b16723f-4b1d-3924-a508-6b3ebe8092d2/81d86fc8-7554-4462-94a8-332a1105378e_Screenshot2iPhone.jpg/750x750bb.jpeg",
            description = "This is dummy video 1",
            ),
        VideoModel(
            videoId = 5L,
            authorDetails = "Megan",
            videoStats = VideoStats(
                like = 110,
                comment = 55,
                share = 33,
                favourite = 25,
            ),
            videoLink = "https://is1-ssl.mzstatic.com/image/thumb/PurpleSource126/v4/3a/b0/ca/3ab0caac-7a5b-0ad8-efcf-81f6cc0bcb5b/3e79acef-0c61-4489-b8db-86bdb33a739c_Screenshot4iPhone.jpg/750x750bb.jpeg",
            thumbnailLink = "https://is1-ssl.mzstatic.com/image/thumb/PurpleSource126/v4/3a/b0/ca/3ab0caac-7a5b-0ad8-efcf-81f6cc0bcb5b/3e79acef-0c61-4489-b8db-86bdb33a739c_Screenshot4iPhone.jpg/750x750bb.jpeg",
            description = "This is dummy video 2",
        ),
        VideoModel(
            videoId = 5L,
            authorDetails = "Mike",
            videoStats = VideoStats(
                like = 82,
                comment = 13,
                share = 76,
                favourite = 2,
            ),
            videoLink = "https://is1-ssl.mzstatic.com/image/thumb/PurpleSource116/v4/e4/5a/a3/e45aa3df-a7d9-cd0f-91cd-532b8e77fe8d/4987efe6-41d1-4750-b4fd-84d15ad4b781_Screenshot1iPhone.jpg/750x750bb.jpeg",
            thumbnailLink = "https://is1-ssl.mzstatic.com/image/thumb/PurpleSource116/v4/e4/5a/a3/e45aa3df-a7d9-cd0f-91cd-532b8e77fe8d/4987efe6-41d1-4750-b4fd-84d15ad4b781_Screenshot1iPhone.jpg/750x750bb.jpeg",
            description = "This is dummy video 3",
        ),
        )
    val storyListData = dummyListData // test with dummyListData or state.videoList

    val snapPagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { storyListData.size }
    )


    BackHandler {
//        if (state.showMemories){
//            onShowMemoriesChanged(false)
//        }else
            if (snapPagerState.currentPage != 0){
            scope.launch {
                snapPagerState.scrollToPage(0)
            }
        }else{
            scope.launch {
                pagerState.animateScrollToPage(
                    0,
                    animationSpec = tween(1, easing = LinearEasing)
                )
            }
        }
    }

    if (state.showMemories) {
        SnapsView(memoriesModel = currentMemoriesModel)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
//            AnimatedVisibility(visible = state.showMemoriesReel) {
//                MemoriesView(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight(),
//                    storyListEmpty = storyListData.isEmpty(),
//                    state = state,
//                    onShowMemoriesChanged = { _ ->
//                        onShowMemoriesChanged(true)
//                    },
//                    updateCurrentMemoriesModel = { currentMemoriesModel = it }
//                )
//            }

            FoodSNAPSPager(
                storyListData = storyListData,
                navController = navController,
                //showMemoriesReel = state.showMemoriesReel,
                //changeMemoriesReelVisibility = { toggleShowMemoriesReel(it) },
                selectedReaction = state.selectedReaction,
                selectReaction = selectReaction,
                clearSelectedReactions = clearSelectedReaction,
                snapPagerState = snapPagerState,
            )
        }
    }
}

