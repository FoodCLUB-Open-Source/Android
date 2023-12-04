package android.kotlin.foodclub.views.home.discover

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.Satoshi
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.navigation.HomeOtherRoutes
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.EditIngredientQuantityPicker
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import android.kotlin.foodclub.utils.helpers.ValueParser
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import android.kotlin.foodclub.viewModels.home.DiscoverViewModel
import android.kotlin.foodclub.views.home.myDigitalPantry.TitlesSection
import android.kotlin.foodclub.viewModels.home.MyBasketViewModel
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DiscoverView(
    navController: NavController,
    viewModel: DiscoverViewModel,
    state: DiscoverState,
    myBasketViewModel: MyBasketViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_240)

    var isSmallScreen by remember { mutableStateOf(false) }
    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
    }


    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White, darkIcons = true
        )
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
        0.7f
    } else {
        1f
    }

    val datePickerDialogColors = DatePickerDefaults.colors(
        containerColor = Color.White,
        titleContentColor = Color.White,
        headlineContentColor = Color.White,
    )
    val datePickerColors = DatePickerDefaults.colors(
        weekdayContentColor = Color.Gray,
        selectedDayContainerColor = Color.Red,
        todayDateBorderColor = Color.Red,
        todayContentColor = Color.Red
    )

    viewModel.getPostsByWorld(197)
    viewModel.getPostsByUserId()
    viewModel.myFridgePosts()

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
                searchTextValue = state.mainSearchText,
                navController = navController,
                basketCache = viewModel.myBasketCache
            )
        }

        item {
            MainTabRow(
                tabsList = mainTabItemsList,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                mainTabIndex = it
            }
        }

        item {
            if (mainTabIndex == 0){
                SubSearchBar(
                    navController = navController,
                    searchTextValue = state.ingredientSearchText,
                    onSearch = { input->
                        searchText = input
                        viewModel.onSubSearchTextChange(input)
                    }
                )
            }else{
                // TODO figure out what do show here
                Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_30)))
            }
        }


        item {
            var subTabIndex by remember { mutableIntStateOf(0) }
            SubTabRow(
                onTabChanged = {
                    subTabIndex = it
                }
            )
        }

        item {
            if (isSheetOpen) {
                EditIngredientBottomModal(
                    ingredient = state.ingredientToEdit!!,
                    onDismissRequest = { isSheetOpen = it },
                    onEdit = {
                        viewModel.updateIngredient(it)
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
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_6)),
                        datePickerState = datePickerState,
                        datePickerColors = datePickerColors,
                        datePickerDialogColors = datePickerDialogColors,
                        onDismiss = { isDatePickerVisible = false },
                        onSave = { date ->
                            if (date != null) {
                                selectedDate = date
                                state.ingredientToEdit!!.expirationDate = selectedDate
                                viewModel.updateIngredient(state.ingredientToEdit!!)
                            }
                        }
                    )
                }
            }
            if (isDialogOpen){
                AddIngredientDialog(stringResource(R.string.added), stringResource(R.string.successfully_added))
                LaunchedEffect(key1 = true){
                    delay(3000)
                    isDialogOpen = false
                }
            }
        }

        item {
            if (mainTabIndex == 0) {
                homePosts = state.postList

                if (searchText.isBlank()) {
                    IngredientsList(
                        Modifier,
                        viewModel = viewModel,
                        productsList = state.userIngredients,
                        userIngredientsList = state.userIngredients,
                        onEditQuantityClicked = {
                            isSheetOpen = true
                            viewModel.updateIngredient(it)
                        },
                        onDateClicked = {
                            viewModel.updateIngredient(it)
                            isDatePickerVisible = true
                            viewModel.updateIngredient(it)
                        },
                        onIngredientAdd = {},
                        onDeleteIngredient = {
                            viewModel.deleteIngredientFromList(it)
                        }
                    )
                } else {
                    IngredientsList(
                        Modifier,
                        viewModel = viewModel,
                        productsList = state.searchResults,
                        userIngredientsList = state.userIngredients,
                        onEditQuantityClicked = {
                            viewModel.updateIngredient(it)
                            viewModel.updateIngredient(it)
                        },
                        onDateClicked = {
                            isDatePickerVisible = true
                            viewModel.updateIngredient(it)
                            viewModel.updateIngredient(it)
                        },
                        onIngredientAdd = {
                            viewModel.addToUserIngredients(it)
                            searchText = ""
                            isDialogOpen = true
                        },
                        onDeleteIngredient = {
                            viewModel.deleteIngredientFromList(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))


                HorizontalPager(
                    beyondBoundsPageCount = 1,
                    flingBehavior = fling,
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dim_1000))
                        .padding(top =dimensionResource(id = R.dimen.dim_0)),
                    state = pagerState1
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                top =dimensionResource(id = R.dimen.dim_5),
                                start = dimensionResource(id = R.dimen.dim_15),
                                end = dimensionResource(id = R.dimen.dim_15),
                                bottom = dimensionResource(id = R.dimen.dim_100)
                            )
                    ) {
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            val userName = state.sessionUserUsername

                            if (homePosts != null) {
                                items(homePosts!!) { dataItem ->
                                    viewModel.getPostData(dataItem.videoId)
                                    GridItem2(navController, dataItem, userName)
                                }
                            } else if (worldPosts != null) {
                                items(worldPosts.value) { dataItem ->

                                    viewModel.getPostData(dataItem.videoId)
                                    GridItem2(navController, dataItem, userName)
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

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchBar(
    searchTextValue: String,
    navController: NavController,
    basketCache: MyBasketCache
) {
    val basketCount = basketCache.getBasket().getIngredientCount()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.dim_60), end = dimensionResource(id = R.dimen.dim_20), start = dimensionResource(id = R.dimen.dim_20), bottom = dimensionResource(id = R.dimen.dim_10)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(
                    RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                )
                .pointerInput(Unit) {
                    navController.navigate(HomeOtherRoutes.MySearchView.route)
                },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = containerColor
            ),
            value = searchTextValue,
            onValueChange = {

            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top =dimensionResource(id = R.dimen.dim_3)),
                    text = stringResource(id = R.string.search_for),
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painterResource(id = R.drawable.search_icon_ingredients),
                        contentDescription = null,
                    )
                }
            }
        )

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))

        Button(
            shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dim_56))
                .width(dimensionResource(id = R.dimen.dim_56)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(245, 245, 245, 255),
            ),
            contentPadding = PaddingValues(),
            onClick = {
                // TODO impl search - this can be done
            }
        ) {

            BadgedBox(
                modifier = Modifier.clickable {
                    navController.navigate(HomeOtherRoutes.MyBasketView.route)
                },
                badge = {
                    Badge(
                        modifier = Modifier.offset(x = (-5).dp, y =dimensionResource(id = R.dimen.dim_5)),
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
    tabsList: Array<String>,
    horizontalArrangement: Arrangement.Horizontal,
    onTabChanged: (Int) -> Unit
) {
    var mainTabIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = dimensionResource(id = R.dimen.dim_20), end = dimensionResource(id = R.dimen.dim_20), top = dimensionResource(id = R.dimen.dim_10), bottom = dimensionResource(id = R.dimen.dim_10)),
        horizontalArrangement = horizontalArrangement
    ) {
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
                            val strokeWidthPx = 2.dp.toPx()
                            val topPaddingPx =4.dp.toPx()
                            val underlineHeight = 2.dp.toPx()
                            val verticalOffset = size.height - (underlineHeight / 2) + topPaddingPx
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
                lineHeight = 24.38.sp,
                textAlign = TextAlign.Start,
                fontFamily = Montserrat
            )
            if (tabsList[0] != stringResource(id = R.string.my_kitchen)) {
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_50)))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubSearchBar(
    navController: NavController,
    searchTextValue: String,
    onSearch: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.dim_20), end = dimensionResource(id = R.dimen.dim_20), start = dimensionResource(id = R.dimen.dim_20), bottom = dimensionResource(id = R.dimen.dim_15)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.68f)
                .clip(
                    RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = containerColor
            ),
            value = searchTextValue,
            onValueChange = {
                onSearch(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top =dimensionResource(id = R.dimen.dim_3)),
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

@Composable
fun SubTabRow(
    onTabChanged: (Int) -> Unit
) {
    val subTabItemsList = stringArrayResource(id = R.array.discover_sub_tabs)
    var subTabIndex by remember { mutableIntStateOf(0) }

    LazyRow(
        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dim_20), end = dimensionResource(id = R.dimen.dim_20), bottom =dimensionResource(id = R.dimen.dim_5), top = dimensionResource(id = R.dimen.dim_10)),
        content = {
            itemsIndexed(subTabItemsList) { index, data ->
                val selected = subTabIndex == index

                Text(
                    modifier = Modifier
                        .drawBehind {
                            if (selected) {
                                val strokeWidthPx = 2.dp.toPx()
                                val topPaddingPx =
                                    4.dp.toPx()
                                val underlineHeight =
                                    2.dp.toPx()
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
                    lineHeight = 20.88.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = Montserrat
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_50)))
            }
        }
    )
}

@Composable
fun IngredientsList(
    modifier: Modifier,
    viewModel: DiscoverViewModel,
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
            .fillMaxHeight(0.57f)
            .background(
                color = Color.White
            )
    ) {
        TitlesSection(
            modifier = modifier,
            view = stringResource(id = R.string.discover_view)
        )

        IngredientsListColumn(
            viewModel = viewModel,
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
    viewModel: DiscoverViewModel,
    productsList: List<Ingredient>,
    onEditQuantityClicked: (Ingredient) -> Unit,
    onDateClicked: (Ingredient) -> Unit,
    onIngredientAdd: (Ingredient) -> Unit,
    onDeleteIngredient: (Ingredient) -> Unit,
    userIngredientsList: List<Ingredient>
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = dimensionResource(id = R.dimen.dim_15), end = dimensionResource(id = R.dimen.dim_15))
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
                                viewModel.updateIngredient(it)
                                onEditQuantityClicked(item)
                            },
                            onDateClicked = {
                                viewModel.updateIngredient(it)
                                onDateClicked(item)
                            },
                            onAddItemClicked = {
                                viewModel.updateIngredient(it)
                                onIngredientAdd(item)
                            }
                        )
                    }
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.dim_8)))
                Divider(thickness =dimensionResource(id = R.dimen.dim_1), modifier = Modifier.alpha(0.5f), color = Color.LightGray)
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
                    modifier = modifier.padding(start =dimensionResource(id = R.dimen.dim_6)),
                    text = item.type,
                    fontWeight = FontWeight(500),
                    lineHeight = 19.5.sp,
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    color = Color.Black
                )
            }
        }
        Column(modifier = modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = modifier
                        .padding(start =dimensionResource(id = R.dimen.dim_6))
                        .clickable {
                            onEditQuantityClicked(item)
                        },
                    text = quantity,
                    fontWeight = FontWeight(500),
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    lineHeight = 19.5.sp,
                    fontFamily = Montserrat,
                    color = Color.Gray,
                    style = quantityTextStyle(quantity)
                )
            }
        }
        Column(modifier = modifier.weight(1f)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = modifier
                        .padding(start = dimensionResource(id = R.dimen.dim_10))
                        .clickable {
                            onDateClicked(item)
                        },
                    text = expirationDate,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Start,
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    lineHeight = 19.5.sp,
                    fontFamily = Montserrat,
                    color = Color.Gray,
                    style = expirationDateTextStyle(expirationDate)
                )
                if (!isItemAdded) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditIngredientBottomModal(
    ingredient: Ingredient,
    onDismissRequest: (Boolean) -> Unit,
    onEdit: (Ingredient) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismissRequest(false)
        },
        containerColor = Color.White
    ) {
        val pickerValues = remember {
            mutableStateOf((1..10).map {
                Pair(
                    it,
                    (it * 100).toString() + ValueParser.quantityUnitToString(ingredient.unit)
                )
            })
        }

        val quantity = pickerValues.value.map { it.first }
        val grammage = pickerValues.value.map { it.second }
        val types = stringArrayResource(id = R.array.quantity_list).toList()
        Column(
            modifier = Modifier
                .heightIn(max = dimensionResource(id = R.dimen.dim_350))
                .padding(start = dimensionResource(id = R.dimen.dim_10), end = dimensionResource(id = R.dimen.dim_10))
        ) {
            EditIngredientQuantityPicker(
                ingredient = ingredient,
                quantity = quantity,
                grammage = grammage,
                types = types,
                onEditIngredient = {
                    onEdit(it)
                    onDismissRequest(false)
                }
            )
        }
    }
}

@Composable
fun AddIngredientDialog(headline: String, text: String){
    Dialog(
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false),
        onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                .width(dimensionResource(id = R.dimen.dim_500))
                .fillMaxHeight(0.2f)
                .background(Color.White),
            shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16)),
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
                            .size( dimensionResource(id = R.dimen.dim_34))
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
                        lineHeight = 19.5.sp,
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                        fontFamily = Montserrat
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.dim_10), horizontal = dimensionResource(id = R.dimen.dim_30)),
                ) {
                    Text(
                        text = text,
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                        lineHeight = 17.07.sp,
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
                height =dimensionResource(id = R.dimen.dim_0),
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
fun GridItem2(navController: NavController, dataItem: VideoModel, userName: String) {
    Card(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dim_272))
            .width(dimensionResource(id = R.dimen.dim_178))
            .padding(dimensionResource(id = R.dimen.dim_10)), shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = rememberAsyncImagePainter(dataItem.thumbnailLink),
                contentDescription = null,
                Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("DELETE_RECIPE/${dataItem.videoId}") },
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

