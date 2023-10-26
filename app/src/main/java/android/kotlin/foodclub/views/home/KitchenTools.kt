package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.kotlin.foodclub.views.home.ui.theme.FoodClubTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class KitchenTools : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodClubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KitchenTools("Android")
                }
            }
        }
    }
}

@Composable
fun KitchenTools(name: String, modifier: Modifier = Modifier) {

    // LIST FOR CARD TITLES
    val cardTitle = listOf("Kitchen Tools", "Can Opening", "Food Preparation Tools")

    // LIST FOR SUB-TEXT
    val cardSubText = listOf("Course Length - Approx 20 mins", "Course Length - Approx 20 mins", "Course Length - Approx 20 mins")

    // LIST FOR IMAGE
    val imageContents = listOf(R.drawable.temporary_image_placeholder, R.drawable.temporary_image_placeholder, R.drawable.temporary_image_placeholder)

    // LIST FOR LEARN BUTTONS
    val Learnbuttons = listOf("Learn", "Learn", "Learn")

    // LIST FOR QUIZ BUTTONS
    val Quizbuttons = listOf("Quiz", "Quiz", "Quiz")

    // THE FONT WE'LL USE
    val montserratFontFamily = FontFamily(
        Font(R.font.montserratbold, FontWeight.Normal)
    )

    // QUIZ BUTTON GREY COLOUR
    val customGreyColor = Color(0xFFE7E7E7)

    // LEARN BUTTON GREEN COLOUR
    val customGreenColor = Color(0xFF80C40C)


    // KITCHEN TOOL TITLE TEXT
    Text(
        text = "Kitchen Tools",
        fontSize = 20.sp,
        fontFamily = montserratFontFamily,
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


        // HOW MANY ITEMS
        items(3) { index ->
            Spacer(modifier = Modifier.height(16.dp))

            // THE CARD STUFF
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(Color.White)
            ) {

                // INNER CARD CONTENTS

                // TITLE TEXTS SETTINGS
                Text(
                    text = cardTitle.getOrNull(index) ?: "Default Text",
                    fontFamily = montserratFontFamily,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .align(Alignment.Center)
                )

                // IMAGE SETTINGS
                Image(
                    painter = painterResource(id = imageContents.getOrNull(index) ?: R.drawable.temporary_image_placeholder),
                    contentDescription = "",
                    modifier = Modifier
                        .width(330.dp)
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                        .padding(bottom = 105.dp)
                        .align(Alignment.Center)
                )

                // SUB TEXTS SETTINGS
                Text(
                    text = cardSubText.getOrNull(index) ?: "Default Text",
                    fontFamily = montserratFontFamily,
                    fontSize = 11.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 78.dp)
                        .align(Alignment.Center)
                )

                // LEARN MORE BUTTON SETTINGS
                Button(
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier
                        .padding(start = 55.dp, bottom = 15.dp)
                        .height(35.dp)
                        .width(100.dp)
                        .align(Alignment.BottomStart),

                    onClick = {
                        // ON CLICK TO DO
                    },

                    // Button colour(s)
                    colors = ButtonDefaults.buttonColors(
                        containerColor  = customGreenColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = Learnbuttons.getOrNull(index) ?: "",
                        color = Color.White,
                        fontSize = 13.sp

                    )
                }

                // QUIZ BUTTON SETTINGS
                Button(
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier
                        .padding(end = 55.dp, bottom = 15.dp)
                        .height(35.dp)
                        .width(100.dp)
                        .align(Alignment.BottomEnd),

                    onClick = {
                        // ON CLICK TO DO
                    },

                    // Button colour(s)
                    colors = ButtonDefaults.buttonColors(
                        containerColor  = customGreyColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = Quizbuttons.getOrNull(index) ?: "",
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
        KitchenTools("Android")
    }
}
