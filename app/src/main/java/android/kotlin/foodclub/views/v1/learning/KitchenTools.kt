package android.kotlin.foodclub.views.v1.learning

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.FoodClubTheme
import android.kotlin.foodclub.config.ui.Montserrat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun KitchenTools() {
    KitchenToolsUI()
}

@Composable
fun KitchenToolsUI(modifier: Modifier = Modifier) {

    // TODO these should be dynamic and come from the backend
    val cardTitle = stringArrayResource(id = R.array.card_titles).toList()

    val cardSubText = stringArrayResource(id = R.array.card_subtitles).toList()

    val imageContents = listOf(R.drawable.temporary_image_placeholder, R.drawable.temporary_image_placeholder, R.drawable.temporary_image_placeholder)

    val learnButtons = stringArrayResource(id = R.array.learn_buttons).toList()

    val quizButtons = stringArrayResource(id = R.array.quiz_buttons).toList()

    val customGreyColor = Color(0xFFE7E7E7)

    val customGreenColor = Color(0xFF80C40C)

    Text(
        text = stringResource(id = R.string.kitchen_tools),
        fontSize = dimensionResource(id = R.dimen.fon_24).value.sp,
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = TextUnit(-0.96f, TextUnitType.Sp),
        modifier = Modifier.padding(top =  dimensionResource(id = R.dimen.dim_32), start = dimensionResource(id = R.dimen.dim_70))
    )

    Box(
        modifier = Modifier
            .offset(dimensionResource(id = R.dimen.dim_18), dimensionResource(id = R.dimen.dim_29))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
            .size( dimensionResource(id = R.dimen.dim_32))
            .background(customGreyColor)


    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_left),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.dim_60), start = dimensionResource(id = R.dimen.dim_18), end = dimensionResource(id = R.dimen.dim_18))
    ) {


        items(count = 3) { index ->
            Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_16)))

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15)))
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dim_230))
                    .background(Color.White)
            ) {
                Text(
                    text = cardTitle.getOrNull(index) ?: stringResource(id = R.string.default_text),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = TextUnit(-0.8f, TextUnitType.Sp),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.dim_25))
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = imageContents.getOrNull(index) ?: R.drawable.temporary_image_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_330))
                        .height(dimensionResource(id = R.dimen.dim_200))
                        .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15)))
                        .padding(bottom = dimensionResource(id = R.dimen.dim_105))
                        .align(Alignment.Center)
                )

                Text(
                    text = cardSubText.getOrNull(index) ?: stringResource(id = R.string.default_text),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                    letterSpacing = TextUnit(-0.8f, TextUnitType.Sp),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.dim_78))
                        .align(Alignment.Center)
                )

                Button(
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)),
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.dim_55), bottom = dimensionResource(id = R.dimen.dim_15))
                        .height( dimensionResource(id = R.dimen.dim_35))
                        .width(dimensionResource(id = R.dimen.dim_100))
                        .align(Alignment.BottomStart),

                    onClick = {
                        // TODO add click functionality
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor  = customGreenColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = learnButtons.getOrNull(index) ?: stringResource(id = R.string.default_text),
                        fontFamily = Montserrat,
                        letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
                        color = Color.White,
                        fontSize = dimensionResource(id = R.dimen.fon_13).value.sp

                    )
                }

                Button(
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)),
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.dim_55), bottom = dimensionResource(id = R.dimen.dim_15))
                        .height( dimensionResource(id = R.dimen.dim_35))
                        .width(dimensionResource(id = R.dimen.dim_100))
                        .align(Alignment.BottomEnd),

                    onClick = {
                        // TODO add click functionality
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor  = customGreyColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = quizButtons.getOrNull(index) ?: stringResource(id = R.string.default_text),
                        fontFamily = Montserrat,
                        letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
                        color = Color.Black,
                        fontSize = dimensionResource(id = R.dimen.fon_13).value.sp

                    )
                }


            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun KitchenToolsPreview() {
    FoodClubTheme {
        rememberNavController()
        KitchenTools()
    }
}
