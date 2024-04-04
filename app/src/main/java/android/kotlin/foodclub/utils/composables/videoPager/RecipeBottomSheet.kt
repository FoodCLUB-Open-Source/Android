package android.kotlin.foodclub.utils.composables.videoPager

import android.kotlin.foodclub.R
import androidx.compose.foundation.horizontalScroll
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.utils.composables.customComponents.CustomSlider
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeBottomSheet(
    onDismiss: () -> Unit,
    recipe: Recipe?,
    onAddToBasket: () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_160)
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSmallScreen by remember { mutableStateOf(false) }

//    val sections = listOf("Ingredients", "Chef Ai", "Health", "Environment","Sticker")
//    var selectedSection by remember { mutableStateOf(sections.first()) }

    val categories = listOf("Protein", "Breakfast")

    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
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
                    .height(screenHeight)
                    .padding(
                        start = dimensionResource(id = R.dimen.dim_16),
                        end = dimensionResource(id = R.dimen.dim_16)
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = dimensionResource(id = R.dimen.dim_16))
                    ) {
                        Text(
                            stringResource(id = R.string.example_recipe),
                            color = Color.Black,
                            fontFamily = Montserrat,
                            fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.dim_18).value.sp else dimensionResource(id = R.dimen.dim_22).value.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_16)))

                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(
                            start = if (isSmallScreen) dimensionResource(id = R.dimen.dim_10) else dimensionResource(
                                id = R.dimen.dim_10
                            )
                        )
                ) {
                    categories.forEach { category ->
                        LabelText(
                            text = category
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_8)))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            if (isSmallScreen) dimensionResource(id = R.dimen.dim_0) else dimensionResource(id = R.dimen.dim_16)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                start = if (isSmallScreen) dimensionResource(id = R.dimen.dim_16) else dimensionResource(
                                    id = R.dimen.dim_0
                                )
                            )
                    ) {
                        Text(
                            stringResource(id = R.string.serving_size),
                            color = Color.Black,
                            fontFamily = Montserrat,
                            fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.dim_14).value.sp else dimensionResource(id = R.dimen.dim_17).value.sp
                        )
                    }
                    Box(
                        modifier = Modifier.padding(end = if (isSmallScreen) dimensionResource(id = R.dimen.dim_10) else dimensionResource(id = R.dimen.dim_0))
                    ) {
                        CustomSlider(
                            sliderWidth = if (isSmallScreen) dimensionResource(id = R.dimen.dim_150) else dimensionResource(id = R.dimen.dim_200),
                            initialValue = ingredientsDivider,
                            maxValue = 24f,
                            onValueChange = {
                                ingredientsMultiplier = it.toFloat()
                            }
                        )
                    }
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

                IngredientsSection(isSmallScreen,recipe,ingredientsMultiplier,ingredientsDivider)

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(
                                dimensionResource(id = R.dimen.dim_1),
                                Color(126, 198, 11),
                                RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                            .fillMaxWidth(),
                        colors = defaultButtonColors(),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_15)),
                        onClick = {
                            recipe.ingredients.map { ingredient -> ingredient.quantity = (ingredient.quantity * ingredientsMultiplier/2).toInt() }
                            onAddToBasket()
                            onDismiss()
                        }
                    ) {
                        Text(
                            stringResource(id = R.string.add_to_my_shopping_list),
                            color = Color.White,
                            fontFamily = Montserrat,
                            fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
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
            .padding(horizontal = dimensionResource(id = R.dimen.dim_8), vertical = dimensionResource(id = R.dimen.dim_4))
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = Montserrat,
            lineHeight = dimensionResource(id = R.dimen.dim_14).value.sp,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_2))
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
fun IngredientsSection(isSmallScreen: Boolean, recipe: Recipe?, ingredientsMultiplier: Float, ingredientsDivider: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(id = R.string.ingredients), color = Color.Black,
            fontFamily = Montserrat,
            fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.dim_13).value.sp else dimensionResource(id = R.dimen.dim_16).value.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensionResource(id = R.dimen.dim_16))
        ) {
        }
        Spacer(modifier = Modifier.width(if (isSmallScreen) dimensionResource(id = R.dimen.dim_10) else dimensionResource(id = R.dimen.dim_16)))
        Box(modifier = Modifier.padding(end = if (isSmallScreen) dimensionResource(id = R.dimen.dim_10) else dimensionResource(id = R.dimen.dim_16))) {
            Text(
                stringResource(id = R.string.clear), color = colorResource(R.color.bottom_sheet_nav_bar_selected_color),
                fontFamily = Montserrat,
                fontSize = if (isSmallScreen) dimensionResource(id = R.dimen.dim_13).value.sp else dimensionResource(id = R.dimen.dim_16).value.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
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
        LazyColumn {
            if (recipe != null) {
                itemsIndexed(recipe.ingredients) { _, item ->
                    Ingredient(
                        ingredient = item,
                        quantityMultiplier = (ingredientsMultiplier / ingredientsDivider)
                    )
                }
            }
        }
    }
}