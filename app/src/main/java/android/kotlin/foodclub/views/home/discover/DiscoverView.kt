package android.kotlin.foodclub.views.home.discover

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.Satoshi
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.navigation.HomeOtherRoutes
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.EditIngredientBottomModal
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import android.kotlin.foodclub.utils.composables.shimmerBrush
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import android.kotlin.foodclub.viewModels.home.discover.DiscoverEvents
import android.kotlin.foodclub.views.home.myDigitalPantry.TitlesSection
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White, darkIcons = true
        )
    }

    var showIngredientSheet by remember { mutableStateOf(false) }

    val triggerIngredientBottomSheetModal: () -> Unit = {
        showIngredientSheet = !showIngredientSheet
    }

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

    val fling = PagerDefaults.flingBehavior(
        state = pagerState1, lowVelocityAnimationSpec = tween(
            easing = LinearEasing, durationMillis = 300
        )
    )
    var mainTabIndex by remember { mutableIntStateOf(0) }
    val mainTabItemsList = stringArrayResource(id = R.array.discover_tabs)



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .alpha(alphaValue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            MainSearchBar(
                navController = navController,
                basketCache = state.myBasketCache!!
            )
        }

        item {
            MainTabRow(
                isInternetConnected,
                brush,
                tabsList = mainTabItemsList,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                mainTabIndex = it
            }
        }

        if (isInternetConnected) {
            item {
                if (mainTabIndex == 0) {
                    SubSearchBar(
                        navController = navController,
                        searchTextValue = searchText,//state.ingredientSearchText,
                        onSearch = { input ->
                            searchText = input
                            //events.onAddIngredientsSearchTextChange(input)
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
        }


        item {
            var subTabIndex by remember { mutableIntStateOf(0) }
            SubTabRow(
                onTabChanged = {
                    subTabIndex = it
                },
                isInternetConnected,
                brush
            )
        }

        item {
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
        }
        if (isShowPost && postId != null) {
            item {
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
        }

        item {
            if (mainTabIndex == 0) {
                homePosts = state.postList

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
                    /*
                    if (searchText.isBlank()) {
                        IngredientsList(
                            Modifier,
                            events = events,
                            productsList = state.userIngredients,
                            userIngredientsList = state.userIngredients,
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
                    else
                    {
                        IngredientsList(
                            Modifier,
                            events = events,
                            productsList = state.productsData.productsList,
                            userIngredientsList = state.userIngredients,
                            onEditQuantityClicked = {
                                events.updateIngredient(it)
                            },
                            onDateClicked = {
                                isDatePickerVisible = true
                                events.updateIngredient(it)
                            },
                            onIngredientAdd = {
                                events.addToUserIngredients(it)
                                //searchText = ""
                                isDialogOpen = true
                            },
                            onDeleteIngredient = {
                                events.deleteIngredientFromList(it)
                            }
                        )
                    }

                     */
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isInternetConnected) {

                        Button(
                            onClick = { triggerIngredientBottomSheetModal() },
                            shape = RoundedCornerShape(
                                dimensionResource(
                                    id = R.dimen.dim_15
                                )
                            ),
                            colors = ButtonDefaults.buttonColors(foodClubGreen),
                            //contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_5))
                        ) {
                            Text(
                                text = stringResource(id = R.string.add_ingredients),
                                fontSize = dimensionResource(
                                    id = R.dimen.fon_20
                                ).value.sp,
                                fontFamily = Montserrat
                            )
                        }

                        // TO BE REMOVED
                        Text(
                            modifier = Modifier.clickable {
                                navController.navigate(route = HomeOtherRoutes.MyDigitalPantryView.route)
                            },
                            text = stringResource(id = R.string.see_all_ingredients),
                            color = foodClubGreen,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                            fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                            textAlign = TextAlign.Center
                        )


                    } else {
                        CircularProgressIndicator(
                            color = foodClubGreen,
                            strokeWidth = dimensionResource(id = R.dimen.dim_4)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
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
        IngredientsBottomSheet(
            onDismiss = triggerBottomSheetModal,
            productsData = state.productsData
        )
    }


    if (showIngredientSheet) {
        AddIngredientsBottomSheet(onDismiss = triggerIngredientBottomSheetModal, isInternetConnected, events=events, state=state, navController = navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchBar(
    navController: NavController,
    basketCache: MyBasketCache
) {
    val basketCount = basketCache.getBasket().getIngredientCount()
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(
                    RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                )
                .background(containerColor)
                .clickable {
                    navController.navigate(HomeOtherRoutes.MySearchView.route)
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon_ingredients),
                        contentDescription = null
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.dim_3)),
                    text = stringResource(id = R.string.search_for),
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))

        Button(
            shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dim_56))
                .width(dimensionResource(id = R.dimen.dim_56)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.discover_view_basket_icon_container_color),
            ),
            contentPadding = PaddingValues(),
            onClick = {}
        ) {

            BadgedBox(
                modifier = Modifier.clickable {
                    navController.navigate(HomeOtherRoutes.MyBasketView.route)
                },
                badge = {
                    Badge(
                        modifier = Modifier.offset(
                            x = -dimensionResource(id = R.dimen.dim_5),
                            y = dimensionResource(id = R.dimen.dim_5)
                        ),
                        containerColor = foodClubGreen
                    )
                    { Text(text = (basketCount).toString(), color = Color.Black) }
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.vector__1_),
                    contentDescription = stringResource(id = R.string.add_to_basket),
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
            .fillMaxWidth()
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
                    textAlign = TextAlign.Start,
                    fontFamily = Montserrat
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
                    textAlign = TextAlign.Start,
                    fontFamily = Montserrat
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
                .fillMaxWidth( if (enableCamera && enableMike) 0.68f else 1.0f)
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
fun IngredientsList(
    modifier: Modifier,
    events: DiscoverEvents,
    productsList: List<Ingredient>,
    onEditQuantityClicked: (Ingredient) -> Unit,
    onDateClicked: (Ingredient) -> Unit,
    onIngredientAdd: (Ingredient) -> Unit,
    onDeleteIngredient: (Ingredient) -> Unit,
    userIngredientsList: List<Ingredient>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            //.fillMaxHeight(0.57f)
            .background(
                color = Color.White
            )
    ) {
        TitlesSection(
            modifier = modifier,
            view = stringResource(id = R.string.discover_view)
        )


        IngredientsListColumn(
            events = events,
            productsList = productsList,
            userIngredientsList = userIngredientsList,
            onEditQuantityClicked = { onEditQuantityClicked(it) },
            onDateClicked = { onDateClicked(it) },
            onIngredientAdd = { onIngredientAdd(it) },
            onDeleteIngredient = { onDeleteIngredient(it) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsListColumn(
    events: DiscoverEvents,
    productsList: List<Ingredient>,
    onEditQuantityClicked: (Ingredient) -> Unit,
    onDateClicked: (Ingredient) -> Unit,
    onIngredientAdd: (Ingredient) -> Unit,
    onDeleteIngredient: (Ingredient) -> Unit,
    userIngredientsList: List<Ingredient>
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.dim_15),
                end = dimensionResource(id = R.dimen.dim_15)
            )
            .background(Color.White)
            .height(dimensionResource(id = R.dimen.dim_275)),
        content = {
            itemsIndexed(productsList) { _, item ->
                var notSwiped by remember { mutableStateOf(false) }
                val dismissState = rememberDismissState(
                    confirmValueChange = { dismiss ->
                        if (dismiss == DismissValue.DismissedToEnd) notSwiped =
                            !notSwiped
                        dismiss != DismissValue.DismissedToEnd
                    }
                )

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    LaunchedEffect(key1 = true) {
                        onDeleteIngredient(item)
                        dismissState.reset()
                    }
                } else {
                    LaunchedEffect(key1 = true) {
                        dismissState.reset()
                    }
                }
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.White
                                DismissValue.DismissedToEnd -> Color.White
                                DismissValue.DismissedToStart -> Color.Red
                            }, label = ""
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
                            label = ""
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = dimensionResource(id = R.dimen.dim_20)),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = null,
                                modifier = Modifier.scale(scale),
                                tint = Color.White
                            )
                        }
                    },
                    dismissContent = {
                        SingleSearchIngredientItem(
                            modifier = Modifier,
                            item = item,
                            userIngredientsList = userIngredientsList,
                            onEditQuantityClicked = {
                                events.updateIngredient(it)
                                onEditQuantityClicked(item)
                            },
                            onDateClicked = {
                                events.updateIngredient(it)
                                onDateClicked(item)
                            },
                            onAddItemClicked = {
                                events.updateIngredient(it)
                                onIngredientAdd(item)
                            }
                        )
                    }
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.dim_8)))
                Divider(
                    thickness = dimensionResource(id = R.dimen.dim_1),
                    modifier = Modifier.alpha(0.5f),
                    color = Color.LightGray
                )
                Spacer(Modifier.height(dimensionResource(id = R.dimen.dim_8)))

            }
        }
    )
}

@Composable
fun SingleSearchIngredientItem(
    modifier: Modifier,
    item: Ingredient,
    onEditQuantityClicked: (Ingredient) -> Unit,
    onDateClicked: (Ingredient) -> Unit,
    onAddItemClicked: (Ingredient) -> Unit,
    userIngredientsList: List<Ingredient>
) {
    val unit = stringResource(id = R.string.gram_unit) // TODO make this dynamic
    val quantity = itemQuantity(item, unit)
    val expirationDate = itemExpirationDate(item)
    val isItemAdded = item in userIngredientsList

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Column(
            modifier = modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(dimensionResource(id = R.dimen.dim_48))
                        .width(dimensionResource(id = R.dimen.dim_48))
                        .clip(CircleShape)
                )
                Text(
                    modifier = modifier.padding(start = dimensionResource(id = R.dimen.dim_6)),
                    text = item.type,
                    fontWeight = FontWeight(500),
                    lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    color = Color.Black,
                    maxLines = integerResource(id = R.integer.int_2),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Column(modifier = modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (isItemAdded) {
                    Text(
                        modifier = modifier
                            .padding(start = dimensionResource(id = R.dimen.dim_6))
                            .clickable {
                                onEditQuantityClicked(item)
                            },
                        text = quantity,
                        fontWeight = FontWeight(500),
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                        lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                        fontFamily = Montserrat,
                        color = Color.Gray,
                        style = quantityTextStyle(quantity)
                    )

                } else {
                    Box(modifier = Modifier.weight(1f, fill = false))
                    Spacer(modifier = Modifier.weight(1f, fill = true))
                }
            }
        }
        Column(modifier = modifier.weight(1f)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isItemAdded) {
                    Text(
                        modifier = modifier
                            .padding(start = dimensionResource(id = R.dimen.dim_20))
                            .clickable {
                                onDateClicked(item)
                            },
                        text = expirationDate,
                        fontWeight = FontWeight(500),
                        textAlign = TextAlign.Start,
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                        lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                        fontFamily = Montserrat,
                        color = Color.Gray,
                        style = expirationDateTextStyle(expirationDate)
                    )
                } else {
                    Box(modifier = Modifier.weight(1f, fill = false))
                    Spacer(modifier = Modifier.weight(1f, fill = true))
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_24))
                            .clip(CircleShape)
                            .background(foodClubGreen)
                            .clickable {
                                onAddItemClicked(item)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EditIngredientBottomModal(
//    ingredient: Ingredient,
//    onDismissRequest: (Boolean) -> Unit,
//    onEdit: (Ingredient) -> Unit
//) {
//    val sheetState = rememberModalBottomSheetState()
//    ModalBottomSheet(
//        sheetState = sheetState,
//        onDismissRequest = {
//            onDismissRequest(false)
//        },
//        modifier = Modifier.height(400.dp),
//        containerColor = Color.White
//    ) {
//        val pickerValues = remember {
//            mutableStateOf((1..10).map {
//                Pair(
//                    it,
//                    (it * 100).toString() + ValueParser.quantityUnitToString(ingredient.unit)
//                )
//            })
//        }
//        val units = listOf(QuantityUnit.GRAMS, QuantityUnit.KILOGRAMS, QuantityUnit.LITERS, QuantityUnit.MILLILITERS,)
//
//
//        val quantity = pickerValues.value.map { it.first }
//        val grammage = pickerValues.value.map { it.second }
//        val types = stringArrayResource(id = R.array.quantity_list).toList()
//        Column(
//            modifier = Modifier
//                .heightIn(max = dimensionResource(id = R.dimen.dim_350))
//                .padding(
//                    start = dimensionResource(id = R.dimen.dim_10),
//                    end = dimensionResource(id = R.dimen.dim_10)
//                )
//        ) {
//
//            EditIngredientQuantityPicker(
//                ingredient = ingredient,
//                quantity = quantity,
//                grammage = grammage,
//                types = types,
//                onEditIngredient = {
//                    onEdit(it)
//                    onDismissRequest(false)
//                }
//            )
//        }
//    }
//}

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


@Composable
fun itemQuantity(item: Ingredient, unit: String): String {
    return if (item.quantity != 0) item.quantity.toString() + unit else stringResource(id = R.string.edit)
}

@Composable
fun itemExpirationDate(item: Ingredient): String {
    return if (item.expirationDate != "") {
        item.expirationDate.split(" ").take(2).joinToString(" ")
    } else stringResource(id = R.string.edit)
}

@Composable
fun quantityTextStyle(quantity: String): TextStyle {
    return if (quantity == stringResource(id = R.string.edit)) TextStyle(textDecoration = TextDecoration.Underline) else TextStyle(
        textDecoration = TextDecoration.None
    )
}

@Composable
fun expirationDateTextStyle(expirationDate: String): TextStyle {
    return if (expirationDate == stringResource(id = R.string.edit)) TextStyle(textDecoration = TextDecoration.Underline) else TextStyle(
        textDecoration = TextDecoration.None
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientsBottomSheet(onDismiss: () -> Unit, isInternetConnected: Boolean, state: DiscoverState, events: DiscoverEvents, navController: NavController) {
    val screenHeight =
        LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_160)
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSmallScreen by remember { mutableStateOf(false) }

    val categories = listOf("Protein", "Breakfast")

    var inputText by remember { mutableStateOf("") }
    val brush = shimmerBrush()

    val mainTabItemsList = stringArrayResource(id = R.array.discover_tabs)

    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isDialogOpen by remember { mutableStateOf(false) }
    var alphaValue by remember { mutableFloatStateOf(1f) }


    var topTabIndex by remember { mutableIntStateOf(0) }

    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
    }

    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        scrimColor = Color.Transparent
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f))
        {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                IconButton(onClick = { onDismiss() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_left),
                        contentDescription = "Exit"
                    )
                }

                Text(
                    text = stringResource(id = R.string.add_ingredients),
                    fontSize = dimensionResource(
                        id = R.dimen.fon_25
                    ).value.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )

                /*
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(
                            id = R.dimen.dim_20
                        ).value.sp,
                        color = foodClubGreen
                    )
                }

                 */
            }



            /*
            MainTabRow(
                isInternetConnected,
                brush,
                tabsList = mainTabItemsList,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                topTabIndex = it
            }

             */


            if (isInternetConnected) {

                if (topTabIndex == 0) {
                    SubSearchBar(
                        navController = navController,
                        searchTextValue = state.ingredientSearchText,
                        onSearch = { input ->
                            inputText = input
                            events.onAddIngredientsSearchTextChange(input)
                        }
                    )
                } else {
                    // TODO figure out what do show here
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_30)))
                }

            }


            /*
            var subTabIndex by remember { mutableIntStateOf(0) }
            SubTabRow(
                onTabChanged = {
                    subTabIndex = it
                },
                isInternetConnected,
                brush
            )

             */


            var homePosts: List<VideoModel>? = state.postList

            if (isInternetConnected) {

                if (inputText.isBlank()) {
                    /*
                    IngredientsList(
                        Modifier,
                        events = events,
                        productsList = state.userIngredients,
                        userIngredientsList = state.userIngredients,
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

                     */
                } else {
                    IngredientsList(
                        Modifier,
                        events = events,
                        productsList = state.productsData.productsList,
                        userIngredientsList = state.userIngredients,
                        onEditQuantityClicked = {
                            events.updateIngredient(it)
                        },
                        onDateClicked = {
                            isDatePickerVisible = true
                            events.updateIngredient(it)
                        },
                        onIngredientAdd = {
                            events.addToUserIngredients(it)
                            isDialogOpen = false
                        },
                        onDeleteIngredient = {
                            events.deleteIngredientFromList(it)
                        }
                    )
                }
            }


        }
    }
}

