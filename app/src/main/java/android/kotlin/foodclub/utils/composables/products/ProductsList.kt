package android.kotlin.foodclub.utils.composables.products

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultSearchBarColors
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.EditIngredientBottomModal
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListModalSheet(
    events: ProductsEvents,
    state: ProductState,
    productsList: LazyPagingItems<Ingredient>,
    onDismiss: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        ProductsList(
            events = events,
            state = state,
            productsList = productsList,
            searchBarPlaceholder = stringResource(id = R.string.search_ingredients),
            searchBarColors = defaultSearchBarColors()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsList(
    events: ProductsEvents,
    state: ProductState,
    productsList: LazyPagingItems<Ingredient>,
    searchBarPlaceholder: String,
    searchBarColors: TextFieldColors,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()

    when (state.currentAction) {
        ProductAction.EDIT_QUANTITY -> EditIngredientBottomModal(
            ingredient = state.editedIngredient,
            onDismissRequest = {
                events.dismissAction()
            },
            onEdit = { item ->
                events.updateIngredient(item)
            }
        )

        ProductAction.CHANGE_EXPIRY_DATE -> Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            CustomDatePicker(
                modifier = Modifier.shadow(dimensionResource(id = R.dimen.dim_5)),
                datePickerState = datePickerState,
                onDismiss = {
                    datePickerState.setSelection(null)
                    events.dismissAction()
                },
                onSave = { date ->
                    if (date != null) {
                        state.editedIngredient.expirationDate = date
                        events.updateIngredient(state.editedIngredient)
                    }
                }
            )
        }
        else -> {}
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
    ) {
        ProductSearchBar(
            onSearch = { events.search(it) },
            textFieldColors = searchBarColors,
            placeholder = searchBarPlaceholder
        )
        ProductsListTitleSection(modifier = modifier, includeExpiryDate = state.allowExpiryDate)
        ProductsListContent(
            events = events,
            productsList = productsList,
            userIngredientsList = state.addedProducts,
            includeExpiryDate = state.allowExpiryDate
        )
    }
}

@Composable
fun ProductsListTitleSection(modifier: Modifier, includeExpiryDate: Boolean) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.dim_20),
                top = dimensionResource(id = R.dimen.dim_15),
                bottom = dimensionResource(id = R.dimen.dim_15),
                end = dimensionResource(id = R.dimen.dim_44)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.name),
            fontWeight = FontWeight(500),
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontFamily = Montserrat,
            color = Color.Gray,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            modifier = modifier.weight(1f),
            text = stringResource(id = R.string.quantity),
            fontWeight = FontWeight(500),
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
        if (includeExpiryDate) {
            Text(
                text = stringResource(id = R.string.expiry_date),
                fontWeight = FontWeight(500),
                fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
                fontFamily = Montserrat,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProductsListContent(
    events: ProductsEvents,
    productsList: LazyPagingItems<Ingredient>,
    userIngredientsList: List<Ingredient>,
    includeExpiryDate: Boolean
) {
    var height by remember {
        mutableStateOf(0.dp)
    }
    height = (productsList.itemCount * dimensionResource(id = R.dimen.dim_65).value).dp

    LazyColumn(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.dim_15),
                end = dimensionResource(id = R.dimen.dim_15)
            )
            .background(Color.White)
            .height(height),
        content = {
            items(
                count = productsList.itemCount,
            ) { index ->
                val item = productsList[index]
                if (item != null) {
                    IngredientItem(
                        modifier = Modifier,
                        item = item,
                        userIngredientsList = userIngredientsList,
                        onEditQuantityClicked = {
                            events.selectAction(item, ProductAction.EDIT_QUANTITY)
                        },
                        onDateClicked = {
                            events.selectAction(item, ProductAction.CHANGE_EXPIRY_DATE)
                        },
                        onAddItemClicked = {
                            events.updateIngredient(item)
                        },
                        onDeleteIngredient = {
                            events.deleteIngredient(item)
                        },
                        includeExpiryDate = includeExpiryDate
                    )
                }

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
fun IngredientItem(
    modifier: Modifier,
    item: Ingredient,
    onEditQuantityClicked: (Ingredient) -> Unit,
    onDateClicked: (Ingredient) -> Unit,
    onAddItemClicked: ((Ingredient) -> Unit)? = null,
    onDeleteIngredient: (Ingredient) -> Unit,
    userIngredientsList: List<Ingredient>,
    includeExpiryDate: Boolean
) {
    val quantity: String
    val expirationDate: String
    val itemSearched = userIngredientsList.find { item.product.foodId == it.product.foodId }
    if (itemSearched != null) {
        quantity = itemQuantity(itemSearched)
        expirationDate = itemExpirationDate(itemSearched)
    } else {
        quantity = itemQuantity(item)
        expirationDate = itemExpirationDate(item)
    }

    var isItemAdded = userIngredientsList.any { item.product.foodId == it.product.foodId }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Column(
            modifier = modifier.weight(1.2f)
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
                    modifier = modifier.padding(start = dimensionResource(id = R.dimen.dim_6)),
                    text = item.product.label,
                    fontWeight = FontWeight(500),
                    lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    color = Color.Black,
                    maxLines = integerResource(id = R.integer.int_2),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Box(modifier = modifier.weight(1f)) {
            Text(
                modifier = modifier
//                    .padding(start = dimensionResource(id = R.dimen.dim_6))
                    .clickable {
                        onEditQuantityClicked(item)
                    },
                text = quantity,
                fontWeight = FontWeight(500),
                fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontFamily = Montserrat,
                color = Color.Gray,
                style = quantityTextStyle(quantity),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
//            ) {
//
//            }
        }

        if(includeExpiryDate) {
            Box(modifier = modifier.weight(1f)) {
                Text(
                    modifier = modifier
//                        .padding(start = dimensionResource(id = R.dimen.dim_20))
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
                    style = expirationDateTextStyle(expirationDate),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }

        if(onAddItemClicked != null) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_24))
                    .clip(CircleShape)
                    .background(if (isItemAdded) Color.Red else foodClubGreen)
                    .clickable {
                        if (isItemAdded) {
                            onDeleteIngredient(item)
                        } else {
                            onAddItemClicked(item)
                        }
                        isItemAdded = !isItemAdded
                    },
                contentAlignment = Alignment.Center
            ) {
                val rotationAngle by animateFloatAsState(
                    targetValue = if (isItemAdded) 45f else 0f,
                    label = ""
                )

                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        } else {
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.dim_24)))
        }
    }
}

@Composable
fun itemQuantity(item: Ingredient): String {
    return if (item.quantity != 0) item.quantity.toString() + item.unit.short else stringResource(id = R.string.edit)
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

