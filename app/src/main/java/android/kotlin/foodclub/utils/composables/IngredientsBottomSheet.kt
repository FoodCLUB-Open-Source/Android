package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.enums.DrawerContentState
import android.kotlin.foodclub.utils.helpers.ValueParser
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Ingredients Bottom Sheet
 *
 * Ingredients bottom sheet is a pop up containing list of ingredients which can be used to execute
 * function on chosen by user [Ingredient]. For example user can select 340 grams of Tomato and add
 * it to their cart.
 *
 * @param onDismiss Executes when the user clicks outside of the bottom sheet or when user clicks
 * back button
 * @param productsDataFlow [ProductsData] StateFlow which contains list of the products currently
 * loaded using the API
 * @param loadMoreObjects Optional function which executes loading further list of products based on
 * text put in "search" field and passes callback function which should be called when the list is
 * loaded
 * @param onListUpdate Optional function which executes when text in "search" field is changed
 * @param onSave Optional function which executes when user clicks "On Save" button and provides
 * [Ingredient] object
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun IngredientsBottomSheet(
    onDismiss: () -> Unit,
    productsDataFlow: StateFlow<ProductsData>,
    loadMoreObjects: (searchText: String, onLoadCompleted: () -> Unit)
    -> Unit = { _, _ -> },
    onListUpdate: (searchText: String) -> Unit = {},
    onSave: (ingredient: Ingredient) -> Unit = {}
) {

    var editedIngredient by remember { mutableStateOf<Ingredient?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_150)

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val contentState = remember { mutableStateOf(DrawerContentState.IngredientListContent) }
    var savedSearchText by remember { mutableStateOf("") }


    ModalBottomSheet(
        containerColor = Color.Black,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        AnimatedContent(
            targetState = contentState, transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState.value == DrawerContentState.IngredientListContent) {
                                keyframes {
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }

            }, label = ""
        ) { contentState ->
            when (contentState.value) {
                DrawerContentState.IngredientListContent -> {
                    IngredientListView(
                        screenHeight = screenHeight,
                        savedSearchText = savedSearchText,
                        productsDataFlow = productsDataFlow,
                        onDismiss = {
                            coroutineScope.launch {
                                bottomSheetState.hide()
                                onDismiss()
                            }
                        },
                        loadMoreObjects = loadMoreObjects,
                        onListUpdate = onListUpdate,
                        onIngredientSelect = { ingredient, searchText ->
                            editedIngredient = ingredient
                            savedSearchText = searchText
                            contentState.value = DrawerContentState.IngredientAmountSelection
                        }
                    )
                }

                DrawerContentState.IngredientAmountSelection -> {
                    if (editedIngredient != null) {
                        IngredientSelectedView(
                            screenHeight = screenHeight,
                            selectedIngredient = editedIngredient!!,
                            onDismiss = {
                                contentState.value = DrawerContentState.IngredientListContent
                            },
                            onSave = {
                                onSave(it)
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                    onDismiss()
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun IngredientSelectedView(
    screenHeight: Dp,
    selectedIngredient: Ingredient,
    onDismiss: () -> Unit, onSave: (ingredient: Ingredient) -> Unit
) {
    val valuesPickerState = rememberPickerState()
    val pickerValues = remember {
        mutableStateOf((1..99).map {
            (it * 10).toString() +
                    ValueParser.quantityUnitToString(selectedIngredient.unit)
        })
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.dim_17), end = dimensionResource(id = R.dimen.dim_17)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dim_50))
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = stringResource(id = R.string.left_arrow),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_24))
                        .align(Alignment.CenterStart)
                        .clickable(onClick = { onDismiss() })
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_8)))
                Text(
                    text = stringResource(id = R.string.add_items),
                    color = Color.White,
                    fontFamily = Montserrat,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.dim_30), bottom =  dimensionResource(id = R.dimen.dim_40))
            ) {
                AsyncImage(
                    model = selectedIngredient.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_130))
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_35)))
                Picker(
                    state = valuesPickerState,
                    items = pickerValues.value,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.3f),
                    textModifier = Modifier.padding(dimensionResource(id = R.dimen.dim_8)),
                    textStyle = TextStyle(
                        fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                        color = Color(android.graphics.Color.parseColor("#545454"))
                    )
                )
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            dimensionResource(id = R.dimen.dim_1),
                            Color(126, 198, 11, 255),
                            shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                        )
                        .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(126, 198, 11, 255),
                        contentColor = Color.White
                    ), contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
                    onClick = {
                        onSave(
                            Ingredient(
                                selectedIngredient.id,
                                selectedIngredient.type,
                                ValueParser.quantityStringToInt(
                                    valuesPickerState.selectedItem,
                                    selectedIngredient.unit
                                ),
                                selectedIngredient.unit,
                                selectedIngredient.imageUrl
                            )
                        )
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        color = Color.White,
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IngredientListView(
    screenHeight: Dp,
    savedSearchText: String,
    productsDataFlow: StateFlow<ProductsData>,
    onDismiss: () -> Unit,
    onListUpdate: (searchText: String) -> Unit,
    loadMoreObjects: (searchText: String, onLoadCompleted: () -> Unit) -> Unit,
    onIngredientSelect: (ingredient: Ingredient, searchText: String) -> Unit
) {
    val productsData = productsDataFlow.collectAsState()
    var searchText by remember { mutableStateOf(savedSearchText) }
    val showingList = productsData.value
    val lazyListState = rememberLazyListState()


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.dim_17), end = dimensionResource(id = R.dimen.dim_17)),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.clickable { onDismiss() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = "Close Sheet",
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dim_24))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_8)))
                Text(
                    text = "Add items",
                    color = Color.White,
                    fontFamily = Montserrat
                )
            }
        }

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_30))
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.dim_20))
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text(text = "Search here", fontSize = dimensionResource(id = R.dimen.fon_15).value.sp) },
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { /* Handle onDone event if needed */ }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(dimensionResource(id = R.dimen.dim_51))
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_20)))
                    Text(
                        text = "Clear",
                        color = Color(0x00545454),
                        modifier = Modifier.clickable { searchText = "" }
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_20)))
                }
            }
            items(
                items = productsDataFlow.value.productsList,
                key = { it.id }
            ) {
                if (showingList.productsList.contains(it)) {
                    IngredientComposable(
                        ingredient = it,
                        onClick = { ingredient -> onIngredientSelect(ingredient, searchText) }
                    )
                }

            }
        }
    }

    var listLoading by remember { mutableStateOf(false) }
    val loadMore by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > productsDataFlow.value.productsList.size - 10
        }
    }

    LaunchedEffect(searchText) {
        delay(1500)
        if (searchText.length > 3) {
            onListUpdate(searchText)
        }
    }

    LaunchedEffect(loadMore) {
        if (!listLoading) {
            listLoading = true
            loadMoreObjects(searchText) { listLoading = false }
        }
    }
}

@Composable
private fun IngredientComposable(
    ingredient: Ingredient,
    onClick: (Ingredient) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dim_70))
            .padding(start = dimensionResource(id = R.dimen.dim_20), top = dimensionResource(id = R.dimen.dim_20))
            .clickable(onClick = { onClick(ingredient) })
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = ingredient.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_40))
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width( dimensionResource(id = R.dimen.dim_15)))
            Text(
                text = ingredient.type,
                color = Color.White,
                fontFamily = Montserrat
            )
        }
    }
}