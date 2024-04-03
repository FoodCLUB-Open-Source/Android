package android.kotlin.foodclub.views.home.createRecipe

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.borderBlue
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.utils.composables.CustomSliderDiscrete
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeEvents
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeViewModel
import android.kotlin.foodclub.views.home.createRecipe.components.CategoriesSection
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun CreateRecipeView(
    navController: NavController,
    events: CreateRecipeEvents,
    state: CreateRecipeState
) {
    var showSheet by remember { mutableStateOf(false) }
    val codeTriggered = remember { mutableStateOf(false) }
    var servingSize by remember { mutableIntStateOf(0) }
    var recipeName by remember { mutableStateOf("") }

    LaunchedEffect(key1 = codeTriggered.value) {
        if (!codeTriggered.value) {
            codeTriggered.value = true
        }
    }

    val triggerBottomSheetModal: () -> Unit = {
        showSheet = !showSheet
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                start = dimensionResource(id = R.dimen.dim_15),
                top = dimensionResource(id = R.dimen.dim_0),
                end = dimensionResource(id = R.dimen.dim_15),
                bottom = dimensionResource(id = R.dimen.dim_15)
            )
    ) {
        if (showSheet) {
            IngredientsBottomSheet(
                onDismiss = triggerBottomSheetModal,
                productsData = state.products,
                loadMoreObjects = { searchText, onLoadCompleted ->
                    events.fetchMoreProducts(searchText, onLoadCompleted)
                },
                onListUpdate = { events.fetchProductsDatabase(it) },
                onSave = { events.addIngredient(it) }
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(id = R.dimen.dim_70),
                    start = dimensionResource(id = R.dimen.dim_8),
                    end = dimensionResource(id = R.dimen.dim_8)
                )
                .background(Color.White)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.dim_16)
                )) {
                    Header { navController.navigateUp() }

                    OutlinedTextField(
                        value = recipeName,
                        onValueChange = { recipeName = it },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.add_recipe_name),
                                fontFamily = Montserrat,
                                fontSize = dimensionResource(id = R.dimen.fon_15).value.sp,
                                color = Color.Black.copy(alpha = 0.4f)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = borderBlue,
                            unfocusedBorderColor = borderBlue
                        )
                    )

                    ServingSizeSection { servingSize = it }

                    CategoriesSection(
                        categories = state.categories,
                        onCategoryRemove = { events.unselectCategory(it) },
                        onCategoryAdd = { events.selectCategory(it) },
                        onCategoriesClear = { events.clearCategories() }
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_2)))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.ingredients),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = TextUnit(-0.72f, TextUnitType.Sp),
                            fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                        )
                        ClickableText(
                            text = AnnotatedString(stringResource(id = R.string.clear_all)),
                            onClick = { events.clearIngredients() },
                            style = TextStyle(
                                color = foodClubGreen,
                                fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                                letterSpacing = TextUnit(-0.48f, TextUnitType.Sp),
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_2)))
                }

            }
            items(items = state.ingredients, key = { it.product.foodId }) { ingredient ->
                Ingredient(
                    ingredient = ingredient,
                    isRevealed = state.revealedIngredientId == ingredient.product.foodId,
                    onExpand = { events.onIngredientExpanded(ingredient.product.foodId) },
                    onCollapse = { events.onIngredientCollapsed(ingredient.product.foodId) },
                    onDelete = { events.onIngredientDeleted(ingredient) }
                )
            }
        }

        Button(
            shape = RectangleShape,
            modifier = Modifier
                .border(
                    dimensionResource(id = R.dimen.dim_1),
                    foodClubGreen,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = foodClubGreen,
                contentColor = Color.White
            ), contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_15)),
            onClick = { /* Create Recipe */ }
        ) {
            Text(
                text = stringResource(id = R.string.share_recipe),
                color = Color.White,
                fontFamily = Montserrat,
                fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

//Ingredient is gonna be changed (new design on Figma)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun Ingredient(
    ingredient: Ingredient,
    isRevealed: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onDelete: () -> Unit
) {
    val ingredientXOffset = remember { mutableFloatStateOf(0f) }
    var showItem by remember { mutableStateOf(true) }

    val transitionState = remember {
        MutableTransitionState(isRevealed).apply { targetState = !isRevealed }
    }
    val transition = updateTransition(transitionState, label = "")
    val offsetTransition by transition.animateFloat(
        label = stringResource(id = R.string.ingredient_offset_transitions),
        transitionSpec = { tween(durationMillis = 500) },
        targetValueByState = {
            if (isRevealed) (-ingredientXOffset.floatValue - 200f)
            else -ingredientXOffset.floatValue
        }
    )

    var quantity by remember { mutableIntStateOf(ingredient.quantity) }
    val type by remember { mutableStateOf(ingredient.product.label) }
    val unit by remember { mutableStateOf(ingredient.unit) }


    AnimatedVisibility(
        visible = showItem,
        exit = shrinkOut(shrinkTowards = Alignment.TopCenter)
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dim_100))
                .padding(end = dimensionResource(id = R.dimen.dim_15))
        ) {
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
                ), contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_5)),
                onClick = { showItem = false }
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
        Column {
            Box(modifier = Modifier
                .offset {
                    IntOffset((ingredientXOffset.floatValue + offsetTransition).roundToInt(), 0)
                }
                .pointerInput(key1 = "") {
                    detectHorizontalDragGestures { change, dragAmount ->
                        val original = Offset(ingredientXOffset.floatValue, 0f)
                        val summed = original + Offset(x = dragAmount, y = 0f)
                        val newValue = Offset(summed.x.coerceIn(-200f, 0f), 0f)
                        if (newValue.x <= -20f) {
                            onExpand()
                            return@detectHorizontalDragGestures
                        } else if (newValue.x >= 0) {
                            onCollapse()
                            return@detectHorizontalDragGestures
                        }
                        if (change.positionChange() != Offset.Zero) change.consume()
                        ingredientXOffset.floatValue = newValue.x
                    }
                }
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dim_100))
                .border(
                    dimensionResource(id = R.dimen.dim_1),
                    Color(0xFFE8E8E8),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
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
                        .height(dimensionResource(id = R.dimen.dim_80))
                        .width(dimensionResource(id = R.dimen.dim_80))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
                )
                Box(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.dim_95))
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.dim_70)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dim_105))
                            .align(Alignment.CenterStart)
                    ) {
                        Text(
                            text = type,
                            modifier = Modifier.align(Alignment.TopStart),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.dim_35))
                                    .padding(end = dimensionResource(id = R.dimen.dim_5))
                                    .clickable {
                                        ingredient.decrementQuantity(5)
                                        quantity = ingredient.quantity
                                    }
                            )
                            Text(
                                text = quantity.toString() + unit.short,
                                color = Color.Black,
                                fontFamily = Montserrat,
                                fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                            )
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.dim_35))
                                    .padding(start = dimensionResource(id = R.dimen.dim_5))
                                    .clickable {
                                        ingredient.incrementQuantity(5)
                                        quantity = ingredient.quantity
                                    }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
        }
    }

    LaunchedEffect(showItem) {
        if (!showItem) {
            delay(800)
            onDelete()
        }
    }
}

@Composable
fun Header(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dim_55),
                bottom = dimensionResource(id = R.dimen.dim_20)
            )
            .height(dimensionResource(id = R.dimen.dim_50)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.dim_10))
                .background(Color.Transparent),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                    )
                    .align(Alignment.BottomCenter)
                    .width(dimensionResource(id = R.dimen.dim_40))
                    .height(dimensionResource(id = R.dimen.dim_40)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB8B8B8),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(
                    dimensionResource(id = R.dimen.dim_5)
                ),
                onClick = { onBackClick() }
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.baseline_arrow_back_ios_new_24
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_20))
                        .height(dimensionResource(id = R.dimen.dim_20))
                )
            }
        }
        Text(
            text = stringResource(id = R.string.my_new_recipe),
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.dim_8)
            ),
            fontFamily = Montserrat,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = TextUnit(-1.12f, TextUnitType.Sp),
            fontSize = dimensionResource(id = R.dimen.fon_28).value.sp
        )
    }
}

@Composable
fun ServingSizeSection(onSliderValueChange: (Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.dim_16)
        )
    ) {
        Text(
            text = stringResource(id = R.string.serving_size),
            fontFamily = Montserrat,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = TextUnit(-0.72f, TextUnitType.Sp),
            fontSize = dimensionResource(id = R.dimen.fon_18).value.sp
        )
        CustomSliderDiscrete(
            maxValue = 16f,
            onValueChange = { onSliderValueChange(it) }
        )
    }
}

@Composable
@Preview
fun CreateRecipeViewPreview() {
    val navController = rememberNavController()
    val viewModel: CreateRecipeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    CreateRecipeView(
        navController,
        viewModel,
        state.value
    )
}