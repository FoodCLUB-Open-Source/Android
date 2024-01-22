package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.utils.helpers.ValueParser
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun HomeIngredient(
    ingredient: Ingredient,
    quantityMultiplier: Float
) {
    var isSelected by remember { mutableStateOf(ingredient.isSelected) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp -
            dimensionResource(id = R.dimen.dim_240)
    var isSmallScreen by remember { mutableStateOf(false) }
    var quantity by remember {
        mutableIntStateOf((quantityMultiplier * ingredient.quantity).toInt())
    }
    LaunchedEffect(key1 = quantityMultiplier) {
        Log.d("HomeIngredient", "change: $quantityMultiplier")
        quantity = (quantityMultiplier * ingredient.quantity).toInt()
    }

    Log.d("ScreenHeightLog", "Screen bottom sheet: $screenHeight")
    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                if (isSmallScreen) dimensionResource(id = R.dimen.dim_100) else
                    dimensionResource(id = R.dimen.dim_130)
            )
            .border(
                1.dp, Color(0xFFE8E8E8),
                RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
            .background(Color.White)
            .padding(dimensionResource(id = R.dimen.dim_10))
    ) {
        val ingredientImage = if(ingredient.imageUrl == "") {
            R.drawable.salad_ingredient
        } else ingredient.imageUrl
        AsyncImage(
            model = ingredientImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dim_110))
                .width(
                    if (isSmallScreen) dimensionResource(id = R.dimen.dim_85) else
                        dimensionResource(id = R.dimen.dim_100)
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
        )
        Box(
            modifier = Modifier // GREEN TICK
                .size(dimensionResource(id = R.dimen.dim_35))
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_30)))
                .background(
                    if (isSelected) foodClubGreen
                    else Color(0xFFECECEC)
                )
                .clickable {
                    isSelected = !isSelected
                    ingredient.isSelected = !ingredient.isSelected
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
        Box(modifier = Modifier
            .padding(
                start = if (isSmallScreen) dimensionResource(id = R.dimen.dim_90) else
                    dimensionResource(id = R.dimen.dim_100),
                top = dimensionResource(id = R.dimen.dim_10)
            )
            .fillMaxSize()
        ) {
            Box(modifier = Modifier
                .width(dimensionResource(id = R.dimen.dim_115))
                .padding(start = dimensionResource(id = R.dimen.dim_10))) {
                Text(
                    text = ingredient.type,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    fontWeight = FontWeight.Normal,
                    fontFamily = Montserrat
                )
            }
            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_50))
                            .padding(end = dimensionResource(id = R.dimen.dim_15))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
                            .clickable {
                                ingredient.decrementQuantity((5 / quantityMultiplier).toInt())
                                quantity = (quantityMultiplier * ingredient.quantity).toInt()
                            }
                    )
                    Text(
                        quantity.toString() + ValueParser.quantityUnitToString(ingredient.unit),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = 14.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_50))
                            .padding(start = dimensionResource(id = R.dimen.dim_15))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
                            .clickable {
                                ingredient.incrementQuantity((5 / quantityMultiplier).toInt())
                                quantity = (quantityMultiplier * ingredient.quantity).toInt()
                            }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(if (isSmallScreen) 10.dp else 20.dp))
}