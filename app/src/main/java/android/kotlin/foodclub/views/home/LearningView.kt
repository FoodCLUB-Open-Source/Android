package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.ui.theme.PlusJakartaSans
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

class LearningView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodClubTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    // FONT
    val montserratFontFamily = FontFamily(
        Font(R.font.montserratbold, FontWeight.ExtraLight)
    )



    // GREEN
    val customGreenColor = Color(0xFF80C40C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 55.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        // TITLE TEXT
        Text(
            text = "Welcome to FoodSKILLS",
            fontSize = 22.sp,
            fontFamily = montserratFontFamily,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // SPACER BETWEEN TITLE AND BOX 1
        Spacer(modifier = Modifier.height(10.dp))


        // ********** BOX 1 **********
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
                    .weight(2f) // SHIFTS TEXT TO RIGHT
                    .padding(10.dp)

                )

                // TEXT 1 (BIG)
                Text(
                    text = "Kitchen Tools",
                    fontSize = 18.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp, start = 55.dp, end = 20.dp) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                )

                Text(
                    text = "Course length - Approx",
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp,  start = 41.dp, end = 20.dp)

                )


                Spacer(modifier = Modifier.weight(1f).padding(1.dp)) // INVERTED PADDING

            }

            // *****ANYTHING TO DO WITH ROWS (BOX 1) *****

            // & ROW
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // LEFT SIDE % TEXT
                Text(
                    text = "60%",
                    fontSize = 30.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 1.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            // COMPLETED ROW
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // GREEN COMPLETED TEXT
                Text(
                    text = "Completed",
                    fontSize = 10.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 50.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            // ROW FOR THE MINUTES TEXT
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "20 mins",
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
                    contentDescription = "",
                    modifier = Modifier
                        .size(75.dp)
                        .padding(bottom = 25.dp, start = 20.dp)


                )
            }
        }

    }


    // ******************** BOX 2 ********************




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {


        // SPACER BETWEEN TITLE AND BOX 2
        Spacer(modifier = Modifier.height(10.dp))


        // BOX 2
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
                    .weight(2f) // SHIFTS TEXT TO RIGHT
                    .padding(10.dp)

                )

                // TEXT 1 (BIG)
                Text(
                    text = "Health",
                    fontSize = 18.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp, start = 5.dp, end = 36.dp) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                )

                Text(
                    text = "Course length - Approx",
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp,  start = 41.dp, end = 20.dp)

                )


                Spacer(modifier = Modifier.weight(1f).padding(1.dp)) // INVERTED PADDING

            }

            // *****ANYTHING TO DO WITH ROWS (BOX 3) *****

            // & ROW
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // LEFT SIDE % TEXT
                Text(
                    text = "0%",
                    fontSize = 30.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 1.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            // COMPLETED ROW
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // GREEN COMPLETED TEXT
                Text(
                    text = "Completed",
                    fontSize = 10.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 50.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            // ROW FOR THE MINUTES TEXT
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "20 mins",
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
                    contentDescription = "",
                    modifier = Modifier
                        .size(75.dp)
                        .padding(bottom = 25.dp, start = 20.dp)


                )
            }
        }

    }


    // ******************** BOX 4 ********************

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 420.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        // SPACER BETWEEN TITLE AND BOX 1
        Spacer(modifier = Modifier.height(10.dp))


        // BOX 4
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
                    .weight(2f) // SHIFTS TEXT TO RIGHT
                    .padding(10.dp)

                )

                // TEXT 4 (BIG)
                Text(
                    text = "Flavours",
                    fontSize = 18.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp, start = 55.dp, end = 71.dp) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                )

                Text(
                    text = "Course length - Approx",
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp,  start = 41.dp, end = 20.dp)

                )


                Spacer(modifier = Modifier.weight(1f).padding(1.dp)) // INVERTED PADDING

            }

            // *****ANYTHING TO DO WITH ROWS (BOX 4)*****

            // & ROW
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // LEFT SIDE % TEXT
                Text(
                    text = "0%",
                    fontSize = 30.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 1.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            // COMPLETED ROW
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // GREEN COMPLETED TEXT
                Text(
                    text = "Completed",
                    fontSize = 10.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 50.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            // ROW FOR THE MINUTES TEXT
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "20 mins",
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
                    contentDescription = "",
                    modifier = Modifier
                        .size(75.dp)
                        .padding(bottom = 25.dp, start = 20.dp)


                )
            }
        }

    }

    // ********************BOX 3********************

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 310.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        // SPACER BETWEEN TITLE AND BOX 1
        Spacer(modifier = Modifier.height(10.dp))


        // BOX 3
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
                    .weight(2f) // SHIFTS TEXT TO RIGHT
                    .padding(10.dp)

                )

                // TEXT 3 (BIG)
                Text(
                    text = "Communities",
                    fontSize = 18.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp, start = 55.dp, end = 19.dp) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                )

                Text(
                    text = "Course length - Approx",
                    fontSize = 9.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp,  start = 41.dp, end = 20.dp)

                )


                Spacer(modifier = Modifier.weight(1f).padding(1.dp)) // INVERTED PADDING

            }

            // *****ANYTHING TO DO WITH ROWS*****

            // & ROW
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // LEFT SIDE % TEXT
                Text(
                    text = "0%",
                    fontSize = 30.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 1.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            // COMPLETED ROW
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // GREEN COMPLETED TEXT
                Text(
                    text = "Completed",
                    fontSize = 10.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = 50.dp,  end = 205.dp, bottom = 15.dp)

                )

            }

            // ROW FOR THE MINUTES TEXT
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "20 mins",
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
                    contentDescription = "",
                    modifier = Modifier
                        .size(75.dp)
                        .padding(bottom = 25.dp, start = 20.dp)


                )
            }
        }






        // ********** BOX 5 (THE BIG ONE) **********

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 130.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            // SPACER BETWEEN TITLE AND BOX 1
            Spacer(modifier = Modifier.height(10.dp))


            // BOX 5
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
                            .weight(2f) // SHIFTS TEXT TO RIGHT
                            .padding(10.dp)

                    )

                    // TEXT 1 (BIG)
                    Text(
                        text = "Click here to vote for future topics",
                        fontSize = 19.sp,
                        fontFamily = PlusJakartaSans,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            bottom = 5.dp,
                            start = 135.dp,
                            end = 30.dp
                        ) // SHIFTS TEXT UP. WE NEED SPACE FOR TEXT BELOW

                    )

                    Spacer(modifier = Modifier.weight(1f).padding(1.dp)) // INVERTED PADDING

                    Button(
                        shape = RoundedCornerShape(9.dp),

                        onClick = { /* TO DO: ON CLICK STUFF */ },
                        modifier = Modifier
                            .padding(
                                bottom = 25.dp, start = 35.dp)
                            .width(120.dp)
                            .height(40.dp),

                        // Button colour(s)
                        colors = ButtonDefaults.buttonColors(
                            containerColor  = customGreenColor,
                            contentColor = Color.White
                        )

                    ) {
                        Text(text = "Vote Now")
                    }

                }

                // *****ANYTHING TO DO WITH ROWS*****

                // & ROW
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        // CHANGE THE IMAGE THIS IS A PLACEHOLDER FOR SIZING REFERENCES
                        painterResource(id = R.drawable.temporary_image_placeholder),
                        contentDescription = "",
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
fun GreetingPreview() {
    FoodClubTheme {
        Greeting("Android")
    }
}

