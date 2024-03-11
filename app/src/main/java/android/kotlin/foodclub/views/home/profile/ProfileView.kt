package android.kotlin.foodclub.views.home.profile

import android.content.Intent
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.others.BottomSheetItem
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.navigation.HomeOtherRoutes
import android.kotlin.foodclub.utils.composables.CustomBottomSheet
import android.kotlin.foodclub.utils.composables.shimmerBrush
import android.kotlin.foodclub.utils.helpers.ProfilePicturePlaceHolder
import android.kotlin.foodclub.utils.helpers.UiEvent
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import android.kotlin.foodclub.utils.helpers.uriToFile
import android.kotlin.foodclub.viewModels.home.profile.ProfileEvents
import android.kotlin.foodclub.viewModels.home.profile.ProfileViewModel
import android.kotlin.foodclub.views.ProfileViewLoadingSkeleton
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileView(
    navController: NavController,
    userId: Long,
    viewModel: ProfileViewModel,
    events: ProfileEvents,
    state: ProfileState
) {
    val context = LocalContext.current
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))
    val brush = shimmerBrush()
    val scope = rememberCoroutineScope()
    var imageUri: Uri? by remember { mutableStateOf(null) }
    val isAPICallLoading = state.isLoading

    LaunchedEffect(key1 = true) {
        state.dataStore?.getImage()?.collect { image ->
            if (image != null) {
                imageUri = Uri.parse(image)
            } else {
                imageUri = null
                Log.e("ProfileView", "NULL IMG URI")
            }
        }
    }

    val pullRefresh = rememberPullRefreshState(
        refreshing = state.isRefreshingUI,
        onRefresh = {
            state.isRefreshingUI = true
            scope.launch {
                delay(2000)
                events.onRefreshUI()
            }
        }
    )
    if (isAPICallLoading){
        ProfileViewLoadingSkeleton(
            brush,
            navController,
            userId,
            state
        )
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefresh)
        ){
            LaunchedEffect(userId) {
                if (userId != 0L && userId != state.sessionUserId) {
                    events.isFollowedByUser(state.sessionUserId, userId)
                }
            }

            LaunchedEffect(key1 = true) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is UiEvent.Navigate -> {
                            navController.navigate(event.route) {
                                popUpTo(Graph.HOME) { inclusive = true }
                            }
                        }
                    }
                }
            }

            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
                pageCount = { 2 }
            )

            val profile = state.userProfile
            val userPosts = state.userPosts
            val topCreators = profile?.topCreators
            val bookmarkedPosts = state.bookmarkedPosts
            val tabItems = stringArrayResource(id = R.array.profile_tabs)
            var showBottomSheet by remember { mutableStateOf(false) }
            var showUserOptionsSheet by remember { mutableStateOf(false) }

            var showBlockView by remember { mutableStateOf(false) }
            var showReportView by remember { mutableStateOf(false) }

            val galleryLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
                    it?.let { uri ->
                        context.contentResolver
                            .takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                        scope.launch {
                            state.dataStore?.storeImage(uri.toString())
                        }
                        val file = uriToFile(uri, context)
                        events.updateUserProfileImage(
                            file = file!!,
                            uri = uri
                        )
                    }
                }

            var showPost by remember {
                mutableStateOf(false)
            }
            var postId by remember {
                mutableLongStateOf(0L)
            }

            var userTabItems = listOf<VideoModel>()

            if (pagerState.currentPage == 0) {
                userTabItems = userPosts
            } else if (pagerState.currentPage == 1) {
                userTabItems = bookmarkedPosts
            }

            if (showPost) {
                events.getPostData(postId)

                ShowProfilePosts(
                    postId = postId,
                    events = events,
                    state = state,
                    onPostDeleted = {
                        events.updatePosts(postId)
                        showPost = false
                    },
                    onBackPressed = {
                        showPost = false
                    },
                    posts = userTabItems
                )
            } else {
                Column(
                    modifier = Modifier
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
                        Box(
                            modifier = if (userId == 0L) {
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    showBottomSheet = true
                                }
                            } else {
                                Modifier
                            }
                        ) {
                            AsyncImage(
                                model = imageUri ?: R.drawable.profilepicture,
                                contentDescription = stringResource(id = R.string.profile_picture),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_60)))
                                    .height(dimensionResource(id = R.dimen.dim_124))
                                    .width(dimensionResource(id = R.dimen.dim_124)),
                                contentScale = ContentScale.Crop
                            )
                            if (userId == 0L) {
                                ProfilePicturePlaceHolder()
                            }
                        }


                        var settingNavigated by remember { mutableStateOf(false)}
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_40)))
                        if (userId == 0L) {
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
                                onClick = {
                                    if(!settingNavigated)
                                    {
                                        navController.navigate("SETTINGS")
                                        settingNavigated = true
                                    }
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.vector_1_),
                                    contentDescription = null,
                                )
                            }
                        } else {
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
                                onClick = { showUserOptionsSheet = true }
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
                        Text(
                            fontFamily = Montserrat,
                            text = profile?.username ?: "",
                            fontSize = dimensionResource(id = R.dimen.fon_23).value.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.dim_5))
                                .padding(horizontal = dimensionResource(id = R.dimen.dim_20)),
                            letterSpacing = -dimensionResource(id = R.dimen.fon_1).value.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = integerResource(R.integer.int_2)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimensionResource(id = R.dimen.dim_5)),
                            horizontalArrangement = Arrangement.spacedBy(
                                dimensionResource(id = R.dimen.dim_70),
                                Alignment.CenterHorizontally
                            )
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .width(dimensionResource(id = R.dimen.dim_60))
                                        .wrapContentHeight()
                                        .clickable(onClick = {
                                            navController.navigate(
                                                "FOLLOWER_VIEW/${
                                                    if (userId != 0L) userId else state.sessionUserId
                                                }"
                                            )
                                        }),

                                    ) {
                                    Text(
                                        text = AnnotatedString(profile?.totalUserFollowers?.toString() ?: ""),
                                        modifier = Modifier.align(Alignment.Center),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontFamily = Montserrat,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = dimensionResource(id = R.dimen.fon_17).value.sp
                                        ),
                                        textAlign = TextAlign.Center,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = integerResource(id = R.integer.int_1)
                                    )
                                }
                                Text(
                                    fontFamily = Montserrat,
                                    text = stringResource(id = R.string.followers),
                                    fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                                    color = colorResource(id = R.color.followers_following_color),
                                    fontWeight = FontWeight.Light,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = integerResource(id = R.integer.int_1)
                                )
                            }

                            Column {
                                Box(
                                    modifier = Modifier
                                        .width(dimensionResource(id = R.dimen.dim_60))
                                        .wrapContentHeight()
                                        .clickable(onClick = {
                                            navController.navigate(
                                                "FOLLOWING_VIEW/${
                                                    if (userId != 0L) userId else state.sessionUserId
                                                }"
                                            )
                                        }),

                                    ) {
                                    Text(
                                        text = AnnotatedString(profile?.totalUserFollowing?.toString() ?: ""),
                                        modifier = Modifier.align(Alignment.Center),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontFamily = Montserrat,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = dimensionResource(id = R.dimen.fon_17).value.sp
                                        ),
                                        textAlign = TextAlign.Center,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                }
                                Text(
                                    fontFamily = Montserrat,
                                    text = stringResource(id = R.string.following),
                                    fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                                    color = colorResource(id = R.color.followers_following_color),
                                    fontWeight = FontWeight.Light,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }


                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            horizontalArrangement = Arrangement.spacedBy(
                                dimensionResource(id = R.dimen.dim_30),
                                Alignment.CenterHorizontally
                            )
                        ) {


                        }
                        if (userId != 0L && userId != state.sessionUserId) {
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

                            FollowButton(
                                isFollowed = state.isFollowed,
                                events = events,
                                sessionUserId = state.sessionUserId,
                            )
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
                            tabItems.forEachIndexed { index, tabItem ->
                                Tab(
                                    selected = index == pagerState.currentPage,
                                    selectedContentColor = Color.Black,
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }, text = {
                                        Text(
                                            text = AnnotatedString(tabItem),
                                            style = TextStyle(
                                                fontFamily = Montserrat,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.Black,
                                                fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                                            )
                                            , overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                )
                            }
                        }

                        HorizontalPager(
                            state = pagerState,
                            beyondBoundsPageCount = 10
                        ) {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .padding(
                                        top = dimensionResource(id = R.dimen.dim_5),
                                        start = dimensionResource(id = R.dimen.dim_15),
                                        end = dimensionResource(id = R.dimen.dim_15)
                                    )
                            ) {
                                val lazyGridState = rememberLazyGridState()

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    state = lazyGridState,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(items = userTabItems,
                                        key = { it.videoId }
                                    ) { dataItem ->
                                        GridItem(
                                            brush,
                                            isInternetConnected,
                                            dataItem = dataItem,
                                            triggerShowDeleteRecipe = { tabItemId ->
                                                postId = tabItemId
                                                showPost = true
                                            })
                                    }
                                }

                                var listLoading by remember { mutableStateOf(false) }
                                val loadMore = remember {
                                    derivedStateOf {
                                        lazyGridState.firstVisibleItemIndex > userTabItems.size - 10
                                    }
                                }

                                LaunchedEffect(loadMore)
                                {
                                    if (!listLoading) {
                                        listLoading = true
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (userId == 0L && showBottomSheet) {
                CustomBottomSheet(
                    itemList = listOf(
                        BottomSheetItem(
                            id = 1,
                            title = stringResource(id = R.string.select_from_gallery),
                            resourceId = R.drawable.select_from_gallery,
                            onClick = { galleryLauncher.launch(arrayOf("image/*")) }
                        ),
                        BottomSheetItem(
                            id = 2,
                            title = stringResource(id = R.string.take_photo),
                            resourceId = R.drawable.take_photo,
                            onClick = {
                                navController.navigate(route = HomeOtherRoutes.TakeProfilePhotoView.route)
                            })
                    ),
                    sheetTitle = stringResource(id = R.string.upload_photo),
                    onDismiss = { showBottomSheet = false },
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_110)),
                    containerColor = Color.White,
                    titleSpace = true
                )
            } else {
                if (showUserOptionsSheet) {
                    android.kotlin.foodclub.utils.composables.BottomSheet(
                        itemList = listOf(
                            BottomSheetItem(1, stringResource(id = R.string.block), null) {
                                showUserOptionsSheet = false; showBlockView = true
                            },
                            BottomSheetItem(2, stringResource(id = R.string.report), null) {
                                showUserOptionsSheet = false;showReportView = true
                            },
                            BottomSheetItem(3, stringResource(id = R.string.hide_your_foodsnaps), null) {},
                            BottomSheetItem(4, stringResource(id = R.string.copy_profile_url), null) {},
                            BottomSheetItem(5, stringResource(id = R.string.share_this_profile), null) {}
                        ),
                        sheetTitle = "",
//                enableDragHandle = true,
                        onDismiss = { showUserOptionsSheet = false; },
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_110)),
                        containerColor = Color.Black,
                        titleSpace = false
                    )
                }

                if (showBlockView) {
                    android.kotlin.foodclub.utils.composables.BlockReportView(
                        containerColor = Color.Black,
                        text = stringResource(id = R.string.block),
                        type = stringResource(id = R.string.block),
                        userId = stringResource(id = R.string.user1),
                        actionBlockReport = {},
                        onDismiss = { showBlockView = false; showUserOptionsSheet = true }
                    )
                }
                if (showReportView) {
                    android.kotlin.foodclub.utils.composables.BlockReportView(
                        containerColor = Color.Black,
                        text = stringResource(id = R.string.report),
                        type = stringResource(id = R.string.report),
                        userId = stringResource(id = R.string.user1),
                        actionBlockReport = {},
                        onDismiss = { showReportView = false; showUserOptionsSheet = true }
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = state.isRefreshingUI,
                state = pullRefresh,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}