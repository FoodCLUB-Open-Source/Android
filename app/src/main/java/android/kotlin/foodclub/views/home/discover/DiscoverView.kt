package android.kotlin.foodclub.views.home.discover

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.navigation.HomeOtherRoutes
import android.kotlin.foodclub.utils.composables.ActionType
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.EditIngredientBottomModal
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import android.kotlin.foodclub.utils.composables.IngredientsList
import android.kotlin.foodclub.utils.composables.shimmerBrush
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import android.kotlin.foodclub.viewModels.home.discover.DiscoverEvents
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
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
//    var isDialogOpen by remember { mutableStateOf(false) }
//    var alphaValue by remember { mutableFloatStateOf(1f) }

//    alphaValue = if (isDialogOpen) {
//        0.5f
//    } else {
//        1f
//    }

    var tabIndex by remember { mutableIntStateOf(0) }
    val mainTabItemsList = stringArrayResource(id = R.array.discover_tabs)

    var gridHeight by remember { mutableStateOf(0.dp) }
    val recommandationVideosCount by remember { mutableIntStateOf(8) }
    gridHeight = if (recommandationVideosCount == 1) {
        (recommandationVideosCount * dimensionResource(id = R.dimen.dim_272).value).dp
    } else {
        ((recommandationVideosCount / 2) * dimensionResource(id = R.dimen.dim_272).value).dp
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(Color.White),
//            .alpha(alphaValue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainSearchBar(
            navController = navController,
            basketCache = state.myBasketCache!!
        )
        MainTabRow(
            isInternetConnected,
            brush,
            tabsList = mainTabItemsList,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            tabIndex = it
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
//        if (isDialogOpen) {
//            AddIngredientDialog(
//                stringResource(R.string.added),
//                stringResource(R.string.successfully_added_first),
//                stringResource(R.string.successfully_added_second),
//                state.ingredientToEdit!!.type
//            )
//            LaunchedEffect(key1 = true) {
//                delay(3000)
//                isDialogOpen = false
//            }
//        }

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

        if (tabIndex == 0) {
            homePosts = state.postList

            if (isInternetConnected) {
                MyIngredientsSearchBar(
                    navController = navController,
                    searchTextValue = searchText,
                    onSearch = { input ->
                        searchText = input
                        events.onSearchIngredientsList(input)
                    },
                    enableCamera = false,
                    enableMike = false,
                    actionType = ActionType.DISCOVER_VIEW
                )

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
                    },
                    actionType = ActionType.DISCOVER_VIEW
                )


                if (state.userIngredients.isEmpty()){
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
                        navController.navigate("ADD_INGREDIENTS")
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
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
            if (state.userIngredients.isNotEmpty()){
                RecommandationSection(
                    gridHeight,
                    recommandationVideosCount,
                    navController = navController,
                    isShowPost = {
                        isShowPost = !isShowPost
                        postId = it
                    }
                )
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

@Composable
fun RecommandationSection(
    gridHeight: Dp,
    recommandationVideosCount: Int,
    navController: NavController,
    isShowPost: (Long) -> Unit
){
    Card(
        modifier = Modifier ,
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.dim_30)
        ),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.dim_1),
            color = colorResource(id = R.color.discover_view_recommendations_border_color)
        ),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = Color.White
        )
    ){
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
        RecommendationVideos(
            gridHeight = gridHeight,
            recommandationVideosCount = recommandationVideosCount,
            navController = navController,
            dataItem = null,
            userName = null,
            isShowPost = {
                isShowPost(it)
            }
        )
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
fun MyIngredientsSearchBar(
    navController: NavController,
    searchTextValue: String,
    onSearch: (String) -> Unit,
    enableCamera: Boolean = true,
    enableMike: Boolean = true,
    actionType: ActionType
) {
    val placeholderText = if (actionType == ActionType.DISCOVER_VIEW){
        stringResource(id = R.string.search_from_my_basket)
    }else{
        stringResource(id = R.string.search_ingredients)
    }
    val textFieldColors = if (actionType == ActionType.DISCOVER_VIEW){
        TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    }else{
        TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dim_20),
                end = dimensionResource(id = R.dimen.dim_20),
                start = dimensionResource(id = R.dimen.dim_20),
                bottom = dimensionResource(id = R.dimen.dim_15)
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
//                .fillMaxWidth(if (enableCamera && enableMike) 0.68f else 1.0f)
                .fillMaxWidth()
                .let { modifier ->
                    if (actionType == ActionType.ADD_INGREDIENTS_VIEW) {
                        modifier.clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                    } else {
                        modifier.border(
                            border = BorderStroke(
                                width = dimensionResource(id = R.dimen.dim_1),
                                color = colorResource(id = R.color.gray).copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
                        )
                    }
                },
            colors = textFieldColors,
            value = searchTextValue,
            onValueChange = {
                onSearch(it)
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholderText,
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontWeight = FontWeight(400),
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

//        if (enableCamera) {
//            Button(
//                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
//                modifier = Modifier
//                    .height(dimensionResource(id = R.dimen.dim_56))
//                    .width(dimensionResource(id = R.dimen.dim_56)),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = foodClubGreen,
//                ),
//                contentPadding = PaddingValues(),
//                onClick = {
//                    navController.navigate("ScanView_route")
//                }
//            ) {
//                Icon(painterResource(id = R.drawable.camera_icon), contentDescription = "")
//            }
//        }
//
//        if (enableMike) {
//            Button(
//                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
//                modifier = Modifier
//                    .height(dimensionResource(id = R.dimen.dim_56))
//                    .width(dimensionResource(id = R.dimen.dim_56)),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = foodClubGreen,
//                ),
//                contentPadding = PaddingValues(),
//                onClick = {
//
//                    // TODO impl microphone
//                }
//            ) {
//                Icon(painterResource(id = R.drawable.mic_icon), contentDescription = "")
//            }
//        }
    }
}