package live.foodclub.presentation.ui.home.myBasket

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.containerColor
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.domain.models.products.Ingredient
import live.foodclub.utils.composables.products.AddIngredientsView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage

@Composable
fun MyBasketView(
    events: MyBasketEvents,
    state: MyBasketState,
    searchResult: LazyPagingItems<Ingredient>
) {
    var showSheet by remember { mutableStateOf(false) }
    var deleteSelected by remember { mutableStateOf(false) }

    LaunchedEffect(deleteSelected) {
        if (deleteSelected) {
            events.deleteSelectedIngredients()
            deleteSelected = false
        }
    }

    BackHandler(enabled = showSheet) {
        showSheet = false
    }

    if (showSheet) {
        AddIngredientsView(
            events = events,
            state = state.productState,
            productsList = searchResult,
            onDismiss = { showSheet = !showSheet }
        )
    } else {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(top = dimensionResource(id = R.dimen.dim_60)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.dim_20),
                            end = dimensionResource(id = R.dimen.dim_20)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // this below commented code block will be used in the future
//                IconButton(
//                    onClick = { navController.navigateUp() },
//                    modifier = Modifier
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.back_icon),
//                        contentDescription = "Back",
//                        tint = Color.Black
//                    )
//                }
                    Text(
                        text = stringResource(id = R.string.shopping_list),
                        fontSize = dimensionResource(id = R.dimen.fon_28).value.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight(integerResource(id = R.integer.int_600)),
                        color = Color.Black,
                        modifier = Modifier.weight(1f),
                        lineHeight = dimensionResource(id = R.dimen.dim_48).value.sp
                    )
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(
                                dimensionResource(id = R.dimen.dim_1),
                                containerColor,
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_22))
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_22)))
                            .width(dimensionResource(id = R.dimen.dim_50))
                            .height(dimensionResource(id = R.dimen.dim_50)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_5)),
                        onClick = { deleteSelected = true }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.delete_bin_5_line__2_),
                            contentDescription = stringResource(id = R.string.go_back),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.dim_20))
                                .height(dimensionResource(id = R.dimen.dim_20))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = dimensionResource(id = R.dimen.dim_20),
                            start = dimensionResource(id = R.dimen.dim_20),
                            bottom = dimensionResource(id = R.dimen.dim_10)
                        )
                        .height(dimensionResource(id = R.dimen.dim_80)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(
                                width = dimensionResource(id = R.dimen.dim_1),
                                color = colorResource(id = R.color.shopping_list_add_items_green),
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
                            )
                            .clip(
                                RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            colorResource(id = R.color.shopping_list_add_items_green),
                        ),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_15)),
                        onClick = { showSheet = !showSheet }
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_items_plus),
                            fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight(integerResource(id = R.integer.int_500)),
                            lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                            color = colorResource(id = R.color.shopping_list_add_items_green)
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = foodClubGreen
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            end = dimensionResource(id = R.dimen.dim_20),
                            start = dimensionResource(id = R.dimen.dim_20)
                        )
                )
                {
                    itemsIndexed(
                        items = state.productsList,
                        key = { _, item -> "${item.product.foodId}_${item.quantity}" }
                    ) { _, ingredient ->
                        BasketIngredient(
                            ingredient = ingredient,
                            isShown = !state.selectedProductsList.contains(ingredient.product.foodId)
                                    || !deleteSelected,
                            onSelectionChange = { bool ->
                                if (bool) events.selectIngredient(ingredient.product.foodId)
                                else events.unselectIngredient(ingredient.product.foodId)
                            },
                            onIngredientUpdate = { events.saveBasket() }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BasketIngredient(
    ingredient: Ingredient,
    isShown: Boolean,
    onSelectionChange: (isSelected: Boolean) -> Unit,
    onIngredientUpdate: () -> Unit
) {
    var isSelected by remember { mutableStateOf(ingredient.isSelected) }

    var quantity by remember { mutableIntStateOf(ingredient.quantity) }
    val type by remember { mutableStateOf(ingredient.product.label) }
    val unit by remember { mutableStateOf(ingredient.unit) }

    var showItem by remember { mutableStateOf(true) }
    if (!isShown) {
        showItem = false
    }


    AnimatedVisibility(
        visible = showItem,
        exit = shrinkOut(shrinkTowards = Alignment.TopCenter)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dim_140))
                    .border(
                        dimensionResource(id = R.dimen.dim_1),
                        colorResource(id = R.color.shopping_list_ingredient_whitish_color),
                        RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                    )
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                    .background(Color.White)
                    .padding(dimensionResource(id = R.dimen.dim_10))
            ) {
                AsyncImage(
                    model = ingredient.product.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dim_200))
                        .width(dimensionResource(id = R.dimen.dim_130))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
                )
                Box(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_35))
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_30)))
                        .background(
                            if (isSelected) foodClubGreen
                            else colorResource(id = R.color.shopping_list_whitish_color)
                        )
                        .clickable {
                            isSelected = !isSelected
                            onSelectionChange(isSelected)
                        }
                        .padding(dimensionResource(id = R.dimen.dim_4)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.dim_140),
                            top = dimensionResource(id = R.dimen.dim_10)
                        )
                        .fillMaxSize()
                ) {
                    Box(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_115))) {
                        Text(
                            text = type,
                            lineHeight = dimensionResource(id = R.dimen.fon_18).value.sp,
                            modifier = Modifier.align(Alignment.TopStart),
                            fontWeight = FontWeight.Normal,
                            fontFamily = Montserrat,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = integerResource(id = R.integer.int_3)
                        )
                    }
                    if (quantity > 0) {
                        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement
                                    .spacedBy(dimensionResource(id = R.dimen.dim_12)),
                                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.dim_16))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.shopping_list_arrow_left),
                                    contentDescription = stringResource(id = R.string.profile_picture),
                                    modifier = Modifier
                                        .height(dimensionResource(id = R.dimen.dim_24))
                                        .width(dimensionResource(id = R.dimen.dim_12))
                                        .padding(
                                            vertical = dimensionResource(id = R.dimen.dim_6),
                                            horizontal = dimensionResource(id = R.dimen.dim_3)
                                        )
                                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
                                        .clickable {
                                            ingredient.decrementQuantity(5)
                                            quantity = ingredient.quantity
                                            onIngredientUpdate()
                                        }
                                )
                                Text(
                                    quantity.toString() + unit.short,
                                    color = Color.Black,
                                    fontFamily = Montserrat,
                                    fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = integerResource(id = R.integer.int_1)
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.shopping_list_arrow_right),
                                    contentDescription = stringResource(id = R.string.profile_picture),
                                    modifier = Modifier
                                        .height(dimensionResource(id = R.dimen.dim_24))
                                        .width(dimensionResource(id = R.dimen.dim_12))
                                        .padding(
                                            vertical = dimensionResource(id = R.dimen.dim_6),
                                            horizontal = dimensionResource(id = R.dimen.dim_3)
                                        )
                                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
                                        .clickable {
                                            ingredient.incrementQuantity(5)
                                            quantity = ingredient.quantity
                                            onIngredientUpdate()
                                        }
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
        }
    }
}
