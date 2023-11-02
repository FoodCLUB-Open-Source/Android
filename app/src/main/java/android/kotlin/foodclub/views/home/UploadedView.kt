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
import android.kotlin.foodclub.views.v1.learning.FutureTopicVoteView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
fun UploadedView(
    navController: NavController,
) {
    UploadedViewUI(".")
}

@Composable
fun UploadedViewUI(name: String, modifier: Modifier = Modifier) {

    // FONT
    val montserratFontFamily = FontFamily(
        Font(R.font.montserratbold, FontWeight.Normal)
    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            //horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // ********** TOP BAR STUFF **********

            // LEFT BACK BUTTON
            Box(
                modifier = Modifier
                    .offset(34.dp, 50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .size(37.dp)
                    .background(Color.Transparent)


            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_left),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // CENTER TEXT
            Text(
                text = "Post",
                fontSize = 20.sp,
                fontFamily = montserratFontFamily,
                modifier = Modifier.offset(130.dp, 56.dp)
            )


            // RIGHT CROSS BUTTON
            Box(
                modifier = Modifier
                    .offset(230.dp, 50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .size(35.dp)
                    .background(Color.Transparent)


            ) {
                Image(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }


        }  // END OF ROW


        // COLUMN FOR IMAGE + 2 BUTTONS


        Column(
            modifier = modifier.fillMaxSize()
        ) {

            // BOX FOR TICK IMAGE
            Box(
                modifier = Modifier
                    .offset(160.dp, 250.dp)
            ) {

                // BACKGROUND BOX
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray.copy(alpha = 0.5f) ) // ALPHA SETS TRANSPARENCY
                        .size(60.dp)
                )

                // TICK IMAGE
                Image(
                    painter = painterResource(id = R.drawable.done_tick),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                )

                // CENTER TEXT
                Text(
                    text = "Successfully Uploaded!",
                    fontSize = 18.sp,
                    color = Color.Gray, // MEANT TO BE WHITE BUT YOU WONT SEE IT, SO CHANGE WHEN THUMBNAIL IS IMPLEMENTED ETC
                    fontFamily = montserratFontFamily,
                    modifier = Modifier
                        .offset(-75.dp, 56.dp)
                        .align(Alignment.Center)
                )
            }


            // ********** TOP STUFF **********

            Row(
                modifier = modifier
                    .offset(0.dp, -55.dp)
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // THANK YOU TEXT
                Text(
                    text = "Thanks For Sharing On FoodCLUB!",
                    fontSize = 11.sp,
                    fontFamily = montserratFontFamily,
                    modifier = Modifier.offset(105.dp, 56.dp)
                )

                // CLOUD TICK IMAGE
                Image(
                    painter = painterResource(id = R.drawable.cloud_tick),
                    contentDescription = "",
                    modifier = Modifier
                        .offset(-140.dp, 56.dp)
                        .width(20.dp)
                        .height(20.dp),
                )
            }

            Column(
                modifier = modifier.fillMaxSize()
                    .padding(bottom = 170.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {


                // ********** BOTTOM STUFF **********


                // INSTAGRAM SHARE BUTTON
                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        // Handle button click here *For the future*
                    },
                    modifier = Modifier
                        .offset(32.dp, 96.dp)
                        .width(327.dp)
                        .height(50.dp)
                        .border(0.5.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.instagram),
                            contentDescription = null,
                            modifier = Modifier.size(45.dp)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text("Share to Instagram", fontSize = 18.sp)
                    }
                }

                // TIKTOK BUTTON
                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        // Handle button click here *For the future*
                    },
                    modifier = Modifier
                        .offset(32.dp, 126.dp)
                        .width(327.dp)
                        .height(50.dp)
                        .border(0.5.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {


                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tiktok),
                            contentDescription = null,
                            modifier = Modifier.size(45.dp) // Adjust te size as needed
                        )
                        Spacer(modifier = Modifier.width(35.dp))

                        Text(
                            text = "Share to TikTok",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .offset(-25.dp, 0.dp)

                        )
                    }
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadedViewPreview() {
    FoodClubTheme {
        val navController = rememberNavController()
        UploadedView(navController)
    }
}