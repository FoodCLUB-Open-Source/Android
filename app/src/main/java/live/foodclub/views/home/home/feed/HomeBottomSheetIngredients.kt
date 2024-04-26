package live.foodclub.views.home.home.feed

import android.content.res.Configuration
import live.foodclub.R
import live.foodclub.config.ui.FoodClubTheme
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.Satoshi
import live.foodclub.config.ui.defaultButtonColors
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.domain.enums.Category
import live.foodclub.domain.enums.QuantityUnit
import live.foodclub.domain.models.products.Ingredient
import live.foodclub.domain.models.products.Product
import live.foodclub.domain.models.recipes.Recipe
import live.foodclub.utils.composables.customComponents.CustomSliderDiscrete
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheetIngredients(
    onDismiss: () -> Unit,
    recipe: Recipe?,
    postTitle: String,
    onAddToBasket: () -> Unit
) {
    val screenHeight =
        LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_160)
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSmallScreen by remember { mutableStateOf(false) }

//    val sections = listOf("Ingredients", "Chef Ai", "Health", "Environment", "Sticker")
//    var selectedSection by remember { mutableStateOf(sections.first()) }

    val categories = listOf("Protein", "Breakfast")

    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
    }
    val scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    val navigationBarColor = Color.White
    LaunchedEffect(true) {
        scope.launch {
            bottomSheetState.expand()
        }
        systemUiController.setNavigationBarColor(navigationBarColor)
    }


    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        scrimColor = Color.Transparent
    ) {
        if (recipe != null) {
            var ingredientsMultiplier by remember { mutableFloatStateOf(recipe.servingSize.toFloat()) }
            val ingredientsDivider = recipe.servingSize.toFloat()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = dimensionResource(id = R.dimen.dim_16))
                    ) {
                        Text(
                            postTitle,
                            color = Color.Black,
                            fontFamily = Montserrat,
                            fontSize = dimensionResource(id = R.dimen.dim_20).value.sp,
                            lineHeight = dimensionResource(id = R.dimen.dim_24).value.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(
                            start = dimensionResource(id = R.dimen.dim_16)
                        )
                ) {
                    categories.forEach { category ->
                        LabelText(
                            text = category
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_8)))
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_16))
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_16))
                ) {
                    Text(
                        stringResource(id = R.string.serving_size),
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = dimensionResource(id = R.dimen.fon_17).value.sp,
                        color = Color.Black,
                        letterSpacing = dimensionResource(id = R.dimen.fon_0_04).value.sp,
                        fontFamily = Montserrat,
                        fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.dim_14).value.sp else dimensionResource(
                            id = R.dimen.dim_17
                        ).value.sp
                    )

                    CustomSliderDiscrete(
                        maxValue = 24f,
                        startValue = recipe.servingSize.toFloat(),
                        onValueChange = { ingredientsMultiplier = it.toFloat() },
                        inactiveTrackColor = colorResource(id = R.color.home_ingredient_slider_color),
                        stepsColor = colorResource(id = R.color.home_ingredient_slider_color),
                    )

                }

                //Navigation Bar for "Ingredients", "Chef Ai", "Health", "Environment","Sticker"
                //implemented using a horizontal scroll
                //                Row(
                //                    modifier = Modifier
                //                        .fillMaxWidth()
                //                        .horizontalScroll(rememberScrollState())
                //                        .padding(
                //                            start = if (isSmallScreen) dimensionResource(id = R.dimen.dim_10) else dimensionResource(
                //                                id = R.dimen.dim_10
                //                            )
                //                        )
                //                ) {
                //                    sections.forEach { section ->
                //                        IconButton(
                //                            onClick = { selectedSection = section }
                //                        ) {
                //                            val isSelected = section == selectedSection
                //                            val textColor = colorResource(R.color.bottom_sheet_nav_bar_selected_color)
                //                            val smallScreenFontSize = dimensionResource(id = R.dimen.dim_14).value.sp
                //                            val fontSize = dimensionResource(id = R.dimen.dim_17).value.sp
                //                            val underlinePadding = dimensionResource(id = R.dimen.dim_10)
                //                            val strokeWidthPx = with(LocalDensity.current) { dimensionResource(id = R.dimen.dim_1).toPx() }
                //                            val underlineOffsetPx =
                //                                if(isSmallScreen) with(LocalDensity.current) { dimensionResource(id = R.dimen.dim_2).toPx() +smallScreenFontSize.toPx() + underlinePadding.toPx()}
                //                                else with(LocalDensity.current) { dimensionResource(id = R.dimen.dim_2).toPx() + fontSize.toPx() + underlinePadding.toPx()}
                //                            Text(
                //                                text = section,
                //                                fontFamily = Montserrat,
                //                                fontSize = if (isSmallScreen) smallScreenFontSize else fontSize,
                //                                maxLines = 1,
                //                                overflow = TextOverflow.Ellipsis,
                //                                modifier = Modifier
                //                                    .padding(horizontal = dimensionResource(id = R.dimen.dim_5))
                //                                    .drawBehind {
                //                                        if (isSelected){
                //                                            drawLine(
                //                                                color = textColor,
                //                                                strokeWidth = strokeWidthPx,
                //                                                start = Offset(0f, underlineOffsetPx),
                //                                                end = Offset(size.width, underlineOffsetPx)
                //
                //                                    )}},
                //                                color = if (isSelected) colorResource(R.color.bottom_sheet_nav_bar_selected_color) else Color.Black,
                //                            )
                //                        }
                //                    }
                //                }
                //
                //                when (selectedSection) {
                //                    "Ingredients" -> IngredientsSection(isSmallScreen,recipe,ingredientsMultiplier,ingredientsDivider)
                //                    "Chef Ai" -> ChefAiSection()
                //                    "Health" -> HealthSection()
                //                    "Environment" -> EnvironmentSection()
                //                    "Sticker" -> StickerSection()
                //
                //                }

                var selectedTabIndex by remember {
                    mutableIntStateOf(0)
                }
                val interactionSource = remember {
                    MutableInteractionSource()
                }

                val sections = listOf("Ingredients")
                /**
                 * fillMaxWidth fixed %30 to screen, because we just have ingredients
                 * tabs. In future just set this param to 1f
                 *
                 * add more section to the sections list
                 * */
                Box(modifier = Modifier.fillMaxWidth()) {
                    Divider(
                        color = colorResource(id = R.color.home_ingredient_bottom_sheet_divider_color),
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                    TabRow(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.dim_16))
                            .fillMaxWidth(0.3f),
                        selectedTabIndex = selectedTabIndex,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                color = foodClubGreen,
                                height = 1.dp,
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            )
                        },
                        containerColor = Color.White
                    ) {
                        sections.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                modifier = Modifier.padding(bottom = 14.dp),

                                ) {
                                Text(
                                    modifier = Modifier.clickable(
                                        enabled = true,
                                        interactionSource = interactionSource,
                                        indication = null,
                                        onClick = { selectedTabIndex = index },
                                    ),
                                    text = title,
                                    fontFamily = Montserrat,
                                    fontWeight = FontWeight.Medium,
                                    color = foodClubGreen,
                                    lineHeight = 24.85.sp
                                )
                            }

                        }

                    }

                }


                Box(
                    modifier = Modifier
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.dim_16))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                stringResource(id = R.string.clear), color = foodClubGreen,
                                fontFamily = Satoshi,
                                fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        when (selectedTabIndex) {
                            0 -> IngredientsSection(
                                isSmallScreen,
                                recipe,
                                ingredientsMultiplier,
                                ingredientsDivider
                            )

                            1 -> {}
                            2 -> {}
                            else -> {}
                        }
                    }
                }
            }
            Divider(
                thickness = dimensionResource(id = R.dimen.dim_1),
                color = colorResource(id = R.color.home_ingredient_bottom_sheet_divider_color)
            )
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(bottom = 32.dp)
                    .border(
                        dimensionResource(id = R.dimen.dim_1),
                        foodClubGreen,
                        RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                    )
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))),
                colors = defaultButtonColors(),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_15)),
                onClick = {
                    recipe.ingredients.map { ingredient ->
                        ingredient.quantity =
                            (ingredient.quantity * ingredientsMultiplier / 2).toInt()
                    }
                    onAddToBasket()
                    onDismiss()
                }
            ) {
                Text(
                    stringResource(id = R.string.add_to_my_shopping_list),
                    color = Color.White,
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_24).value.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HomeBottomSheetIngredientsPreview() {
    FoodClubTheme {
        Surface {
            val fakeRecipe = Recipe(
                id = 1L,
                postId = 1L,
                description = "description",
                servingSize = 1,
                ingredients = listOf(
                    Ingredient(
                        product = Product(
                            foodId = "1",
                            label = "type1",
                            image = null,
                            units = QuantityUnit.entries
                        ),
                        quantity = 1,
                        unit = QuantityUnit.GRAM,
                        expirationDate = "expirationDate1",
                        isSelected = false
                    ),
                    Ingredient(
                        product = Product(
                            foodId = "2",
                            label = "type2",
                            image = null,
                            units = QuantityUnit.entries
                        ),
                        quantity = 1,
                        unit = QuantityUnit.GRAM,
                        expirationDate = "expirationDate2",
                        isSelected = false
                    ),
                    Ingredient(
                        product = Product(
                            foodId = "3",
                            label = "type3",
                            image = null,
                            units = QuantityUnit.entries
                        ),
                        quantity = 1,
                        unit = QuantityUnit.GRAM,
                        expirationDate = "expirationDate3",
                        isSelected = false
                    ),
                ),
                categories = listOf(Category.ITALIAN, Category.MEAT, Category.FAT_REDUCTION)
            )
            HomeBottomSheetIngredients(
                onAddToBasket = {},
                onDismiss = {},
                postTitle = "Chicken broth and meatballs",
                recipe = fakeRecipe
            )
        }
    }
}

@Composable
fun LabelText(
    text: String
) {
    val backgroundColor = colorResource(R.color.bottom_sheet_recipe_label_blue)
    val textColor = Color.White

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(
                horizontal = dimensionResource(id = R.dimen.dim_8),
                vertical = dimensionResource(id = R.dimen.dim_4)
            )
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = Montserrat,
            lineHeight = dimensionResource(id = R.dimen.dim_14).value.sp,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_4))
        )
    }
}


//@Composable
//fun StickerSection() {
//    TODO("Not yet implemented")
//}
//
//@Composable
//fun EnvironmentSection() {
//    TODO("Not yet implemented")
//}
//
//@Composable
//fun HealthSection() {
//    TODO("Not yet implemented")
//}
//
//@Composable
//fun ChefAiSection() {
//    TODO("Not yet implemented")
//}

@Composable
fun IngredientsSection(
    isSmallScreen: Boolean,
    recipe: Recipe?,
    ingredientsMultiplier: Float,
    ingredientsDivider: Float
) {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_16)))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                if (isSmallScreen) dimensionResource(id = R.dimen.dim_210) else dimensionResource(
                    id = R.dimen.dim_300
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy((-12).dp)
        ) {
            if (recipe != null) {
                itemsIndexed(recipe.ingredients) { _, item ->
                    HomeIngredient(
                        ingredient = item,
                        quantityMultiplier = (ingredientsMultiplier / ingredientsDivider),
                        quantityIndicatorColor = foodClubGreen
                    )
                }
            }
        }
    }
}