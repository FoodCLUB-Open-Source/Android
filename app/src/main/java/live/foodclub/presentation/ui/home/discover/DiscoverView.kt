package live.foodclub.presentation.ui.home.discover


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavOptionsBuilder
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import live.foodclub.R
import live.foodclub.config.ui.BottomBarScreenObject
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.domain.enums.Category
import live.foodclub.domain.enums.CategoryType
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.presentation.navigation.HomeOtherRoutes
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.utils.composables.PostListing
import live.foodclub.utils.composables.products.ProductState
import live.foodclub.utils.composables.products.ProductsEvents
import live.foodclub.utils.composables.shimmerBrush
import live.foodclub.utils.composables.videoPager.VideoPager
import live.foodclub.utils.helpers.checkInternetConnectivity
import live.foodclub.presentation.ui.home.discover.composables.KitchenIngredients
import kotlin.math.min


enum class DragValue { Start, End }

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DiscoverView(
    onNavigate: (String, NavOptionsBuilder.() -> Unit) -> Unit,
    events: DiscoverEvents,
    productState: ProductState,
    productsEvents: ProductsEvents,
    state: DiscoverState,
    categoryPosts: LazyPagingItems<VideoModel>,
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val localDensity = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))

    var isShowPost by remember { mutableStateOf(false) }
    var postIndex by remember { mutableIntStateOf(0) }
    val brush = shimmerBrush()

    var subCategoriesTabIndex by remember { mutableIntStateOf(0) }
    var subWorldTabIndex by remember { mutableIntStateOf(0) }
    val dietList = Category.deriveFromType(CategoryType.DIET)
    val cuisineList = Category.deriveFromType(CategoryType.CUISINE)

    var tabIndex by remember { mutableIntStateOf(0) }
    val lazyGridState = rememberLazyGridState()
    val lazyColumnState = rememberLazyListState()
    LaunchedEffect(tabIndex, subCategoriesTabIndex, subWorldTabIndex) {
        if (tabIndex == 1) {
            events.changeCategory(category = dietList[subCategoriesTabIndex])
        } else {
            events.changeCategory(category = cuisineList[subWorldTabIndex])
        }
        lazyGridState.scrollToItem(0)
    }

    val mainTabItemsList = stringArrayResource(id = R.array.discover_tabs)


    if (isShowPost) {
        VideoPager(
            exoPlayer = state.exoPlayer,
            videoList = categoryPosts,
            initialPage = postIndex,
            events = events,
            state = state.videoPagerState,
            modifier = Modifier,
            localDensity = localDensity,
            coroutineScope = coroutineScope,
            onBackPressed = {
                coroutineScope.launch { lazyGridState.scrollToItem(it) }
                isShowPost = false
            },
            onProfileNavigated = {
                onNavigate(BottomBarScreenObject.Profile.route + "?userId=$it") {}
            }
        )
    }
    else {

        Column(
            modifier = Modifier.background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DiscoverViewHeader(
                onNavigate = onNavigate,
                userName = state.username
            )

            MainTabRow(
                isInternetConnected,
                brush,
                tabsList = mainTabItemsList,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                tabIndex = it
            }

        var showMyKitchen by remember {
            mutableStateOf(true)
        }

        var height by remember {
            mutableStateOf(0.dp)
        }

        height = (min(
            productState.filteredAddedProducts.size,
            5
        ) * dimensionResource(id = R.dimen.dim_65).value).dp + dimensionResource(id = R.dimen.dim_230)



        if (tabIndex == 0) {
            if (isInternetConnected) {

                val swipeableState = rememberSwipeableState(1)
                val anchors = mapOf(0f to 0, 1f to 1)

                showMyKitchen = swipeableState.currentValue != 0


                // Auto swipes to the other state if the recommendations list is overflowing upwards
                LaunchedEffect(lazyGridState.isScrollInProgress, lazyGridState.canScrollBackward)
                {
                    if(lazyGridState.isScrollInProgress && !lazyGridState.canScrollBackward){
                        CoroutineScope(Dispatchers.Default).launch {
                            swipeableState.snapTo(1)
                        }
                    }
                }

                // Auto swipes to the other state if the ingredients list is overflowing downwards
                LaunchedEffect(lazyColumnState.isScrollInProgress, lazyColumnState.canScrollForward)
                {
                    if(lazyColumnState.isScrollInProgress && !lazyColumnState.canScrollForward){
                        CoroutineScope(Dispatchers.Default).launch {
                            swipeableState.snapTo(0)
                        }
                    }
                }


                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .focusGroup()
                        .then(
                            if (productState.addedProducts.isNotEmpty())
                            {
                                Modifier.swipeable(
                                    state = swipeableState,
                                    orientation = Orientation.Vertical,
                                    anchors = anchors,
                                    thresholds = { _, _ -> FractionalThreshold(0.3f) }
                                )
                            }
                            else
                            {
                                Modifier
                            }
                        )
                        ) {
                    AnimatedVisibility(visible = showMyKitchen) {
                        Column(
                            modifier = Modifier
                                .height(height)
                            //.background( if (swipeableState.currentValue == 0) Color.Green else Color.Blue)
                            ,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            KitchenIngredients(events = productsEvents, state = productState, lazyColumnState = lazyColumnState)

                            if (productState.addedProducts.isEmpty()) {
                                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))

                                Text(
                                    text = stringResource(id = R.string.add_ingredients_information_text),
                                    fontWeight = FontWeight(500),
                                    fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                                    color = colorResource(
                                        id = R.color.discover_view_add_ingredient_information_text
                                    ).copy(alpha = 0.3f),
                                    lineHeight = dimensionResource(id = R.dimen.fon_17).value.sp,
                                    fontFamily = Montserrat,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
                            }

                            Button(
                                onClick = {
                                    events.onResetSearchData()
                                    onNavigate("ADD_INGREDIENTS") {}
                                },
                                shape = RoundedCornerShape(
                                    dimensionResource(
                                        id = R.dimen.dim_20
                                    )
                                ),
                                colors = ButtonDefaults.buttonColors(foodClubGreen),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.add_ingredients),
                                    fontSize = dimensionResource(
                                        id = R.dimen.fon_14
                                    ).value.sp,
                                    fontFamily = Montserrat,
                                    lineHeight = dimensionResource(id = R.dimen.fon_17).value.sp,
                                    fontWeight = FontWeight(500)
                                )
                            }
                        }
                    }

                    if(productState.addedProducts.isNotEmpty())
                    {
                        Column(
                            modifier = Modifier
                                .focusGroup()
                                .fillMaxHeight()
                        ) {

                            RecommendationSection(
                                onRecommendations = !showMyKitchen,
                                lazyGridState = lazyGridState,
                                isInternetConnected = isInternetConnected,
                                posts = categoryPosts,
                                isShowPost = {
                                    postIndex = it
                                    isShowPost = true
                                }
                            )
                        }
                    }

                }

            }

        } else {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

            if (tabIndex == 1) {
                SubCategoriesTabRow(
                    subCategoriesTabIndex = subCategoriesTabIndex,
                    subCategoriesTabItemsList = dietList,
                    onCategoriesTabChanged = {
                        events.changeCategory(dietList[it])
                        subCategoriesTabIndex = it
                    },
                    isInternetConnected,
                    brush
                )
            } else {
                SubCategoriesTabRow(
                    subCategoriesTabIndex = subWorldTabIndex,
                    subCategoriesTabItemsList = cuisineList,
                    onCategoriesTabChanged = {
                        events.changeCategory(cuisineList[it])
                        subWorldTabIndex = it
                    },
                    isInternetConnected,
                    brush
                )
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(
                        start = dimensionResource(id = R.dimen.dim_15),
                        end = dimensionResource(id = R.dimen.dim_15)
                    )
            ) {
                PostListing(
                    lazyGridState = lazyGridState,
                    userTabItems = categoryPosts,
                    isInternetConnected = isInternetConnected
                ) {
                    postIndex = it
                    isShowPost = true
                }
            }
        }
        }
    }


}


@Composable
fun RecommendationSection(
    onRecommendations: Boolean = true,
    lazyGridState: LazyGridState,
    isInternetConnected: Boolean,
    posts: LazyPagingItems<VideoModel>,
    isShowPost: (Int) -> Unit
) {
    val recipe_vid1 = VideoModel(
        videoId = 1,
        title = null,
        authorDetails = SimpleUserModel(userId = 0, username = "", profilePictureUrl = null),
        videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
        videoStats = VideoStats(
            like = 409876,
            comment = 8356,
            share = 3000,
            favourite = 1500
        ),
        description = "Draft video testing  #foryou #fyp #compose #tik",
        thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
    )
    val recipe_vid2 = VideoModel(
        videoId = 2,
        title = null,
        authorDetails = SimpleUserModel(userId = 0, username = "", profilePictureUrl = null),
        videoLink = "https://kretu.sts3.pl/foodclub_videos/daniel_vid2.mp4",
        videoStats = VideoStats(
            like = 564572,
            comment = 8790,
            share = 2000,
            favourite = 1546
        ),
        description = "Draft video testing  #foryou #fyp #compose #tik",
        thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg"
    )
    val recipe_vid3 = VideoModel(
        videoId = 3,
        title = null,
        authorDetails = SimpleUserModel(userId = 0, username = "", profilePictureUrl = null),
        videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
        videoStats = VideoStats(
            like = 2415164,
            comment = 5145,
            share = 5000,
            favourite = 2000
        ),
        description = "Draft video testing  #foryou #fyp #compose #tik",
        thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
    )
    val recipe_vid4 = VideoModel(
        videoId = 4,
        title = null,
        authorDetails = SimpleUserModel(userId = 0, username = "", profilePictureUrl = null),
        videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
        videoStats = VideoStats(
            like = 51626,
            comment = 1434,
            share = 167,
            favourite = 633
        ),
        description = "Draft video testing  #foryou #fyp #compose #tik",
        thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
    )
    val recipe_vid5 = VideoModel(
        videoId = 5,
        title = null,
        authorDetails = SimpleUserModel(userId = 0, username = "", profilePictureUrl = null),
        videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
        videoStats = VideoStats(
            like = 547819,
            comment = 79131,
            share = 8921,
            favourite = 2901
        ),
        description = "Draft video testing  #foryou #fyp #compose #tik",
        thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
    )
    val recipe_vid6 = VideoModel(
        videoId = 6,
        title = null,
        authorDetails = SimpleUserModel(userId = 0, username = "", profilePictureUrl = null),
        videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
        videoStats = VideoStats(
            like = 4512340,
            comment = 65901,
            share = 8165,
            favourite = 154
        ),
        description = "Draft video testing  #foryou #fyp #compose #tik",
        thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
    )

    val recipe_vid7 = VideoModel(
        videoId = 7,
        title = null,
        authorDetails = SimpleUserModel(userId = 0, username = "", profilePictureUrl = null),
        videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
        videoStats = VideoStats(
            like = 612907,
            comment = 7643,
            share = 1291,
            favourite = 890
        ),
        description = "Draft video testing  #foryou #fyp #compose #tik",
        thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
    )

    val recipesVideosList = listOf(
        recipe_vid1,
        recipe_vid2,
        recipe_vid3,
        recipe_vid4,
        recipe_vid5,
        recipe_vid6,
        recipe_vid7,
        recipe_vid1
    )

    val pagingItems = PagingData.from(recipesVideosList)
    val fakeDataFlow = MutableStateFlow(pagingItems)
    val testPosts = fakeDataFlow.collectAsLazyPagingItems()


    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = Color.White
        )
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(visible = onRecommendations, enter = fadeIn() + expandHorizontally(), exit = fadeOut() + shrinkHorizontally())
            {
                Text(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.dim_20),
                        vertical = dimensionResource(id = R.dimen.dim_20)
                    ),
                    text = stringResource(id = R.string.recommendations),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Black
                )
            }

        }
        PostListing(
            enableInput = onRecommendations,
            lazyGridState = lazyGridState,
            userTabItems = testPosts,
            isInternetConnected = isInternetConnected,
            onPostSelected = isShowPost
        )
    }
}

/* This is the discover view basket icon from the old design that
 navigate the user to MyBasketView. There is some proposal designs
 in FIGMA still keep it, so It might be used in the future. */

//        Button(
//            shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
//            modifier = Modifier
//                .height(dimensionResource(id = R.dimen.dim_56))
//                .width(dimensionResource(id = R.dimen.dim_56)),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = colorResource(id = R.color.discover_view_basket_icon_container_color),
//            ),
//            contentPadding = PaddingValues(),
//            onClick = {}
//        ) {
//            BadgedBox(
//                modifier = Modifier.clickable {
//                    navController.navigate(HomeOtherRoutes.MyBasketView.route)
//                },
//                badge = {
//                    Badge(
//                        modifier = Modifier.offset(
//                            x = -dimensionResource(id = R.dimen.dim_5),
//                            y = dimensionResource(id = R.dimen.dim_5)
//                        ),
//                        containerColor = foodClubGreen
//                    )
//                    { Text(text = (basketCount).toString(), color = Color.Black) }
//                }
//            ) {
//                Icon(
//                    painterResource(id = R.drawable.vector__1_),
//                    contentDescription = stringResource(id = R.string.add_to_basket),
//                    tint = Color.Black
//                )
//            }
//        }
//    }
//}

@Composable
fun DiscoverViewHeader(
    onNavigate: (String, NavOptionsBuilder.() -> Unit) -> Unit,
    userName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dim_60),
                end = dimensionResource(id = R.dimen.dim_20),
                start = dimensionResource(id = R.dimen.dim_20),
                bottom = dimensionResource(id = R.dimen.dim_10)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Bottom),
            fontFamily = Montserrat,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight(500),
            letterSpacing = (-0.04).em,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight(700)
                    )
                ) {
                    append(
                        "${stringResource(id = R.string.hi)} ${userName.replaceFirstChar(Char::titlecase)},"
                    )
                }
                append("\n\n")
                append(stringResource(id = R.string.discover_view_header))
            }
        )

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dim_56))
                    .width(dimensionResource(id = R.dimen.dim_56)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.discover_view_basket_icon_container_color),
                ),
                contentPadding = PaddingValues(),
                onClick = {
                    onNavigate(HomeOtherRoutes.MySearchView.route) {}
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.search_icon_transparent_background),
                    contentDescription = stringResource(id = R.string.search_my_ingredients),
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))

//            The function is not used on the MVP version
//            Button(
//                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
//                modifier = Modifier
//                    .height(dimensionResource(id = R.dimen.dim_56))
//                    .width(dimensionResource(id = R.dimen.dim_56)),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = colorResource(id = R.color.discover_view_basket_icon_container_color),
//                ),
//                contentPadding = PaddingValues(),
//                onClick = {
//                    //navController.navigate("ScanView_route")
//                }
//            ) {
//                Icon(
//                    painterResource(id = R.drawable.camera_icon),
//                    contentDescription = stringResource(id = R.string.scan_my_fridge),
//                    tint = Color.Black
//                )
//            }
        }
    }
}

@Composable
fun MainTabRow(
    isInternetConnected: Boolean,
    brush: Brush,
    tabsList: Array<String>,
    horizontalArrangement: Arrangement.Horizontal,
    onTabChanged: (Int) -> Unit,

    ) {
    var mainTabIndex by remember { mutableIntStateOf(0) }
    val strokeWidthDp = dimensionResource(id = R.dimen.dim_2)
    val topPaddingDp = dimensionResource(id = R.dimen.dim_4)
    val underlineHeightDp = dimensionResource(id = R.dimen.dim_2)
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(
                start = dimensionResource(id = R.dimen.dim_20),
                end = dimensionResource(id = R.dimen.dim_20),
                top = dimensionResource(id = R.dimen.dim_10),
                bottom = dimensionResource(id = R.dimen.dim_10)
            ),
        horizontalArrangement = horizontalArrangement
    ) {
        if (isInternetConnected) {
            tabsList.forEachIndexed { index, data ->
                val isSelected = index == mainTabIndex

                Text(
                    text = data,
                    modifier = Modifier
                        .clickable {
                            onTabChanged(index)
                            mainTabIndex = index
                        }
                        .drawBehind {
                            if (isSelected) {
                                val strokeWidthPx = strokeWidthDp.toPx()
                                val topPaddingPx = topPaddingDp.toPx()
                                val underlineHeight = underlineHeightDp.toPx()
                                val verticalOffset =
                                    size.height - (underlineHeight / 2) + topPaddingPx
                                drawLine(
                                    color = Color.Black,
                                    strokeWidth = strokeWidthPx,
                                    start = Offset(0f, verticalOffset),
                                    end = Offset(size.width, verticalOffset)
                                )
                            }
                        },

                    fontWeight = if (isSelected) FontWeight(500) else FontWeight.Normal,
                    color = if (isSelected) Color.Black else Color(0xFFC2C2C2),
                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_24).value.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = Montserrat,
                    letterSpacing = (-0.04).em
                )
            }
        } else {
            tabsList.forEachIndexed { index, data ->
                val isSelected = index == mainTabIndex

                Text(
                    text = data,
                    modifier = Modifier
                        .background(brush)
                        .clickable {
                        }
                        .drawBehind {
                            if (isSelected) {
                                val strokeWidthPx = strokeWidthDp.toPx()
                                val topPaddingPx = topPaddingDp.toPx()
                                val underlineHeight = underlineHeightDp.toPx()
                                val verticalOffset =
                                    size.height - (underlineHeight / 2) + topPaddingPx
                                drawLine(
                                    color = Color.Black,
                                    strokeWidth = strokeWidthPx,
                                    start = Offset(0f, verticalOffset),
                                    end = Offset(size.width, verticalOffset)
                                )
                            }
                        },
                    color = Color.Transparent,
                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_24).value.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = Montserrat,
                    letterSpacing = (-0.04).em
                )
            }
        }
    }
}

@Composable
fun SubCategoriesTabRow(
    subCategoriesTabIndex: Int,
    subCategoriesTabItemsList: List<Category>?,
    onCategoriesTabChanged: (Int) -> Unit,
    isInternetConnected: Boolean,
    brush: Brush
) {
    LazyRow(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.dim_15),
            end = dimensionResource(id = R.dimen.dim_15),
            bottom = dimensionResource(id = R.dimen.dim_5),
            top = dimensionResource(id = R.dimen.dim_0)
        ),
        content = {
            if (subCategoriesTabItemsList != null) {
                if (isInternetConnected) {
                    itemsIndexed(subCategoriesTabItemsList) { index, data ->
                        val selected = subCategoriesTabIndex == index
                        Button(
                            onClick = { onCategoriesTabChanged(index) },
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = if (selected) Color.Black else Color(0xFFC2C2C2)
                            ),
                            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_5)),
                            modifier = Modifier
                                .padding(
                                    horizontal = dimensionResource(id = R.dimen.dim_5)
                                )
                        ) {
                            Text(
                                text = data.displayName,
                                fontWeight = if (selected) FontWeight(600) else FontWeight.Normal,
                                fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                                lineHeight = dimensionResource(id = R.dimen.fon_19_5).value.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = Montserrat
                            )
                        }
                    }
                } else {
                    itemsIndexed(subCategoriesTabItemsList) { _, data ->
                        Text(
                            modifier = Modifier
                                .background(brush)
                                .clickable {},
                            text = data.displayName,
                            color = Color.Transparent,
                            fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                            lineHeight = dimensionResource(id = R.dimen.fon_19_5).value.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = Montserrat
                        )
                    }
                }
            }
        }
    )
}
