package android.kotlin.foodclub.views.home

import android.app.Activity
import android.kotlin.foodclub.R
import android.kotlin.foodclub.activities.MainActivity
import android.kotlin.foodclub.data.models.UserPostsModel
import android.kotlin.foodclub.ui.theme.Montserrat
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.kotlin.foodclub.viewmodels.home.ProfileViewModel
import androidx.compose.foundation.border
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileView(navController: NavController, userId: Long?) {
    val factory = EntryPointAccessors.fromActivity(LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java).profileViewModelFactory()
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModel.provideFactory(factory, userId, navController)
    )

    val error = viewModel.error.collectAsState()
    val profileModelState = viewModel.profileModel.collectAsState()
    val sessionUserId = viewModel.myUserId.collectAsState()

    if(userId != null && userId != sessionUserId.value) {
        LaunchedEffect(Unit) {
            viewModel.isFollowedByUser(sessionUserId.value, userId)
        }
    }
    val isFollowed = viewModel.isFollowedByUser.collectAsState()

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
    }
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState() { 2 };

    if(profileModelState.value == null) {
        Text(text = "Loading...")
    } else {
        val profile = profileModelState.value
        val userPosts = profile!!.userPosts
        val topCreators = profile.topCreators

        Column (modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp, start = 95.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painterResource(id = R.drawable.story_user),
                    contentDescription = "profile_picture",
                    modifier = Modifier
                        .clip(RoundedCornerShape(60.dp))
                        .height(80.dp)
                        .width(80.dp))
                Spacer(modifier = Modifier.width(40.dp))
                Button(shape = CircleShape,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(53.dp)
                        .width(53.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(255, 255, 255, 255)),
                    contentPadding = PaddingValues(),
                    onClick = { navController.navigate("SETTINGS") }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vector_1_),
                        contentDescription = "",
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 10.dp, start = 4.dp, end = 4.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontFamily = Montserrat,
                    text = profile.username,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 5.dp),
                    letterSpacing = -1.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(70.dp, Alignment.CenterHorizontally)
                ) {
                    ClickableText(
                        text = AnnotatedString(profile.totalUserFollowers.toString()),
                        onClick = {
                            navController.navigate("FOLLOWER_VIEW/${userId ?: sessionUserId.value}")
                        },
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )
                    )
                    ClickableText(
                        text = AnnotatedString(profile.totalUserFollowing.toString()),
                        onClick = {
                            navController.navigate("FOLLOWING_VIEW/${userId ?: sessionUserId.value}")
                        },
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally)
                ) {
                    Text(
                        fontFamily = Montserrat,
                        text = "Followers",
                        fontSize = 14.sp,
                        color = Color(127, 147, 141, 255),
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        fontFamily = Montserrat,
                        text = "Following",
                        fontSize = 14.sp,
                        color = Color(127, 147, 141, 255),
                        fontWeight = FontWeight.Light
                    )
                }
                if(userId != null && userId != sessionUserId.value) {
                    Spacer(modifier = Modifier.height(10.dp))
                    FollowButton(isFollowed.value, viewModel, sessionUserId.value, userId)
                }
                TabRow(selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.White,
                    contentColor = Color.White,
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            height = 2.dp,
                            color = Color.Black
                        )
                    }
                ) {
                    tabItem.forEachIndexed{
                            index,tabItem ->
                        Tab(
                            selected = index == pagerState.currentPage,
                            selectedContentColor = Color.Black,
                            //onClick = { onTabSelected(index)   },
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }, text = {
                                Text(
                                    text =  AnnotatedString(tabItem.title),
                                    style = TextStyle(
                                        fontFamily = Montserrat,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                    )
                                )
                            }
                        )
                    }
                }


                var tabItems = viewModel.getListOfMyRecipes();

                if(pagerState.currentPage == 0){
                    tabItems = viewModel.getListOfMyRecipes();
                }
                else if(pagerState.currentPage == 1){
                    tabItems = viewModel.getListOfBookmarkedRecipes();
                }

                HorizontalPager(
                    state = pagerState,
                    beyondBoundsPageCount = 10,
                ) { page ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(top = 5.dp, start = 15.dp, end = 15.dp, bottom = 110.dp)
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                        ) {
                            items(tabItems) { dataItem ->
                                GridItem(navController, dataItem)
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun GridItem(navController: NavController, dataItem: UserPostsModel){
    Card(modifier = Modifier
        .height(272.dp)
        .width(178.dp)
        .padding(10.dp)
        ,shape = RoundedCornerShape(15.dp)) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()){
            Image(painter = painterResource(id = R.drawable.salad_ingredient), contentDescription = "",
                Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("DELETE_RECIPE") }, contentScale = ContentScale.FillHeight)
        }

    }
}

@Composable
fun FollowButton(isFollowed: Boolean, viewModel: ProfileViewModel, sessionUserId: Long, userId: Long) {
    val colors = if(isFollowed)
        ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF), contentColor = Color.Black)

    else
        ButtonDefaults.buttonColors(containerColor = Color(0xFF7EC60B), contentColor = Color.White)

    val content = if(isFollowed) "Unfollow" else "Follow"

    val modifier = if(isFollowed) Modifier
        .width(130.dp)
        .height(40.dp)
        .border(1.dp, Color.Black, RoundedCornerShape(40.dp))
        .clip(RoundedCornerShape(40.dp))
    else Modifier
        .width(130.dp)
        .height(40.dp)

    Button(
        onClick = { if(isFollowed) viewModel.unfollowUser(sessionUserId, userId)
            else viewModel.followUser(sessionUserId, userId) },
        shape = RoundedCornerShape(40.dp),
        modifier = modifier,
        colors = colors
    ) {
        Text(text = content)
    }
}

data class RecipeHeader(
    val title: String,
)

val tabItem = listOf(
    RecipeHeader(
        "My Recipes",
    ),
    RecipeHeader(
        "Bookmarked",
    )
)
