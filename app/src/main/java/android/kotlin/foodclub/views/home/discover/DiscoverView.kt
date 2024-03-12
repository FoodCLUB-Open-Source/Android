package android.kotlin.foodclub.views.home.discover

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.Satoshi
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.navigation.HomeOtherRoutes
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.EditIngredientBottomModal
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import android.kotlin.foodclub.utils.composables.IngredientsList
import android.kotlin.foodclub.utils.composables.shimmerBrush
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import android.kotlin.foodclub.viewModels.home.discover.DiscoverEvents
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DiscoverView(
    navController: NavController,
    events: DiscoverEvents,
    state: DiscoverState
) {
    val context = LocalContext.current
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))

    var isShowPost by remember { mutableStateOf(false) }
    var postId: Long? by remember { mutableStateOf(null) }

    val brush = shimmerBrush()
    var searchText by remember { mutableStateOf("") }

    var homePosts: List<VideoModel>?
    val worldPosts: State<List<VideoModel>>? = null

    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isDialogOpen by remember { mutableStateOf(false) }
    var alphaValue by remember { mutableFloatStateOf(1f) }

    alphaValue = if (isDialogOpen) {
        0.5f
    } else {
        1f
    }

    val initialPage = 0
    val pagerState1 = rememberPagerState(
        initialPage = initialPage,
        initialPageOffsetFraction = 0f,
        pageCount = { 4 }
    )
    val pagerState2 = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { 2 }
    )

    val fling = PagerDefaults.flingBehavior(
        state = pagerState1, lowVelocityAnimationSpec = tween(
            easing = LinearEasing, durationMillis = 300
        )
    )
    var mainTabIndex by remember { mutableIntStateOf(0) }
    val mainTabItemsList = stringArrayResource(id = R.array.discover_tabs)


    //New column to fit the pager
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .alpha(alphaValue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DiscoverViewHeader(
            navController = navController,
            userName = state.username
        )
        MainTabRow(
            isInternetConnected,
            brush,
            tabsList = mainTabItemsList,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            mainTabIndex = it
        }

        if (isSheetOpen) {
            EditIngredientBottomModal(
                ingredient = state.ingredientToEdit!!,
                onDismissRequest = { isSheetOpen = it },
                onEdit = {
                    events.updateIngredient(it)
                }
            )
        }

        if (isDatePickerVisible) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                CustomDatePicker(
                    modifier = Modifier.shadow(dimensionResource(id = R.dimen.dim_5)),
                    datePickerState = datePickerState,
                    onDismiss = {
                        isDatePickerVisible = false
                        datePickerState.setSelection(null)
                    },
                    onSave = { date ->
                        if (date != null) {
                            selectedDate = date
                            state.ingredientToEdit!!.expirationDate = selectedDate
                            events.updateIngredient(state.ingredientToEdit)
                        }
                    }
                )
            }
        }
        if (isDialogOpen) {
            AddIngredientDialog(
                stringResource(R.string.added),
                stringResource(R.string.successfully_added_first),
                stringResource(R.string.successfully_added_second),
                state.ingredientToEdit!!.type
            )
            LaunchedEffect(key1 = true) {
                delay(3000)
                isDialogOpen = false
            }
        }

        if (isShowPost && postId != null) {
            DiscoverViewPosts(
                postId = postId!!,
                posts = state.postList,
                events = events,
                state = state,
                onBackPressed = {
                    postId = null
                    isShowPost = !isShowPost
                }
            )
        }

        if (mainTabIndex == 0) {
            homePosts = state.postList

            VerticalPager(state = pagerState2, pageSize = PageSize.Fill) { page ->
                if (page == 0) {

                    if (isInternetConnected) {
                        if (mainTabIndex == 0) {
                            SubSearchBar(
                                navController = navController,
                                searchTextValue = searchText,//state.ingredientSearchText,
                                onSearch = { input ->
                                    searchText = input
                                    events.onSearchIngredientsList(input)
                                },
                                enableCamera = false,
                                enableMike = false
                            )
                        } else {
                            // TODO figure out what do show here
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_30)))
                        }
                    }

                    var subTabIndex by remember { mutableIntStateOf(0) }
                    SubTabRow(
                        onTabChanged = {
                            subTabIndex = it
                        },
                        isInternetConnected,
                        brush
                    )

                    //Ingredients list causes excess space
                    if (isInternetConnected) {

                        Log.d("Search Text", searchText.toString())
                        IngredientsList(
                            Modifier,
                            events = events,
                            productsList = if (state.searchIngredientsListText == "") state.userIngredients else state.searchResults,
                            userIngredientsList = if (state.searchIngredientsListText == "") state.userIngredients else state.searchResults,
                            onEditQuantityClicked = {
                                isSheetOpen = true
                                events.updateIngredient(it)
                            },
                            onDateClicked = {
                                events.updateIngredient(it)
                                isDatePickerVisible = true
                                events.updateIngredient(it)
                            },
                            onIngredientAdd = {},
                            onDeleteIngredient = {
                                events.deleteIngredientFromList(it)
                            }
                        )

                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isInternetConnected) {

                            Button(
                                onClick = {
                                    navController.navigate("ADD_INGREDIENTS")
                                },
                                shape = RoundedCornerShape(
                                    dimensionResource(
                                        id = R.dimen.dim_15
                                    )
                                ),
                                colors = ButtonDefaults.buttonColors(foodClubGreen),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.add_ingredients),
                                    fontSize = dimensionResource(
                                        id = R.dimen.fon_20
                                    ).value.sp,
                                    fontFamily = Montserrat
                                )
                            }

                        } else {
                            CircularProgressIndicator(
                                color = foodClubGreen,
                                strokeWidth = dimensionResource(id = R.dimen.dim_4)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    SlideInTitle(pagerState2 = pagerState2)

                    HorizontalPager(
                        beyondBoundsPageCount = 1,
                        flingBehavior = fling,
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.dim_500))
                            .padding(top = dimensionResource(id = R.dimen.dim_0)),
                        state = pagerState1
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = dimensionResource(id = R.dimen.dim_5),
                                    start = dimensionResource(id = R.dimen.dim_15),
                                    end = dimensionResource(id = R.dimen.dim_15),
                                    bottom = dimensionResource(id = R.dimen.dim_100)
                                )
                        ) {
                            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                                val userName = state.sessionUserUsername

                                if (isInternetConnected) {
                                    if (homePosts != null) {
                                        items(homePosts!!) { dataItem ->
                                            events.getPostData(dataItem.videoId)
                                            GridItem2(
                                                navController,
                                                dataItem,
                                                userName,
                                                isShowPost = {
                                                    postId = it
                                                    isShowPost = !isShowPost
                                                }
                                            )
                                        }
                                    } else if (worldPosts != null) {
                                        items(worldPosts.value) { dataItem ->
                                            events.getPostData(dataItem.videoId)
                                            GridItem2(
                                                navController,
                                                dataItem,
                                                userName,
                                                isShowPost = {
                                                    postId = it
                                                    isShowPost = !isShowPost
                                                }
                                            )
                                        }
                                    }
                                } else {
                                    items(8) {
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
                                                    .background(brush)
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

    }

    var showSheet by remember { mutableStateOf(false) }

    val triggerBottomSheetModal: () -> Unit = {
        showSheet = !showSheet
    }
    if (showSheet) {
        IngredientsBottomSheet(
            onDismiss = triggerBottomSheetModal,
            productsData = state.productsData
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlideInTitle(pagerState2: PagerState) {
    val pageOffset = pagerState2.currentPageOffsetFraction
    val maxDelay = integerResource(id = R.integer.int_200)
    val minDelay = integerResource(id = R.integer.int_50)
    val delay = (maxDelay - minDelay) * (1 - (pageOffset.absoluteValue * 2)) + minDelay

    AnimatedVisibility(
        visible = pagerState2.currentPage == 1 && pageOffset == 0.00f,
        enter = slideInHorizontally(animationSpec = tween(durationMillis = delay.toInt())) { fullWidth ->
            // Offsets the content by 1/3 of its width to the left, and slide towards right
            // Overwrites the default animation with tween for this slide animation.
            -fullWidth / 3
        } + fadeIn(
            // Overwrites the default animation with tween
            animationSpec = tween(durationMillis = delay.toInt())
        ),
        exit = slideOutHorizontally(animationSpec = tween(durationMillis = delay.toInt())) { fullWidth ->
            // Offsets the content by 1/3 of its width to the left, and slide towards right
            // Overwrites the default animation with tween for this slide animation.
            fullWidth / 3
        } + fadeOut(animationSpec = tween(durationMillis = delay.toInt()))
    ) {

        Text(
            text = stringResource(id = R.string.Recommendations),
            fontFamily = Montserrat,
            fontSize = dimensionResource(id = R.dimen.fon_25).value.sp
        )
    }
}

//MainSearchBar will be change to DiscoverViewHeader
//which is "Hi Emily" part
//Badged Icon for MyBasketView can be reused in the future

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainSearchBar(
//    navController: NavController,
//    basketCache: MyBasketCache
//) {
//    val basketCount = basketCache.getBasket().getIngredientCount()
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(
//                top = dimensionResource(id = R.dimen.dim_60),
//                end = dimensionResource(id = R.dimen.dim_20),
//                start = dimensionResource(id = R.dimen.dim_20),
//                bottom = dimensionResource(id = R.dimen.dim_10)
//            ),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth(0.85f)
//                .clip(
//                    RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
//                )
//                .background(containerColor)
//                .clickable {
//                    navController.navigate(HomeOtherRoutes.MySearchView.route)
//                }
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.SpaceAround,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = { }
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.search_icon_ingredients),
//                        contentDescription = null
//                    )
//                }
//                Text(
//                    modifier = Modifier
//                        .padding(top = dimensionResource(id = R.dimen.dim_3)),
//                    text = stringResource(id = R.string.search_for),
//                    color = Color.Gray,
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
//        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))
//
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
//
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
    navController: NavController,
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
                    append("${stringResource(id = R.string.hi)} $userName,")
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
                    navController.navigate(HomeOtherRoutes.MySearchView.route)
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.search_icon_transparent_background),
                    contentDescription = stringResource(id = R.string.search_my_ingredients),
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))

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
                    //navController.navigate("ScanView_route")
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.camera_icon),
                    contentDescription = stringResource(id = R.string.scan_my_fridge),
                    tint = Color.Black
                )
            }
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
                if (tabsList[0] != stringResource(id = R.string.my_kitchen)) {
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_50)))
                }
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
                if (tabsList[0] != stringResource(id = R.string.my_kitchen)) {
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_50)))
                }
            }
        }
    }
}


@Composable
fun SubSearchBar(
    navController: NavController,
    searchTextValue: String,
    onSearch: (String) -> Unit,
    enableCamera: Boolean = true,
    enableMike: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dim_20),
                end = dimensionResource(id = R.dimen.dim_20),
                start = dimensionResource(id = R.dimen.dim_20),
                bottom = dimensionResource(id = R.dimen.dim_15)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(if (enableCamera && enableMike) 0.68f else 1.0f)
                .clip(
                    RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            value = searchTextValue,
            onValueChange = {
                onSearch(it)
            },
            singleLine = true,
            placeholder = {
                Text(
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_3)),
                    text = stringResource(id = R.string.search_to),
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        // TODO impl search
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.search_icon_ingredients),
                        contentDescription = null,
                    )
                }
            }
        )

        if (enableCamera) {
            Button(
                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dim_56))
                    .width(dimensionResource(id = R.dimen.dim_56)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = foodClubGreen,
                ),
                contentPadding = PaddingValues(),
                onClick = {
                    navController.navigate("ScanView_route")
                }
            ) {
                Icon(painterResource(id = R.drawable.camera_icon), contentDescription = "")
            }
        }

        if (enableMike) {
            Button(
                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dim_56))
                    .width(dimensionResource(id = R.dimen.dim_56)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = foodClubGreen,
                ),
                contentPadding = PaddingValues(),
                onClick = {

                    // TODO impl microphone
                }
            ) {
                Icon(painterResource(id = R.drawable.mic_icon), contentDescription = "")
            }
        }
    }
}

@Composable
fun SubTabRow(
    onTabChanged: (Int) -> Unit,
    isInternetConnected: Boolean,
    brush: Brush
) {
    val strokeWidthDp = dimensionResource(id = R.dimen.dim_2)
    val topPaddingDp = dimensionResource(id = R.dimen.dim_4)
    val underlineHeightDp = dimensionResource(id = R.dimen.dim_2)
    val subTabItemsList = stringArrayResource(id = R.array.discover_sub_tabs)
    var subTabIndex by remember { mutableIntStateOf(0) }


    LazyRow(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.dim_20),
            end = dimensionResource(id = R.dimen.dim_20),
            bottom = dimensionResource(id = R.dimen.dim_5),
            top = dimensionResource(id = R.dimen.dim_10)
        ),

        content = {
            if (isInternetConnected) {
                itemsIndexed(subTabItemsList) { index, data ->
                    val selected = subTabIndex == index

                    Text(
                        modifier = Modifier
                            .drawBehind {
                                if (selected) {
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
                            }
                            .clickable {
                                subTabIndex = index
                                onTabChanged(index)
                            },
                        text = data,
                        fontWeight = if (selected) FontWeight(500) else FontWeight.Normal,
                        color = if (selected) Color.Black else Color(0xFFC2C2C2),
                        fontSize = dimensionResource(id = R.dimen.fon_17).value.sp,
                        lineHeight = dimensionResource(id = R.dimen.fon_21).value.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = Montserrat
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_50)))
                }
            } else {
                itemsIndexed(subTabItemsList) { index, data ->
                    val selected = subTabIndex == index

                    Text(
                        modifier = Modifier
                            .background(brush)
                            .drawBehind {
                                if (selected) {
                                    val strokeWidthPx = strokeWidthDp.toPx()
                                    val topPaddingPx =
                                        topPaddingDp.toPx()
                                    val underlineHeight =
                                        underlineHeightDp.toPx()
                                    val verticalOffset =
                                        size.height - (underlineHeight / 2) + topPaddingPx
                                    drawLine(
                                        color = Color.Black,
                                        strokeWidth = strokeWidthPx,
                                        start = Offset(0f, verticalOffset),
                                        end = Offset(size.width, verticalOffset)
                                    )
                                }
                            }
                            .clickable {},
                        text = data,
                        color = Color.Transparent,
                        fontSize = dimensionResource(id = R.dimen.fon_17).value.sp,
                        lineHeight = dimensionResource(id = R.dimen.fon_21).value.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = Montserrat
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_50)))
                }
            }
        })


}


@Composable
fun AddIngredientDialog(
    headline: String,
    textFirst: String,
    textSecond: String? = "",
    ingredientName: String? = ""
) {
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                .width(dimensionResource(id = R.dimen.dim_500))
                .fillMaxHeight(0.25f)
                .background(Color.White),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = dimensionResource(id = R.dimen.dim_10)
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_10)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.dim_10)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_34))
                            .clip(CircleShape)
                            .background(foodClubGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_24)),
                            imageVector = Icons.Default.Check,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = headline,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dim_10)),
                        fontWeight = FontWeight(600),
                        lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                        fontFamily = Montserrat
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.dim_10),
                            horizontal = dimensionResource(id = R.dimen.dim_30)
                        ),
                ) {
                    Text(
                        text = "$textFirst $ingredientName $textSecond",
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                        lineHeight = dimensionResource(id = R.dimen.fon_17).value.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabHomeDiscover(
    tabItems: List<String>,
    pagerState1: PagerState,
    scope: CoroutineScope,
    fontSize: TextUnit
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
                height = dimensionResource(id = R.dimen.dim_0),
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
fun GridItem2(
    navController: NavController,
    dataItem: VideoModel,
    userName: String,
    brush: Brush = shimmerBrush(),
    isShowPost: (Long) -> Unit
) {
    val thumbnailPainter = rememberAsyncImagePainter(dataItem.thumbnailLink)
    Card(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dim_272))
            .width(dimensionResource(id = R.dimen.dim_178))
            .padding(dimensionResource(id = R.dimen.dim_10)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (thumbnailPainter.state is AsyncImagePainter.State.Loading) brush
                    else SolidColor(Color.Transparent)
                )
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            val thumbnailPainterOrDefault = if (thumbnailPainter != null) {
                thumbnailPainter
            } else {
                painterResource(id = R.color.gray)
            }

            Image(
                painter = thumbnailPainterOrDefault,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        isShowPost(dataItem.videoId)
                    },
                contentScale = ContentScale.FillHeight
            )

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = dataItem.videoStats.displayLike,
                    fontFamily = Satoshi,
                    color = Color.White,
                    fontSize = dimensionResource(id = R.dimen.fon_15).value.sp
                )
            }
        }
    }
}