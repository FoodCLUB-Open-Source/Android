package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.utils.composables.CustomSlider
import android.kotlin.foodclub.viewModels.home.HomeViewModel
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheetIngredients(onDismiss: () -> Unit, recipe: Recipe?) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 240.dp
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSmallScreen by remember { mutableStateOf(false) }

    val viewModel: HomeViewModel = hiltViewModel()

    if (screenHeight <= 440.dp) {
        isSmallScreen = true
    }
    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        if (recipe != null) {
            var ingredientsMultiplier by remember { mutableFloatStateOf(recipe.servingSize.toFloat()) }
            val ingredientsDivider = recipe.servingSize.toFloat()

            Column(
                modifier = Modifier
                    .height(screenHeight)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            "Chicken broth and meatballs",
                            color = Color.Black,
                            fontFamily = Montserrat,
                            fontSize = if (isSmallScreen) 18.sp else 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
                    ) {
                        Button(
                            shape = RectangleShape,
                            modifier = Modifier
                                .border(
                                    1.dp, Color(0xFF3A7CA8), shape = RoundedCornerShape(20.dp)
                                )
                                .clip(RoundedCornerShape(20.dp))
                                .width(80.dp)
                                .height(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF3A7CA8)
                            ),
                            contentPadding = PaddingValues(bottom = 2.dp),
                            onClick = {}
                        ) {
                            Text(
                                "copy clip",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Montserrat,
                                color = Color(0xFF3A7CA8),
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(if (isSmallScreen) 0.dp else 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = if (isSmallScreen) 16.dp else 0.dp)
                    ) {
                        Text(
                            "Serving Size",
                            color = Color.Black,
                            fontFamily = Montserrat,
                            fontSize = if (isSmallScreen) 14.sp else 17.sp
                        )
                    }
                    Box(
                        modifier = Modifier.padding(end = if (isSmallScreen) 10.dp else 0.dp)
                    ) {
                        CustomSlider(
                            sliderWidth = if (isSmallScreen) 150.dp else 200.dp,
                            initialValue = ingredientsDivider,
                            maxValue = 24f,
                            onValueChange = {
                                ingredientsMultiplier = it.toFloat()
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            "Ingredients", color = Color.Black,
                            fontFamily = Montserrat,
                            fontSize = if (isSmallScreen) 13.sp else 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(if (isSmallScreen) 10.dp else 16.dp))
                    Box(modifier = Modifier.padding(end = if (isSmallScreen) 16.dp else 16.dp)) {
                        Text(
                            "Clear", color = Color(0xFF7EC60B),
                            fontFamily = Montserrat,
                            fontSize = if (isSmallScreen) 13.sp else 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isSmallScreen) 210.dp else 300.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LazyColumn {
                        itemsIndexed(recipe.ingredients) { index, item ->
                            HomeIngredient(
                                ingredient = item,
                                quantityMultiplier = (ingredientsMultiplier / ingredientsDivider)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(
                                1.dp, Color(126, 198, 11), RoundedCornerShape(15.dp)
                            )
                            .clip(RoundedCornerShape(15.dp))
                            .fillMaxWidth(),
                        colors = defaultButtonColors(),
                        contentPadding = PaddingValues(15.dp),
                        onClick = { viewModel.addIngredientsToBasket() }
                    ) {
                        Text(
                            "Add to my shopping list",
                            color = Color.White,
                            fontFamily = Montserrat,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }

    }

}