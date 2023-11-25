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
import androidx.compose.ui.res.stringResource
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
    UploadedViewUI()
}

@Composable
fun UploadedViewUI(modifier: Modifier = Modifier) {

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
        ) {
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

            Text(
                text = stringResource(id = R.string.post),
                fontSize = 20.sp,
                fontFamily = montserratFontFamily,
                modifier = Modifier.offset(130.dp, 56.dp)
            )


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


        }

        Column(
            modifier = modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .offset(160.dp, 250.dp)
            ) {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray.copy(alpha = 0.5f))
                        .size(60.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.done_tick),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                )

                Text(
                    text = stringResource(id = R.string.uploaded),
                    fontSize = 18.sp,
                    color = Color.Gray, // MEANT TO BE WHITE BUT YOU WONT SEE IT, SO CHANGE WHEN THUMBNAIL IS IMPLEMENTED ETC
                    fontFamily = montserratFontFamily,
                    modifier = Modifier
                        .offset(-75.dp, 56.dp)
                        .align(Alignment.Center)
                )
            }

            Row(
                modifier = modifier
                    .offset(0.dp, -55.dp)
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.sharing_thanks),
                    fontSize = 11.sp,
                    fontFamily = montserratFontFamily,
                    modifier = Modifier.offset(105.dp, 56.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.cloud_tick),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(-140.dp, 56.dp)
                        .width(20.dp)
                        .height(20.dp),
                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 170.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {

                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        // TODO Add functionality to button
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
                        Text(
                            text= stringResource(id = R.string.instagram_share),
                            fontSize = 18.sp
                        )
                    }
                }


                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        // TODO Add functionality to button
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
                            modifier = Modifier.size(45.dp)
                        )
                        Spacer(modifier = Modifier.width(35.dp))

                        Text(
                            text = stringResource(id = R.string.tiktok_share),
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