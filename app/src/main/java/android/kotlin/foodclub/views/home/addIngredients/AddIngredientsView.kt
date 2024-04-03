package android.kotlin.foodclub.views.home.addIngredients

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultSearchBarColors
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.EditIngredientBottomModal
import android.kotlin.foodclub.utils.composables.products.ProductAction
import android.kotlin.foodclub.utils.composables.products.ProductState
import android.kotlin.foodclub.utils.composables.products.ProductsEvents
import android.kotlin.foodclub.utils.composables.products.ProductsList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientsView(
    state: ProductState,
    searchResult: LazyPagingItems<Ingredient>,
    events: ProductsEvents,
    backHandler: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                IconButton(onClick = backHandler) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_left),
                        contentDescription = stringResource(id = R.string.go_back),
                        Modifier.size(dimensionResource(id = R.dimen.dim_20))
                    )
                }

                Text(
                    text = stringResource(id = R.string.add_ingredients),
                    fontSize = dimensionResource(
                        id = R.dimen.fon_20
                    ).value.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight(600)
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
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

                ProductsList(
                    events = events,
                    state = state,
                    productsList = searchResult,
                    searchBarPlaceholder = stringResource(id = R.string.search_ingredients),
                    searchBarColors = defaultSearchBarColors()
                )
            }
        }
    )
}