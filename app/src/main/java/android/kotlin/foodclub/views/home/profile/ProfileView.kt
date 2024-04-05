package android.kotlin.foodclub.views.home.profile

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.others.BottomSheetItem
import android.kotlin.foodclub.navigation.HomeOtherRoutes
import android.kotlin.foodclub.utils.composables.CustomBottomSheet
import android.kotlin.foodclub.utils.composables.shimmerBrush
import android.kotlin.foodclub.utils.composables.videoPager.VideoPager
import android.kotlin.foodclub.utils.composables.videoPager.VideoPagerState
import android.kotlin.foodclub.utils.helpers.ProfilePicturePlaceHolder
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import android.kotlin.foodclub.utils.helpers.createGalleryLauncher
import android.kotlin.foodclub.utils.helpers.uriToFile
import android.kotlin.foodclub.viewModels.home.profile.ProfileEvents
import android.kotlin.foodclub.views.ProfileViewLoadingSkeleton
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalDensity
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
import androidx.navigation.NavOptionsBuilder
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileView(
    onNavigate: (String, NavOptionsBuilder.() -> Unit) -> Unit,
    profilePosts: LazyPagingItems<VideoModel>,
    bookmarkedPosts: LazyPagingItems<VideoModel>,
    events: ProfileEvents,
    state: ProfileState
) {
    val context = LocalContext.current
    val localDensity = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))
    val brush = shimmerBrush()
    val scope = rememberCoroutineScope()
    var profileImage: String? by remember { mutableStateOf(null) }
    var showProfileImage by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state) {
        if (state.userProfile?.profilePictureUrl == null) {
            state.dataStore?.getImage()?.collect { image ->
                profileImage = image
            }
        } else {
            profileImage = state.userProfile.profilePictureUrl
        }
    }

    val pullRefresh = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { profilePosts.refresh() }
    )

    if (profilePosts.loadState.refresh is LoadState.Loading) {
        ProfileViewLoadingSkeleton(
            brush,
            onNavigate,
            state
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefresh)
        ) {
            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
                pageCount = { 2 }
            )

            val tabItems = stringArrayResource(id = R.array.profile_tabs)
            var showBottomSheet by remember { mutableStateOf(false) }

            val galleryLauncher =
                createGalleryLauncher(context = context, onResult = { uri ->
                    scope.launch {
                        state.dataStore?.storeImage(uri.toString())
                    }
                    val file = uriToFile(uri, context)
                    events.updateUserProfileImage(
                        file = file!!,
                        uri = uri
                    )
                })


            var showPost by remember { mutableStateOf(false) }
            var showPostIndex by remember { mutableIntStateOf(0) }

            var userTabItems: LazyPagingItems<VideoModel> = profilePosts
            if (pagerState.currentPage == 0) {
                userTabItems = profilePosts
            } else if (pagerState.currentPage == 1) {
                userTabItems = bookmarkedPosts
            }
            if (showProfileImage) {
                ShowProfileImage(profileImage) {
                    showProfileImage = false
                }
            }
            if (showPost) {
                VideoPager(
                    exoPlayer = state.exoPlayer,
                    videoList = userTabItems,
                    initialPage = showPostIndex,
                    events = events,
                    state = VideoPagerState.default(),
                    modifier = Modifier,
                    localDensity = localDensity,
                    coroutineScope = coroutineScope,
                    onBackPressed = {
                        showPost = false
                        state.exoPlayer.stop()
                    },
                )

//                ShowProfilePosts(
//                    postId = postIndex,
//                    events = events,
//                    state = state,
//                    onPostDeleted = {
//                        events.updatePosts(postIndex)
//                        showPost = false
//                    },
//                    onBackPressed = {
//                        showPost = false
//                    },
//                    posts = userTabItems
//                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center
                ) {
                    TopProfileLayout(
                        state = state,
                        profilePhotoUrl = profileImage,
                        onProfilePhotoClick = { showBottomSheet = true },
                        onNavigate = onNavigate
                    )
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
                            text = state.userProfile?.username ?: "",
                            fontSize = dimensionResource(id = R.dimen.fon_23).value.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.dim_5))
                                .padding(horizontal = dimensionResource(id = R.dimen.dim_20)),
                            letterSpacing = -dimensionResource(id = R.dimen.fon_1).value.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = integerResource(R.integer.int_2)
                        )
                        FollowersSection(
                            state = state,
                            events = events,
                            onNavigate = onNavigate
                        )

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
                                            ), overflow = TextOverflow.Ellipsis
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
                                    items(
                                        count = userTabItems.itemCount,
                                    ) { index ->
                                        val tabItem = userTabItems[index]
                                        if (tabItem != null) {
                                            GridItem(
                                                brush = brush,
                                                isInternetConnected = isInternetConnected,
                                                dataItem = tabItem,
                                                triggerShowDeleteRecipe = {
                                                    showPostIndex = index
                                                    showPost = true
                                                }
                                            )
                                        }
                                    }
                                    item {
                                        if (userTabItems.loadState.append is LoadState.Loading) {
                                            CircularProgressIndicator(
                                                color = foodClubGreen,
                                                strokeWidth = dimensionResource(id = R.dimen.dim_4)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }

            if (state.sessionUserId == state.profileUserId && showBottomSheet) {
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
                                onNavigate(HomeOtherRoutes.TakeProfilePhotoView.route) {}
                            })
                    ),
                    sheetTitle = stringResource(id = R.string.upload_photo),
                    onDismiss = { showBottomSheet = false },
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_110)),
                    containerColor = Color.White,
                    titleSpace = true
                )
            }

            PullRefreshIndicator(
                refreshing = profilePosts.loadState.refresh is LoadState.Loading,
                state = pullRefresh,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun TopProfileLayout(
    state: ProfileState,
    profilePhotoUrl: String?,
    onProfilePhotoClick: () -> Unit,
    onNavigate: (String, NavOptionsBuilder.() -> Unit) -> Unit
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
            Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onProfilePhotoClick()
            }
        ) {
            AsyncImage(
                model = profilePhotoUrl ?: R.drawable.default_avatar,
                contentDescription = stringResource(id = R.string.profile_picture),
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_60)))
                    .height(dimensionResource(id = R.dimen.dim_124))
                    .width(dimensionResource(id = R.dimen.dim_124)),
                contentScale = ContentScale.Crop
            )
            if (state.profileUserId == 0L) {
                ProfilePicturePlaceHolder {
                    onProfilePhotoClick()
                }
            }
        }


        var settingNavigated by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_40)))

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
                if (!settingNavigated) {
                    onNavigate("SETTINGS") {}
                    settingNavigated = true
                }
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector_1_),
                contentDescription = null,
            )
        }

    }
}

@Composable
fun FollowersSection(
    state: ProfileState,
    events: ProfileEvents,
    onNavigate: (String, NavOptionsBuilder.() -> Unit) -> Unit
) {
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
                        onNavigate(
                            "FOLLOWER_VIEW/${
                                if (state.profileUserId != 0L) state.profileUserId else state.sessionUserId
                            }"
                        ) {}
                    }),

                ) {
                Text(
                    text = AnnotatedString(
                        state.userProfile?.totalUserFollowers?.toString() ?: ""
                    ),
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
                        onNavigate(
                            "FOLLOWING_VIEW/${
                                if (state.profileUserId != 0L) state.profileUserId else state.sessionUserId
                            }"
                        ) {}
                    }),

                ) {
                Text(
                    text = AnnotatedString(
                        state.userProfile?.totalUserFollowing?.toString() ?: ""
                    ),
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
    if (state.profileUserId != 0L && state.profileUserId != state.sessionUserId) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        FollowButton(
            isFollowed = state.isFollowed,
            events = events,
            sessionUserId = state.sessionUserId,
        )
    }
}