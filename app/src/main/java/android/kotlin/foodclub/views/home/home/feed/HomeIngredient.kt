package android.kotlin.foodclub.views.home.home.feed

import android.content.res.Configuration
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.utils.helpers.ValueParser
import android.kotlin.foodclub.views.home.ui.theme.FoodClubTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun HomeIngredient(
    ingredient: Ingredient,
    quantityMultiplier: Float,
    quantityIndicatorColor: Color = Color.Black
) {
    var isSelected by remember { mutableStateOf(ingredient.isSelected) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp -
            dimensionResource(id = R.dimen.dim_240)
    var isSmallScreen by remember { mutableStateOf(false) }
    var quantity by remember {
        mutableIntStateOf((quantityMultiplier * ingredient.quantity).toInt())
    }
    LaunchedEffect(key1 = quantityMultiplier) {
        quantity = (quantityMultiplier * ingredient.quantity).toInt()
    }

    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.dim_2),
            color = colorResource(id = R.color.home_ingredient_card_border_color),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val painter = rememberAsyncImagePainter(
                model = ingredient.imageUrl,
                placeholder = painterResource(
                    id = R.drawable.salad_ingredient
                ),
                error = painterResource(
                    id = R.drawable.salad_ingredient
                ),
            )

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.dim_12))
                    .size(dimensionResource(id = R.dimen.dim_98))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
            )

            Row(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.dim_12))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dim_32)),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    Text(
                        text = ingredient.type,
                        lineHeight = dimensionResource(id = R.dimen.fon_18).value.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Montserrat,
                        color = Color.Black,
                    )
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                            contentDescription = stringResource(id = R.string.profile_image),
                            modifier = Modifier
                                .padding(end = dimensionResource(id = R.dimen.dim_12))
                                .clickable {
                                    ingredient.decrementQuantity((5 / quantityMultiplier).toInt())
                                    quantity = (quantityMultiplier * ingredient.quantity).toInt()
                                },
                            tint = quantityIndicatorColor,
                        )
                        Text(
                            quantity.toString() + ValueParser.quantityUnitToString(ingredient.unit),
                            color = Color.Black,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = dimensionResource(id = R.dimen.dim_12))
                                .clickable {
                                    ingredient.incrementQuantity((5 / quantityMultiplier).toInt())
                                    quantity = (quantityMultiplier * ingredient.quantity).toInt()
                                },
                            tint = quantityIndicatorColor,
                        )
                    }

                }
                Box(
                    modifier = Modifier // GREEN TICK
                        .size(dimensionResource(id = R.dimen.dim_35))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_30)))
                        .background(
                            if (isSelected) foodClubGreen
                            else colorResource(id = R.color.home_ingredient_unselected_color)
                        )
                        .clickable {
                            isSelected = !isSelected
                            ingredient.isSelected = !ingredient.isSelected
                        }
                        .padding(dimensionResource(id = R.dimen.dim_4)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = if (isSelected) Icons.Outlined.Check else Icons.Outlined.Add,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )
                }
            }
        }
    }
    Spacer(
        modifier = Modifier.height(
            if (isSmallScreen) dimensionResource(id = R.dimen.dim_10)
            else dimensionResource(id = R.dimen.dim_20)
        )
    )
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HomeIngredientPreview() {
    FoodClubTheme {
        HomeIngredient(
            ingredient = Ingredient(
                id = "3",
                type = "Tomato paste",
                quantity = 1,
                unit = QuantityUnit.GRAMS,
                imageUrl = "",
                expirationDate = "expirationDate3",
                isSelected = false
            ),
            quantityMultiplier = 1f,
            quantityIndicatorColor = foodClubGreen
        )
    }
}
