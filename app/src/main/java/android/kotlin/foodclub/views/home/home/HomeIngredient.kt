package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeIngredient(
    ingredientTitle: String,
    ingredientImage: Int
) {
    var isSelected by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 240.dp
    var isSmallScreen by remember { mutableStateOf(false) }

    if (screenHeight <= 440.dp) {
        isSmallScreen = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isSmallScreen) 100.dp else 130.dp)
            .border(1.dp, Color(0xFFE8E8E8), RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = ingredientImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(110.dp)
                .width(if (isSmallScreen) 85.dp else 100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Box(
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(30.dp))
                .background(
                    if (isSelected) foodClubGreen
                    else Color(0xFFECECEC)
                )
                .clickable { isSelected = !isSelected }
                .padding(4.dp),
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
                .padding(start = if (isSmallScreen) 90.dp else 110.dp, top = 10.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .width(115.dp)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = ingredientTitle,
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
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 15.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { }
                    )
                    Text(
                        text = stringResource(id = R.string.weight_placeholder),
                        color = Color.Black,
                        fontFamily = Montserrat,
                        fontSize = 14.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 15.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(if (isSmallScreen) 10.dp else 20.dp))
}