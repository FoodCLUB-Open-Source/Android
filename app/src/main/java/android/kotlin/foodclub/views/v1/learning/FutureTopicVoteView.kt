package android.kotlin.foodclub.views.v1.learning

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.FoodClubTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun FutureTopicVoteView(
    navController: NavController,
) {
    FutureTopicVoteViewUI(".")
}

@Composable
fun FutureTopicVoteViewUI(name: String, modifier: Modifier = Modifier) {

    // LIST FOR RICE CULIVATION BUTTON
    val RiceTextButton = listOf("Rice cultivation", "Rice cultivation", "Rice cultivation", "Rice cultivation", "Rice cultivation")


    // THE FONT WE'LL USE
    val montserratFontFamily = FontFamily(
        Font(R.font.montserratbold, FontWeight.Normal)
    )

    // GREEN
    val customGreenColor = Color(0xFF80C40C)

    // GREY
    val customGreyColor = Color(0xFF5C5C5C)

    // LIGHT GREY COLOUR
    val customLightGreyColor = Color(0xFFD0CCCC)


    // TITLE TEXT
    Text(
        text = "Vote for your future topics",
        fontSize = 16.sp,
        fontFamily = montserratFontFamily,
        modifier = Modifier.padding(top = 110.dp, start = 40.dp)
    )

    // SUB TEXT
    Text(
        text = "Choose from the options below",
        fontSize = 13.sp,
        fontFamily = montserratFontFamily,
        modifier = Modifier.padding(top = 230.dp, start = 40.dp),
        color = customGreyColor,
    )


    // BACK BUTTON BOX
    Box(
        modifier = Modifier
            .offset(34.dp, 50.dp)
            .clip(RoundedCornerShape(12.dp))
            .size(37.dp)
            .background(customLightGreyColor)


    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_left),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }


    // LEFT COLUMN
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 240.dp, start = 18.dp, end = 18.dp)
    ) {
        item {

            // COLUMN 1 (LEFT)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                repeat(5) { index ->
                    Button(
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            // Handle button click here (turns green)
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(70.dp)
                            .padding(top = 15.dp, end = 55.dp)
                            .clip(
                                RoundedCornerShape(1)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            //containerColor = customGreenColor,
                            containerColor = Color.White,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = RiceTextButton.getOrNull(index) ?: "Default Text",
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }


    // RIGHT COLUMN
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 240.dp, start = 1.dp, end = 1.dp),
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 205.dp, top = 16.dp),
            ) {
                repeat(5) { index ->
                    Button(
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            // Handle button click here (turns green)
                        },

                        // Button colour(s)
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.White
                        ),

                        modifier = Modifier
                            .width(170.dp)
                            .height(70.dp)
                            .padding(top = 15.dp, end = 30.dp)
                    ) {
                        Text(text = RiceTextButton.getOrNull(index) ?: "Default Text",
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                }
            }

        }


    }

    // BOTTOM COLUMN (FOR BUTTON)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 770.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    // Handle button click here
                },
                modifier = Modifier
                    .width(327.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customGreenColor,
                    contentColor = Color.White
                )
            ) {
                Text("Send Votes",
                    fontSize = 18.sp,

                    )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun FutureTopicVoteViewPreview() {
    FoodClubTheme {
        val navController = rememberNavController()
        FutureTopicVoteView(navController)
    }
}