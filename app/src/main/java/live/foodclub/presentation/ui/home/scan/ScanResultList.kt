package live.foodclub.presentation.ui.home.scan

import live.foodclub.R
import live.foodclub.config.ui.BottomBarScreenObject
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.containerColor
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.domain.models.products.Ingredient
import live.foodclub.utils.composables.customComponents.CustomDatePicker
import live.foodclub.utils.composables.EditIngredientQuantityPicker
import live.foodclub.utils.composables.IngredientsListTitleSection
import live.foodclub.utils.composables.LoadingProgressBar
import live.foodclub.utils.composables.products.ProductsEvents
import live.foodclub.utils.composables.products.itemExpirationDate
import live.foodclub.utils.composables.products.itemQuantity
import live.foodclub.presentation.ui.home.discover.DiscoverEvents
import live.foodclub.presentation.ui.home.discover.DiscoverState
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultView(
    navController: NavController,
    events: ProductsEvents,
    discoverEvents: DiscoverEvents,
    state: DiscoverState
)
{

    val modifier = Modifier

    var isShowEditScreen by remember { mutableStateOf(false) }
    var topBarTitleText by remember { mutableStateOf("") }

    val searchText = state.ingredientSearchText

    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    var loading by rememberSaveable { mutableStateOf(false) }
    BackHandler {
        navController.popBackStack()

    }
    Box(modifier =Modifier.fillMaxSize() ) {
        if(loading) {
            LoadingProgressBar(
                text= stringResource(id = R.string.uploading),
                route = BottomBarScreenObject.Play.route
                , navController = navController
            )
        }else{
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.scan_results_list),
                                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = dimensionResource(id = R.dimen.fon_48).value.sp,
                                fontFamily = Montserrat
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ){
                                Icon(
                                    painterResource(id = R.drawable.back_arrow),
                                    contentDescription = "",
                                )
                            }
                        },
                        actions = {
                            TextButton(
                                onClick = {
                                    loading=!loading
                                    discoverEvents.addScanListToUserIngredients(state.scanResultItemList)

                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.next),
                                    color = foodClubGreen,
                                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                                    fontWeight = FontWeight(600),
                                    fontFamily = Montserrat
                                )
                            }
                        },
                    )
                },
                content = {
                    Column(
                        modifier = modifier
                            .padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                            .fillMaxSize()
                            .background(Color.White),
                        verticalArrangement = Arrangement.Top
                    ) {


                        if (isShowEditScreen){
                            topBarTitleText = stringResource(id = R.string.edit_item)
                            EditIngredientView(
                                ingredient = state.ingredientToEdit!!,
                                onEditIngredient = { ingr ->
                                    events.updateIngredient(ingr)
                                },
                                onDeleteIngredient = {ingr ->
                                    //TODO add delete ingredient functionality here if needed
                                }
                            )
                        }else{
                            topBarTitleText = stringResource(id = R.string.all_my_ingredients)
                            SearchResultIngredients(
                                modifier = Modifier,
                                searchTextValue = searchText,
                                onSearch = { input->
                                    events.searchWithinAddedIngredients(input)
                                }
                            )
                            Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_15)))

                            ScanResultList(
                                modifier = modifier,
                                productsList = state.scanResultItemList,
                                onAddDateClicked = { ingredient->
                                    events.updateIngredient(ingredient)
                                    isDatePickerVisible = true
                                                   },
                                onEditClicked = {
                                        item->
                                    events.updateIngredient(item)
                                    isShowEditScreen = false
                                },
                                view = stringResource(id = R.string.digitalPantry)
                            )

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
                                            if (date != null){
                                                selectedDate = date
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }

}


@Composable
fun ScanResultList(
    modifier: Modifier,
    productsList: List<Ingredient>,
    onAddDateClicked: (Ingredient) -> Unit,
    onEditClicked: (Ingredient) -> Unit,
    view: String
) {
    Surface(
        shadowElevation = dimensionResource(id = R.dimen.dim_8),
        shape = RoundedCornerShape(topStart = dimensionResource(id = R.dimen.dim_18), topEnd = dimensionResource(id = R.dimen.dim_18))
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
                    color = Color.White
                )
        ) {
            IngredientsListTitleSection(modifier, view)
            SwipeableItemsLazyColumn(
                modifier = modifier,
                height = Int.MAX_VALUE,
                productsList = productsList,
                onEditClicked = onEditClicked,
                onAddDateClicked = {
                    //TODO impl add-update data functionality if needed
                },
                onDeleteIngredient = {
                    //TODO impl delete functionality if needed
                }
            )
        }
    }
}

@Composable
fun SearchResultIngredients(
    modifier: Modifier,
    searchTextValue: String,
    onSearch: (String) -> Unit
) {

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dim_10))
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
                modifier = modifier.padding(top =dimensionResource(id = R.dimen.dim_3)),
                text = stringResource(id = R.string.search_my_ingredients),
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        },
        leadingIcon = {
            IconButton(
                onClick = {}
            ){
                Icon(
                    painterResource(id = R.drawable.search_icon_ingredients),
                    contentDescription = "",
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableItemsLazyColumn(
    modifier: Modifier,
    height: Int,
    productsList: List<Ingredient>,
    onEditClicked: (Ingredient) -> Unit,
    onAddDateClicked: (Ingredient) -> Unit,
    onDeleteIngredient: (Ingredient) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.dim_15),
                end = dimensionResource(id = R.dimen.dim_15)
            )
            .background(Color.White)
            .height(height.dp)
    ) {
        itemsIndexed(productsList) { index, ingredient ->
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
                    onDeleteIngredient(ingredient)
                    dismissState.reset()
                }
            } else {
                LaunchedEffect(key1 = true) {
                    dismissState.reset()
                }
            }

            Spacer(Modifier.height(dimensionResource(id = R.dimen.dim_8)))
            if (index != 0) {
                Divider(thickness =dimensionResource(id = R.dimen.dim_1), modifier = modifier.alpha(0.5f), color = Color.LightGray)
            }
            Spacer(Modifier.height(dimensionResource(id = R.dimen.dim_8)))


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
                    SingleIngredientItem(
                        modifier,
                        ingredient,
                        onAddDateClicked,
                        onEditClicked
                    )
                }
            )
        }
    }
}

@Composable
fun SingleIngredientItem(
    modifier: Modifier,
    item: Ingredient,
    onAddDateClicked: (Ingredient) -> Unit,
    onEditClicked: (Ingredient) -> Unit
) {
    val title = item.product.label.split(",").first().trim()
    val unit = stringResource(id = R.string.gram_unit) // TODO make dynamic
    val quantity = itemQuantity(item = item)
    val expirationDate = itemExpirationDate(item = item)

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
                    model = item.product.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(dimensionResource(id = R.dimen.dim_48))
                        .width(dimensionResource(id = R.dimen.dim_48))
                        .clip(CircleShape)
                )
                Text(
                    modifier = modifier.padding(start =dimensionResource(id = R.dimen.dim_6)),
                    text = title,
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
                        .padding(start = dimensionResource(id = R.dimen.dim_6))
                        .clickable {
                            onEditClicked(item)
                        },
                    text = quantity,
                    fontWeight = FontWeight(500),
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    lineHeight = 19.5.sp,
                    fontFamily = Montserrat,
                    color = Color.Gray
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
                        .clickable {
                            onAddDateClicked(item)
                        },
                    text = expirationDate,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Start,
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    lineHeight = 19.5.sp,
                    fontFamily = Montserrat,
                    color = Color.Gray
                )
                Box(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_24))
                        .clip(CircleShape)
                        .background(foodClubGreen)
                        .clickable {
                            onEditClicked(item)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun EditIngredientView(
    ingredient: Ingredient,
    onEditIngredient: (Ingredient) -> Unit,
    onDeleteIngredient: (Ingredient) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ingredient.product.image,
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
        val pickerValues = remember {
            mutableStateOf((1..10).map {
                Pair(
                    it,
                    (it * 100).toString() + ingredient.unit.short
                )
            })
        }

        val quantity = pickerValues.value.map { it.first }
        val grammage = pickerValues.value.map { it.second }
        val types = stringArrayResource(id = R.array.quantity_list).toList()

        Row(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_20), bottom = dimensionResource(id = R.dimen.dim_20), start = dimensionResource(id = R.dimen.dim_20))) {
            Text(
                text = stringResource(id = R.string.quantity_colon),
                fontSize = dimensionResource(id = R.dimen.fon_22).value.sp,
                fontWeight = FontWeight(600),
                color = Color.Black,
                fontFamily = Montserrat
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.dim_17),
                        end = dimensionResource(id = R.dimen.dim_17),
                        bottom = dimensionResource(id = R.dimen.dim_20)
                    ),
            ) {
                EditIngredientQuantityPicker(
                    ingredient = ingredient,
                    quantity = quantity,
                    grammage = grammage,
                    types = types,
                    onEditIngredient = {
                        onEditIngredient(it)
                    }
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
                OutlinedButton(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            dimensionResource(id = R.dimen.dim_2),
                            colorResource(id = R.color.scan_results_list_button_color),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.White
                    ), contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
                    onClick = {
                        onDeleteIngredient(ingredient)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.remove),
                        color = colorResource(id = R.color.scan_results_list_button_color),
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 24.38.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_15)))

                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            dimensionResource(id = R.dimen.dim_1),
                            colorResource(id = R.color.scan_results_list_button_color),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.scan_results_list_button_color),
                        contentColor = Color.White
                    ), contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
                    onClick = {
                        // TODO impl save changes
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        color = Color.White,
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 24.38.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
