package android.kotlin.foodclub.views.home.discover

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.SwipeToDismissContainer
import android.kotlin.foodclub.utils.composables.products.IngredientItem
import android.kotlin.foodclub.utils.composables.products.ProductAction
import android.kotlin.foodclub.utils.composables.products.ProductState
import android.kotlin.foodclub.utils.composables.products.ProductsEvents
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

@Composable
fun KitchenIngredients(
    events: ProductsEvents,
    state: ProductState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
    ) {
        IngredientsListTitleSection(modifier)
        IngredientsListColumn(events = events, productState = state)
    }
}

@Composable
fun IngredientsListTitleSection(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.dim_20),
                top = dimensionResource(id = R.dimen.dim_15),
                bottom = dimensionResource(id = R.dimen.dim_15)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.name),
            fontWeight = FontWeight(500),
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
        Text(
            modifier = modifier.padding(start = dimensionResource(id = R.dimen.dim_15)),
            text = stringResource(id = R.string.quantity),
            fontWeight = FontWeight(500),
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
        Text(
            text = stringResource(id = R.string.expiry_date),
            fontWeight = FontWeight(500),
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))
    }
}

@Composable
fun IngredientsListColumn(
    events: ProductsEvents,
    productState: ProductState
) {
    var height by remember {
        mutableStateOf(0.dp)
    }
    height = (min(productState.addedProducts.size, 5) * dimensionResource(id = R.dimen.dim_65).value).dp

    LazyColumn(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.dim_15),
                end = dimensionResource(id = R.dimen.dim_15)
            )
            .background(Color.White)
            .height(height),
        content = {
            itemsIndexed(productState.addedProducts) { _, item ->
                SwipeToDismissContainer(
                    onDismiss = { events.deleteIngredient(item) }
                ) { modifier ->
                    IngredientItem(
                        modifier = modifier,
                        item = item,
                        userIngredientsList = productState.addedProducts,
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