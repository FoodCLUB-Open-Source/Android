package android.kotlin.foodclub.views.v1.learning

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.FoodClubTheme
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun LearningView(
    navController: NavController,
) {
    LearningViewUI()
}


@Composable
fun LearningViewUI() {

    val montserratFontFamily = FontFamily(
        Font(R.font.montserratbold, FontWeight.ExtraLight)
    )

    val customGreenColor = Color(0xFF80C40C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 55.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = stringResource(id = R.string.welcome_to_foodskills),
            fontSize = 22.sp,
            fontFamily = montserratFontFamily,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .width(350.dp)
                .height(100.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .width(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                )

            {
                Spacer(modifier = Modifier
                    .weight(2f)
                    .padding(10.dp)

                )

                Text(
                    text = stringResource(id = R.string.kitchen_tools),
                    fontSize = 18.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp, start = 55.dp, end = 20.dp) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                )

                Text(
                    text = stringResource(id = R.string.course_length),
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp,  start = 41.dp, end = 20.dp)

                )


                Spacer(modifier = Modifier
                    .weight(1f)
                    .padding(1.dp))

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.forty_percent),
                    fontSize = 30.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 1.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.completed),
                    fontSize = 10.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 50.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dummy_duration),
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 28.dp,  start = 203.dp, end = 20.dp)

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(bottom = 25.dp, start = 20.dp)


                )
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .width(350.dp)
                .height(100.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .width(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                )

            {
                Spacer(modifier = Modifier
                    .weight(2f)
                    .padding(10.dp)

                )

                Text(
                    text = stringResource(id = R.string.health),
                    fontSize = 18.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp, start = 5.dp, end = 36.dp) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                )

                Text(
                    text = stringResource(id = R.string.course_length),
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp,  start = 41.dp, end = 20.dp)

                )

                Spacer(modifier = Modifier
                    .weight(1f)
                    .padding(1.dp))

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.zero_percent),
                    fontSize = 30.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 1.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.completed),
                    fontSize = 10.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 50.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dummy_duration),
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 28.dp,  start = 203.dp, end = 20.dp)

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(bottom = 25.dp, start = 20.dp)


                )
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 420.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .width(350.dp)
                .height(100.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .width(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                )

            {
                Spacer(modifier = Modifier
                    .weight(2f)
                    .padding(10.dp)

                )

                Text(
                    text = stringResource(id = R.string.flavours),
                    fontSize = 18.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp, start = 55.dp, end = 71.dp) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                )

                Text(
                    text = stringResource(id = R.string.course_length),
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp,  start = 41.dp, end = 20.dp)

                )


                Spacer(modifier = Modifier
                    .weight(1f)
                    .padding(1.dp))

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.zero_percent),
                    fontSize = 30.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 1.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.completed),
                    fontSize = 10.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 50.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dummy_duration),
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 28.dp,  start = 203.dp, end = 20.dp)

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(bottom = 25.dp, start = 20.dp)


                )
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 310.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .width(350.dp)
                .height(100.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .width(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                )

            {
                Spacer(modifier = Modifier
                    .weight(2f)
                    .padding(10.dp)

                )

                Text(
                    text = stringResource(id = R.string.communities),
                    fontSize = 18.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp, start = 55.dp, end = 19.dp) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                )

                Text(
                    text = stringResource(id = R.string.course_length),
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp,  start = 41.dp, end = 20.dp)

                )


                Spacer(modifier = Modifier
                    .weight(1f)
                    .padding(1.dp))

            }


            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.zero_percent),
                    fontSize = 30.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 1.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.completed),
                    fontSize = 10.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 50.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dummy_duration),
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 28.dp,  start = 203.dp, end = 20.dp)

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(bottom = 25.dp, start = 20.dp)


                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 130.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .width(350.dp)
                    .height(150.dp)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .width(350.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    )

                {
                    Spacer(
                        modifier = Modifier
                            .weight(2f)
                            .padding(10.dp)

                    )

                    Text(
                        text = stringResource(id = R.string.click_to_vote),
                        fontSize = 19.sp,
                        fontFamily = PlusJakartaSans,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            bottom = 5.dp,
                            start = 135.dp,
                            end = 30.dp
                        )

                    )

                    Spacer(modifier = Modifier
                        .weight(1f)
                        .padding(1.dp))

                    Button(
                        shape = RoundedCornerShape(9.dp),

                        onClick = { /* TODO: ON CLICK STUFF */ },
                        modifier = Modifier
                            .padding(
                                bottom = 25.dp, start = 35.dp
                            )
                            .width(120.dp)
                            .height(40.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor  = customGreenColor,
                            contentColor = Color.White
                        )

                    ) {
                        Text(text = stringResource(id = R.string.vote_now))
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(id = R.drawable.temporary_image_placeholder),
                        contentDescription = null,
                        modifier = Modifier
                            .size(185.dp)
                            .padding(end = 65.dp)


                    )

                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LearningViewPreview() {
    FoodClubTheme {
        val navController = rememberNavController()
        LearningView(navController)
    }
}

