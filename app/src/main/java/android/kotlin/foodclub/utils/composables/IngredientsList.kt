package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.viewModels.home.discover.DiscoverEvents
import android.kotlin.foodclub.views.home.myDigitalPantry.TitlesSection
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import coil.compose.AsyncImage
import kotlin.math.min


@Composable
fun IngredientsList(
    modifier: Modifier,
    events: DiscoverEvents,
    productsList: List<Ingredient>,
    onEditQuantityClicked: (Ingredient) -> Unit,
    onDateClicked: (Ingredient) -> Unit,
    onIngredientAdd: (Ingredient) -> Unit,
    onDeleteIngredient: (Ingredient) -> Unit,
    userIngredientsList: List<Ingredient>,
    actionType: ActionType
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
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
            onDeleteIngredient = { onDeleteIngredient(it) },
            actionType = actionType
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
    userIngredientsList: List<Ingredient>,
    actionType: ActionType
) {
    var height by remember {
        mutableStateOf(0.dp)
    }

    height = when (actionType) {
        ActionType.DISCOVER_VIEW -> (min(productsList.size, 5) * dimensionResource(id = R.dimen.dim_65).value).dp
        ActionType.ADD_INGREDIENTS_VIEW -> (productsList.size * dimensionResource(id = R.dimen.dim_65).value).dp
    }
    LazyColumn(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.dim_15),
                end = dimensionResource(id = R.dimen.dim_15)
            )
            .background(Color.White)
            .height(height),
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
                            item = item, //Item is different when editing need to make sure to check based on ID
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
    val unit = stringResource(id = R.string.gram_unit)
    val quantity = itemQuantity(item, unit)
    val expirationDate = itemExpirationDate(item)
    val isItemAdded = userIngredientsList.filter { item.id == it.id }.size == 1

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
                if (true)//isItemAdded)
                {
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
                        style = quantityTextStyle(quantity),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
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
                if (true)//isItemAdded)
                {
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
                        style = expirationDateTextStyle(expirationDate),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                //Item may have a different ID when it is being edited so it isn't in the list will have to check that
                if (!isItemAdded) {
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

enum class ActionType {
    DISCOVER_VIEW,
    ADD_INGREDIENTS_VIEW
}
