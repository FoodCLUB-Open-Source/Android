package android.kotlin.foodclub.views.home

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.Satoshi
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import android.kotlin.foodclub.viewmodels.home.DiscoverViewModel
import androidx.compose.runtime.mutableIntStateOf
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoverView(navController: NavController) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 240.dp

    var isSmallScreen by remember { mutableStateOf(false) }
    if (screenHeight <= 440.dp) { isSmallScreen = true }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White, darkIcons = true
        )
    }

    val viewModel: DiscoverViewModel = hiltViewModel()

    viewModel.getPostsByWorld(197)
    viewModel.getPostsByUserId()
    viewModel.myFridgePosts()

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, end = 20.dp, start = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    color = Color.Black,
                    text = "Hi Emily,",
                    fontFamily = Montserrat,
                    fontSize = if (isSmallScreen) 22.sp else 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 7.dp),
                    style = TextStyle(letterSpacing = -1.sp)
                )

                Text(
                    color = Color.Black,
                    text = "Let's get cooking!",
                    fontFamily = Montserrat,
                    fontSize = if (isSmallScreen) 20.sp else 23.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(letterSpacing = -1.sp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Button(shape = CircleShape,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(40.dp)
                        .width(40.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(245, 245, 245, 255),

                        ),
                    contentPadding = PaddingValues(),

                    onClick = {

                        navController.navigate("SEARCH_VIEW")

                    }

                ) {

                    Image(
                        painter = painterResource(id = R.drawable.frame_1171275072),
                        contentDescription = "",
                    )

                }

                Button(
                    shape = CircleShape,
                    modifier = Modifier.clip(CircleShape).height(40.dp).width(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(245, 245, 245, 255),
                        ),
                    contentPadding = PaddingValues(),
                    onClick = { navController.navigate("BASKET_VIEW") }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vector__1_),
                        contentDescription = ""
                        )
                }
            }
        }

        val initialPage = 0
        val pagerState1 = rememberPagerState(
            initialPage = initialPage,
            initialPageOffsetFraction = 0f
        ) {
            4
        }

        val fling = PagerDefaults.flingBehavior(
            state = pagerState1, lowVelocityAnimationSpec = tween(
                easing = LinearEasing, durationMillis = 300
            )
        )

        val scope = rememberCoroutineScope()


        val tabItems1 = listOf(
            "Categories", "World", "My Fridge"
        )

        val tabItems2 = listOf(
            "Proteins", "Carbs", "Plant Based", "Drinks"
        )

        val tabItems3 = listOf(
            "England", "Italy", "France", "Germany"
        )


        var tabIndex by remember { mutableIntStateOf(0) }

        TabRow(
            selectedTabIndex = tabIndex, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White),
            containerColor = Color.White,
            contentColor = Color.White,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    height = 2.dp,
                    color = Color.Black
                )
            }
        ) {
            tabItems1.forEachIndexed { index, data ->
                val selected = tabIndex == index

                Tab(selected = selected,
                    modifier = Modifier.background(Color.White),
                    selectedContentColor = Color.Black,
                    onClick = {
                        tabIndex = index
                    }, text = {
                        Text(
                            text = data,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            color = if (selected) Color.Black else Color(0xFFC2C2C2)
                        )
                    })

            }
        }

        var homePosts: State<List<VideoModel>>? = null
        var worldPosts: State<List<VideoModel>>? = null
        var myFridgePosts: State<List<UserPosts>>? = null


        Spacer(modifier = Modifier.height(10.dp))

        if (tabIndex == 0){
            TabHomeDiscover(tabItems2, pagerState1, scope, 12.sp)
            homePosts = viewModel.postList.collectAsState()
        }

        else if (tabIndex == 1){
            TabHomeDiscover(tabItems3, pagerState1, scope, 12.sp)
            worldPosts = viewModel.postListPerCategory.collectAsState()
        }

        else if(tabIndex == 2){
            TabHomeDiscover(tabItems2, pagerState1, scope, 12.sp)
            myFridgePosts = viewModel.myFridgePosts.collectAsState()
        }



        Spacer(modifier = Modifier.height(if (tabIndex == 2) 5.dp else 0.dp))

        val systemUiController = rememberSystemUiController()
        var showSheet by remember { mutableStateOf(false) }

        val triggerBottomSheetModal: () -> Unit = {
            showSheet = !showSheet
            systemUiController.setStatusBarColor(
                color = Color(0xFFACACAC), darkIcons = true
            )
            systemUiController.setNavigationBarColor(
                color = Color.Black, darkIcons = true
            )
        }
        SideEffect {
            if (!showSheet) {
                systemUiController.setSystemBarsColor(
                    color = Color.White, darkIcons = true
                )
            }
        }
        if (showSheet) {
            IngredientsBottomSheet(triggerBottomSheetModal, viewModel.productsDatabase)
        }
        if (tabIndex == 2) {
            Button(shape = RectangleShape,
                modifier = Modifier
                    .border(1.dp, foodClubGreen, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp)).width(125.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, contentColor = foodClubGreen
                ),
                contentPadding = PaddingValues(15.dp),
                onClick = {
                    triggerBottomSheetModal()
                }) {
                Text(
                    text = "Add items +",
                    fontSize = 13.sp,
                    fontFamily = Montserrat,
                    color = Color(126, 198, 11, 255),
                )
            }
        }



        HorizontalPager(
            beyondBoundsPageCount = 1,
            flingBehavior = fling,
            modifier = Modifier.fillMaxSize().padding(top = 0.dp),
            state = pagerState1
        ) {
            Box(Modifier.fillMaxWidth()
                    .padding(top = 5.dp, start = 15.dp, end = 15.dp, bottom = 100.dp)
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2),) {
                    if(homePosts!=null){
                        items(homePosts.value) { dataItem ->
                            viewModel.getPostData(dataItem.videoId)
                            GridItem2(navController,dataItem,viewModel.sessionUserName.value)
                        }
                    }

                    else if(worldPosts!=null){
                        items(worldPosts.value) { dataItem ->
                            
                            viewModel.getPostData(dataItem.videoId)
                            GridItem2(navController,dataItem,viewModel.sessionUserName.value)
                        }
                    }

                    else if(myFridgePosts!=null){
                        items(myFridgePosts.value) { dataItem ->
                            viewModel.getPostData(dataItem.id.toLong())
                            GridItem2(navController,dataItem,viewModel.sessionUserName.value)
                        }
                    }
                }

            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabHomeDiscover(
    tabItems: List<String>, pagerState1: PagerState, scope: CoroutineScope, fontSize: TextUnit
) {

    TabRow(
        selectedTabIndex = pagerState1.currentPage,
        modifier = Modifier.background(Color.White),
        containerColor = Color.White,
        contentColor = Color.White,
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState1.currentPage]),
                height = 0.dp,
                color = Color.Black
            )
        }

    ) {
        tabItems.forEachIndexed { index, item ->
            Tab(selected = pagerState1.currentPage == index,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                selectedContentColor = Color.Black,
                onClick = {
                    scope.launch {
                        pagerState1.animateScrollToPage(index)
                    }

                },

                text = {
                    Text(

                        text = AnnotatedString(item), style = TextStyle(
                            fontWeight = if (pagerState1.currentPage == index)
                                FontWeight.Bold
                            else
                                FontWeight.Normal,
                            color = if (pagerState1.currentPage == index)
                                Color.Black
                            else
                                Color(0xFFC2C2C2),
                            fontSize = fontSize,
                            textAlign = TextAlign.Center,
                        )
                    )
                })

        }
    }
}

@Composable
fun GridItem2(navController: NavController, dataItem: VideoModel, userName:String) {
    Card(modifier = Modifier.height(272.dp).width(178.dp)
            .padding(10.dp), shape = RoundedCornerShape(15.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Image(
                painter = rememberAsyncImagePainter(dataItem.thumbnailLink),
                contentDescription = "",
                Modifier.fillMaxSize()
                    .clickable { navController.navigate("DELETE_RECIPE/${dataItem.videoId}")},
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = userName,
                    fontFamily = Satoshi,
                    color = Color.White,
                    fontSize = 15.sp
                )
                Text(
                    text =  dataItem.createdAt ,
                    fontFamily = Satoshi,
                    fontSize = 13.sp,
                    color= Color.White
                )
            }
        }

    }
}

@Composable
fun GridItem2(navController: NavController, dataItem: UserPosts, userName: String) {
    Card(modifier = Modifier.height(272.dp).width(178.dp)
            .padding(10.dp), shape = RoundedCornerShape(15.dp)
    ) {

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Image(
                painter = rememberAsyncImagePainter(dataItem.thumbnailUrl),
                contentDescription = "",
                Modifier.fillMaxSize()
                    .clickable { navController.navigate("DELETE_RECIPE/${dataItem.id}") },
                contentScale = ContentScale.FillHeight
            )
            Column(
                Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = dataItem.totalLikes.toString() ,
                    fontFamily = Satoshi,
                    color = Color.White,
                    fontSize = 15.sp
                )

            }
        }

    }
}