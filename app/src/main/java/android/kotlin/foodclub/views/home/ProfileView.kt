package android.kotlin.foodclub.views.home

import android.content.Intent
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.others.BottomSheetItem
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.navigation.HomeOtherRoutes
import android.kotlin.foodclub.utils.composables.CustomBottomSheet
import android.kotlin.foodclub.utils.helpers.UiEvent
import android.kotlin.foodclub.utils.helpers.uriToFile
import android.kotlin.foodclub.viewModels.home.ProfileViewModel
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileView(
    navController: NavController,
    userId: Long,
    viewModel: ProfileViewModel,
) {

    val profileModelState = viewModel.profileModel.collectAsState()
    val bookmarkedPostsState = viewModel.bookmarkedPosts.collectAsState()
    val sessionUserId = viewModel.myUserId.collectAsState()
    val context = LocalContext.current
    val dataStore = viewModel.storeData
    var imageUri: Uri? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = true) {
        dataStore.getImage().collect { image ->
            if (image != null) {
                imageUri = Uri.parse(image)
            } else {
                imageUri = null
                Log.i("ProfileView", "NULL IMG URI")
            }
        }
    }

    LaunchedEffect(userId) {
        viewModel.setUser(userId)
        if (userId != 0L && userId != sessionUserId.value) {
            viewModel.isFollowedByUser(sessionUserId.value, userId)
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

    val isFollowed = viewModel.isFollowedByUser.collectAsState()

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
    }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState() { 2 }

    if (profileModelState.value == null) {
        Text(text = stringResource(id = R.string.loading))
    } else {
        val profile = profileModelState.value
        val userPosts = viewModel.userPosts.collectAsState()
        val topCreators = profile!!.topCreators
        val bookmarkedPosts = bookmarkedPostsState.value
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
                        dataStore.storeImage(uri.toString())
                    }
                    val file = uriToFile(uri, context)
                    viewModel.updateUserProfileImage(
                        id = viewModel.myUserId.value,
                        file = file!!,
                        uri = uri
                    )
                }
            }

        var showDeleteRecipe by remember {
            mutableStateOf(false)
        }
        var postId by remember {
            mutableLongStateOf(0)
        }

        var userTabItems = listOf<UserPosts>()

        if (pagerState.currentPage == 0) {
            userTabItems = userPosts.value
        } else if (pagerState.currentPage == 1) {
            userTabItems = bookmarkedPosts
        }

        if (showDeleteRecipe) {
            viewModel.getPostData(postId)

            ShowProfilePosts(
                postId = postId,
                posts = userTabItems,
                viewModel = viewModel,
                onPostDeleted = {
                    viewModel.updatePosts(postId)
                    showDeleteRecipe = false
                },
                onBackPressed = {
                    showDeleteRecipe = false
                }
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
                    Box(if (userId == 0L) Modifier.clickable {
                        showBottomSheet = true
                    } else Modifier) {
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
                            Image(
                                painter = painterResource(R.drawable.profile_picture_change_icon),
                                contentDescription = stringResource(id = R.string.profile_picture_edit),
                                modifier = Modifier
                                    .height(dimensionResource(id = R.dimen.dim_46))
                                    .width(dimensionResource(id = R.dimen.dim_46))
                                    .offset(
                                        x = (cos(PI / 4) * 62 + 39).dp,
                                        y = (sin(PI / 4) * 62 + 39).dp
                                    )
                            )
                        }
                    }
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
                            onClick = { navController.navigate("SETTINGS") }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.vector_1_),
                                contentDescription = "",
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
                        text = profile.username,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_5)),
                        letterSpacing = (-1).sp
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
                        ClickableText(
                            text = AnnotatedString(profile.totalUserFollowers.toString()),
                            onClick = {
                                navController.navigate(
                                    "FOLLOWER_VIEW/${
                                        if (userId != 0L) userId else sessionUserId.value
                                    }"
                                )
                            },
                            style = TextStyle(
                                color = Color.Black,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = dimensionResource(id = R.dimen.fon_17).value.sp
                            )
                        )
                        ClickableText(
                            text = AnnotatedString(profile.totalUserFollowing.toString()),
                            onClick = {
                                navController.navigate(
                                    "FOLLOWING_VIEW/${
                                        if (userId != 0L) userId else sessionUserId.value
                                    }"
                                )
                            },
                            style = TextStyle(
                                color = Color.Black,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = dimensionResource(id = R.dimen.fon_17).value.sp
                            )
                        )
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
                        Text(
                            fontFamily = Montserrat,
                            text = stringResource(id = R.string.followers),
                            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                            color = Color(127, 147, 141, 255),
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            fontFamily = Montserrat,
                            text = stringResource(id = R.string.following),
                            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                            color = Color(127, 147, 141, 255),
                            fontWeight = FontWeight.Light
                        )
                    }
                    if (userId != 0L && userId != sessionUserId.value) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
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
                                    )
                                }
                            )
                        }
                    }

                    var testItems = mutableListOf<UserPosts>()

                    for (i in 1..1000) {
                        testItems.add(
                            UserPosts(
                                id = i,
                                title = "",
                                dateCreated = "2023-11-08T15:25:38.170Z",
                                description = "",
                                videoUrl = "https://foodclub-s3-dev-eu-west-2.s3.eu-west-2.amazonaws.com/https%3A//example.com/video5.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAYFCINAASZYUSC4GL%2F20231130%2Feu-west-2%2Fs3%2Faws4_request&X-Amz-Date=20231130T162415Z&X-Amz-Expires=3600&X-Amz-Signature=080afac2a4ff677b93b11e012f3aae9d67dd1d6d812fc8bcb45a93af08489f16&X-Amz-SignedHeaders=host&x-id=GetObject",
                                thumbnailUrl = "https://foodclub-s3-dev-eu-west-2.s3.eu-west-2.amazonaws.com/https%3A//example.com/thumbnail5.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAYFCINAASZYUSC4GL%2F20231130%2Feu-west-2%2Fs3%2Faws4_request&X-Amz-Date=20231130T162415Z&X-Amz-Expires=3600&X-Amz-Signature=2f08054e150859f7ca98f88dad46482afeb9730cd19d0d7090a1347253ff1196&X-Amz-SignedHeaders=host&x-id=GetObject",
                                totalLikes = 0,
                                totalViews = 0
                            )
                        )
                    }

                    //

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

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                state = lazyGridState
                            ) {
                                items(items = testItems,//userTabItems,
                                    key = { it.id }
                                ) { dataItem ->
                                    GridItem(dataItem, triggerShowDeleteRecipe = { tabItemId ->
                                        postId = tabItemId
                                        showDeleteRecipe = true
                                    })
                                }
                            }

                            var listLoading by remember { mutableStateOf(false) }
                            val loadMore = remember {
                                derivedStateOf {
                                    lazyGridState.firstVisibleItemIndex > testItems.size - 10
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


                    /*

                     */
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
                        BottomSheetItem(1, "Block", null) {
                            showUserOptionsSheet = false; showBlockView = true
                        },
                        BottomSheetItem(2, "Report", null) {
                            showUserOptionsSheet = false;showReportView = true
                        },
                        BottomSheetItem(3, "Hide your FoodSNAPS", null) {},
                        BottomSheetItem(4, "Copy profile URL", null) {},
                        BottomSheetItem(5, "Share this Profile", null) {}
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
                    text = "Block",
                    type = "Block",
                    userId = "User1",
                    actionBlockReport = {},
                    onDismiss = { showBlockView = false; showUserOptionsSheet = true }
                )
            }

            if (showReportView) {
                android.kotlin.foodclub.utils.composables.BlockReportView(
                    containerColor = Color.Black,
                    text = "Report",
                    type = "Report",
                    userId = "User1",
                    actionBlockReport = {},
                    onDismiss = { showReportView = false; showUserOptionsSheet = true }
                )
            }
        }
    }
}

@Composable
fun GridItem(
    dataItem: UserPosts,
    triggerShowDeleteRecipe: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dim_272))
            .width(dimensionResource(id = R.dimen.dim_178))
            .padding(dimensionResource(id = R.dimen.dim_10)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.salad_ingredient),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        triggerShowDeleteRecipe(dataItem.id.toLong())
                    }
            )
        }
    }
}

@Composable
fun FollowButton(
    isFollowed: Boolean,
    viewModel: ProfileViewModel,
    sessionUserId: Long,
    userId: Long
) {
    val colors = if (isFollowed)
        ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color.Black
        )
    else
        ButtonDefaults.buttonColors(
            containerColor = foodClubGreen,
            contentColor = Color.White
        )

    val content = isFollowed(isFollowed)

    val modifier = if (isFollowed) Modifier
        .width(dimensionResource(id = R.dimen.dim_130))
        .height(dimensionResource(id = R.dimen.dim_40))
        .border(
            dimensionResource(id = R.dimen.dim_1),
            Color.Black,
            RoundedCornerShape(dimensionResource(id = R.dimen.dim_40))
        )
        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_40)))
    else Modifier
        .width(dimensionResource(id = R.dimen.dim_130))
        .height(dimensionResource(id = R.dimen.dim_40))

    Button(
        onClick = {
            if (isFollowed) viewModel.unfollowUser(sessionUserId, userId)
            else viewModel.followUser(sessionUserId, userId)
        },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_40)),
        modifier = modifier,
        colors = colors
    ) {
        Text(text = content)
    }
}

@Composable
fun isFollowed(isFollowed: Boolean): String {
    return if (isFollowed) stringResource(id = R.string.unfollow) else stringResource(id = R.string.follow)
}