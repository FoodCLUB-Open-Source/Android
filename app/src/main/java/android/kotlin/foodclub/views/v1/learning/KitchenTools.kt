package android.kotlin.foodclub.views.v1.learning

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.FoodClubTheme
import android.kotlin.foodclub.config.ui.Montserrat
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun KitchenTools(
    navController: NavController,
) {
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
        fontSize = 24.sp,
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = TextUnit(-0.96f, TextUnitType.Sp),
        modifier = Modifier.padding(top = 32.dp, start = 70.dp)
    )

    Box(
        modifier = Modifier
            .offset(18.dp, 29.dp)
            .clip(RoundedCornerShape(12.dp))
            .size(32.dp)
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
            .padding(top = 60.dp, start = 18.dp, end = 18.dp)
    ) {


        items(count = 3) { index ->
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = cardTitle.getOrNull(index) ?: stringResource(id = R.string.default_text),
                    fontFamily = Montserrat,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = TextUnit(-0.8f, TextUnitType.Sp),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = imageContents.getOrNull(index) ?: R.drawable.temporary_image_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .width(330.dp)
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                        .padding(bottom = 105.dp)
                        .align(Alignment.Center)
                )

                Text(
                    text = cardSubText.getOrNull(index) ?: stringResource(id = R.string.default_text),
                    fontFamily = Montserrat,
                    fontSize = 12.sp,
                    letterSpacing = TextUnit(-0.8f, TextUnitType.Sp),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 78.dp)
                        .align(Alignment.Center)
                )

                Button(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 55.dp, bottom = 15.dp)
                        .height(35.dp)
                        .width(100.dp)
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
                        fontSize = 13.sp

                    )
                }

                Button(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(end = 55.dp, bottom = 15.dp)
                        .height(35.dp)
                        .width(100.dp)
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
                        fontSize = 13.sp

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
        val navController = rememberNavController()
        KitchenTools(navController)
    }
}
