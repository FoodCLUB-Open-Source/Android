package android.kotlin.foodclub.views.home.createRecipe

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.borderBlue
import android.kotlin.foodclub.config.ui.defaultSearchBarColors
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.utils.composables.CustomSliderDiscrete
import android.kotlin.foodclub.utils.composables.EditIngredientBottomModal
import android.kotlin.foodclub.utils.composables.SwipeToDismissContainer
import android.kotlin.foodclub.utils.composables.products.IngredientItem
import android.kotlin.foodclub.utils.composables.products.ProductAction
import android.kotlin.foodclub.utils.composables.products.ProductsListTitleSection
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeEvents
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeViewModel
import android.kotlin.foodclub.views.home.createRecipe.components.CategoriesSection
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CreateRecipeView(
    navController: NavController,
    events: CreateRecipeEvents,
    state: CreateRecipeState,
    onIngredientsSearchBarClick: () -> Unit
) {
    val codeTriggered = remember { mutableStateOf(false) }
    var servingSize by remember { mutableIntStateOf(0) }
    var recipeName by remember { mutableStateOf("") }

    LaunchedEffect(key1 = codeTriggered.value) {
        if (!codeTriggered.value) {
            codeTriggered.value = true
        }
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
        when (state.productState.currentAction) {
            ProductAction.EDIT_QUANTITY -> EditIngredientBottomModal(
                ingredient = state.productState.editedIngredient,
                onDismissRequest = {
                    events.dismissAction()
                },
                onEdit = { item ->
                    events.updateIngredient(item)
                }
            )
            else -> {}
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

            item {
                IngredientSearchBar(onIngredientsSearchBarClick)
                ProductsListTitleSection(
                    modifier = Modifier,
                    includeExpiryDate = state.productState.allowExpiryDate
                )
            }
            itemsIndexed(state.productState.addedProducts) { _, item ->
                SwipeToDismissContainer(
                    onDismiss = { events.deleteIngredient(item) }
                ) { modifier ->
                    IngredientItem(
                        modifier = modifier,
                        item = item,
                        onAddItemClicked = {},
                        userIngredientsList = state.productState.addedProducts,
                        onEditQuantityClicked = {
                            events.selectAction(item, ProductAction.EDIT_QUANTITY)
                        },
                        onDateClicked = {
                            events.selectAction(item, ProductAction.CHANGE_EXPIRY_DATE)
                        },
                        onDeleteIngredient = {
                            events.deleteIngredient(item)
                        },
                        includeExpiryDate = state.productState.allowExpiryDate
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

@Composable
fun IngredientSearchBar(onIngredientsSearchBarClick: () -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val interactions = remember { mutableStateListOf<Interaction>() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.clickable { }.fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                .shadow(
                    elevation = dimensionResource(id = R.dimen.dim_2),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
                ),
            colors = defaultSearchBarColors(),
            value = searchText,
            onValueChange = { searchText = it },
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.create_recipe_ingredient_searchbar),
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            interactionSource = interactionSource,
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.search_icon_ingredients),
                    contentDescription = null,
                )
            }
        )
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when(interaction) {
                is PressInteraction.Press -> {
                    interactions.add(interaction)
                    onIngredientsSearchBarClick()
                }
                is PressInteraction.Release -> {
                    interactions.remove(interaction.press)
                }
                is PressInteraction.Cancel -> {
                    interactions.remove(interaction.press)
                }
            }
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
    ) {}
}