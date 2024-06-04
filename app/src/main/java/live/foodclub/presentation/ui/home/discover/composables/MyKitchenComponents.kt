package live.foodclub.presentation.ui.home.discover.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import live.foodclub.R
import live.foodclub.config.ui.defaultSearchBarColors
import live.foodclub.utils.composables.SwipeToDismissContainer
import live.foodclub.utils.composables.customComponents.CustomDatePicker
import live.foodclub.utils.composables.products.EditIngredientBottomModal
import live.foodclub.utils.composables.products.IngredientItem
import live.foodclub.utils.composables.products.ProductAction
import live.foodclub.utils.composables.products.ProductSearchBar
import live.foodclub.utils.composables.products.ProductState
import live.foodclub.utils.composables.products.ProductsEvents
import live.foodclub.utils.composables.products.ProductsListTitleSection
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitchenIngredients(
    events: ProductsEvents,
    state: ProductState,
    lazyColumnState: LazyListState,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState()

    val addedItems = remember { mutableStateListOf<String>() }
    LaunchedEffect(state.addedProducts) {
        addedItems.clear()
        addedItems.addAll(state.addedProducts.map { it.product.foodId })
    }

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
            onSearch = { events.searchWithinAddedIngredients(it) },
            textFieldColors = defaultSearchBarColors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            placeholder = stringResource(id = R.string.search_from_my_basket),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(
                        width = dimensionResource(id = R.dimen.dim_1),
                        color = colorResource(id = R.color.gray).copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
                ),
            enableCamera = false,
            enableMike = false
        )
        ProductsListTitleSection(modifier = modifier, includeExpiryDate = state.allowExpiryDate)
        IngredientsListColumn(lazyColumnState = lazyColumnState, events = events, productState = state, addedItems = addedItems)
    }
}

@Composable
fun IngredientsListColumn(
    events: ProductsEvents,
    productState: ProductState,
    addedItems: MutableList<String>,
    lazyColumnState: LazyListState
) {
    var height by remember {
        mutableStateOf(0.dp)
    }
    height = (min(productState.filteredAddedProducts.size, 5) * dimensionResource(id = R.dimen.dim_65).value).dp

    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.dim_15),
                end = dimensionResource(id = R.dimen.dim_15)
            )
            .background(Color.White)
            .height(height),
        content = {
            itemsIndexed(
                items = productState.filteredAddedProducts,
                key = { _, item -> item.product.foodId }
            ) { _, item ->
                val updatedItem = productState.addedProducts.find { it.product.foodId == item.product.foodId } ?: item

                SwipeToDismissContainer(
                    onDismiss = { events.deleteIngredient(item) }
                ) { modifier ->
                    IngredientItem(
                        modifier = modifier,
                        item = item,
                        isItemAdded = addedItems.contains(updatedItem.product.foodId),
                        userIngredientsList = productState.filteredAddedProducts,
                        onEditQuantityClicked = {
                            events.selectAction(item, ProductAction.EDIT_QUANTITY)
                        },
                        onDateClicked = {
                            events.selectAction(item, ProductAction.CHANGE_EXPIRY_DATE)
                        },
                        onDeleteIngredient = {
                            events.deleteIngredient(item)
                        },
                        includeExpiryDate = productState.allowExpiryDate
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