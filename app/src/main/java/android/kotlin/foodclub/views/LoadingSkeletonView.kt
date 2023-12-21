package android.kotlin.foodclub.views

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.utils.composables.ShimmerBrush
import android.kotlin.foodclub.utils.helpers.ProfilePicturePlaceHolder
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import android.kotlin.foodclub.viewModels.home.profile.ProfileEvents
import android.kotlin.foodclub.views.home.profile.ProfileState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMotionApi::class)
@Composable
fun VideoPagerLoadingSkeleton() {
    val context = LocalContext.current
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))

    val brush = ShimmerBrush()
    Box(
        modifier = Modifier
            .fillMaxSize()

    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(dimensionResource(id = R.dimen.dim_15))
        )
        {
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


        Column (horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(id = R.dimen.dim_15))){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.End)
                    .size(dimensionResource(id = R.dimen.dim_50))
            ) {
                val durationms1=dimensionResource(id = R.dimen.dim_14)
                val durationms2=dimensionResource(id = R.dimen.dim_16)
                val durationms3=dimensionResource(id = R.dimen.dim_22)

                Box(modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_55))
                    ) {
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_55))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_35)))
                            .background(Color.Black.copy(alpha = 0.5f))
                            .blur(radius = dimensionResource(id = R.dimen.dim_5))
                    )

                    val maxBookmarkSize =  dimensionResource(id = R.dimen.dim_32)
                    val bookmarkIconSize by animateDpAsState(
                        targetValue = dimensionResource(id = R.dimen.dim_21),
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
                        tint =  Color.White,
                        modifier = Modifier
                            .size(bookmarkIconSize)
                            .align(Alignment.Center)
                            .zIndex(1f),
                        contentDescription = stringResource(id = R.string.bookmark)
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.End)
                    .width(dimensionResource(id = R.dimen.dim_50))
                    .height(dimensionResource(id = R.dimen.dim_80)),
            ) {
                val durationms1=dimensionResource(id = R.dimen.dim_14)
                val durationms2=dimensionResource(id = R.dimen.dim_16)
                val durationms3=dimensionResource(id = R.dimen.dim_22)
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

                        ) {
                            val maxSize =  dimensionResource(id = R.dimen.dim_32)
                            val iconSize by animateDpAsState(
                                targetValue = dimensionResource(id = R.dimen.dim_21),
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
                                tint = Color.White,
                                modifier = Modifier.size(iconSize)
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_3)))
                            Box(modifier= Modifier
                                .size(iconSize)
                                .background(brush))
                        }
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

            Button(
                onClick = {  },
                colors = defaultButtonColors(),
                shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)),
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_120))
                    .height(dimensionResource(id = R.dimen.dim_35)),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(isInternetConnected)
                    {
                        Text(
                            text = stringResource(id = R.string.info),
                            fontFamily = Montserrat,
                            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                        )}
                    else{Box(modifier= Modifier
                        .width(dimensionResource(id = R.dimen.dim_101))
                        .height(dimensionResource(id = R.dimen.dim_18))
                        .background(brush))}
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileViewLoadingSkeleton (
    brush: Brush,
    isInternetConnected:Boolean,
    navController: NavController,
    userId: Long,
    events: ProfileEvents,
    state: ProfileState
) {


    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState() { 2 }

    Text(text = stringResource(id = R.string.loading))

    val userPosts = state.userPosts

    val bookmarkedPosts = state.bookmarkedPosts
    var userTabItems = listOf<VideoModel>()

    if(pagerState.currentPage == 0){
        userTabItems = userPosts
    }
    else if(pagerState.currentPage == 1){
        userTabItems = bookmarkedPosts
    }
    val tabItems = stringArrayResource(id = R.array.profile_tabs)
    var showUserOptionsSheet by remember { mutableStateOf(false) }
    var showDeleteRecipe by remember {
        mutableStateOf(false)
    }
    var postId by remember {
        mutableLongStateOf(0)
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.dim_70),
                    start = dimensionResource(id = R.dimen.dim_95)
                ),
            horizontalArrangement = Arrangement.Center
        ) {

            Box( Modifier) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(dimensionResource(id = R.dimen.dim_124))
                        .width(dimensionResource(id = R.dimen.dim_124))
                        .background(brush),

                    )
                if (userId == 0L) {
                    ProfilePicturePlaceHolder()
                }
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_40)))
            if(userId ==0L) {
                Button(shape = CircleShape,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(dimensionResource(id = R.dimen.dim_53))
                        .width(dimensionResource(id = R.dimen.dim_53)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(
                            255,
                            255,
                            255,
                            255
                        )
                    ),
                    contentPadding = PaddingValues(),
                    onClick = { navController.navigate("SETTINGS") }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vector_1_),
                        contentDescription = null,
                    )
                }
            }else{
                Button(shape = CircleShape,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(dimensionResource(id = R.dimen.dim_53))
                        .width(dimensionResource(id = R.dimen.dim_53)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(
                            255,
                            255,
                            255,
                            255
                        )
                    ),
                    contentPadding = PaddingValues(),
                    onClick = { showUserOptionsSheet=true }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dots),
                        contentDescription = "",
                    )
                }
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(
                    top = dimensionResource(id = R.dimen.dim_10),
                    start = dimensionResource(id = R.dimen.dim_4),
                    end = dimensionResource(id = R.dimen.dim_4)
                ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .width(dimensionResource(id = R.dimen.dim_114))
                .height(dimensionResource(id = R.dimen.dim_31))
                .background(brush))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.dim_5)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_70), Alignment.CenterHorizontally)
            ) {
                Column {
                    Box(
                        Modifier
                            .width(dimensionResource(id = R.dimen.dim_62))
                            .height(dimensionResource(id = R.dimen.dim_17))
                            .background(brush))
                    Text(
                        fontFamily = Montserrat,
                        text = stringResource(id = R.string.followers),
                        fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                        color = Color(127, 147, 141, 255),
                        fontWeight = FontWeight.Light
                    )
                }
                Column {
                    Box(
                        Modifier
                            .width(dimensionResource(id = R.dimen.dim_62))
                            .height(dimensionResource(id = R.dimen.dim_17))
                            .background(brush))
                    Text(
                        fontFamily = Montserrat,
                        text = stringResource(id = R.string.following),
                        fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                        color = Color(127, 147, 141, 255),
                        fontWeight = FontWeight.Light
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_30), Alignment.CenterHorizontally)
            ) {


            }
            if(userId != 0L && userId != state.sessionUserId) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_40)),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_130))
                        .height(dimensionResource(id = R.dimen.dim_40)),
                    colors =ButtonDefaults.buttonColors(
                        containerColor = foodClubGreen,
                        contentColor = Color.White
                    )
                ) {
                    Box(modifier= Modifier
                        .width(dimensionResource(id = R.dimen.dim_101))
                        .height(dimensionResource(id = R.dimen.dim_18))
                        .background(brush))

                }
            }
            TabRow(selectedTabIndex = pagerState.currentPage,
                containerColor = Color.White,
                contentColor = Color.White,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        height = dimensionResource(id = R.dimen.dim_2),
                        color = Color.Black
                    )
                }
            ) {
                tabItems.forEachIndexed{
                        index,tabItem ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        selectedContentColor = Color.Black,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }, text = {
                            Text(
                                text =  AnnotatedString(tabItem),
                                style = TextStyle(
                                    fontFamily = Montserrat,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                                )
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                beyondBoundsPageCount = 10,
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(
                            top = dimensionResource(id = R.dimen.dim_5),
                            start = dimensionResource(id = R.dimen.dim_15),
                            end = dimensionResource(id = R.dimen.dim_15),
                            bottom = dimensionResource(id = R.dimen.dim_110)
                        )
                ) {
                    val lazyGridState = rememberLazyGridState()

                    Column(horizontalAlignment =Alignment.CenterHorizontally) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            state = lazyGridState
                        ) {items(2){
                            Card(modifier = Modifier
                                .height(dimensionResource(id = R.dimen.dim_272))
                                .width(dimensionResource(id = R.dimen.dim_178))
                                .padding(dimensionResource(id = R.dimen.dim_10))
                            ,shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(brush)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            )
                        }
                        }
                        }
                        CircularProgressIndicator(color = foodClubGreen,
                            strokeWidth = dimensionResource(id = R.dimen.dim_4))
                    }


                }


            }
        }
    }
}