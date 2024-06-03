package live.foodclub.presentation.ui.v1.learning

import live.foodclub.R
import live.foodclub.config.ui.FoodClubTheme
import live.foodclub.config.ui.PlusJakartaSans
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun LearningView() {
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
            .padding(top = dimensionResource(id = R.dimen.dim_55)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = stringResource(id = R.string.welcome_to_foodskills),
            fontSize = dimensionResource(id = R.dimen.fon_22).value.sp,
            fontFamily = montserratFontFamily,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_10))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15)))
                .width(dimensionResource(id = R.dimen.dim_350))
                .height(dimensionResource(id = R.dimen.dim_100))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_350)),
                horizontalAlignment = Alignment.CenterHorizontally,

                )

            {
                Spacer(modifier = Modifier
                    .weight(2f)
                    .padding(dimensionResource(id = R.dimen.dim_10))

                )

                Text(
                    text = stringResource(id = R.string.kitchen_tools),
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom =dimensionResource(id = R.dimen.dim_5), start = dimensionResource(id = R.dimen.dim_55), end = dimensionResource(id = R.dimen.dim_20))

                )

                Text(
                    text = stringResource(id = R.string.course_length),
                    fontSize = dimensionResource(id = R.dimen.fon_9).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_15),  start = dimensionResource(id = R.dimen.dim_41), end = dimensionResource(id = R.dimen.dim_20))

                )


                Spacer(modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen.dim_1)))

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.forty_percent),
                    fontSize = dimensionResource(id = R.dimen.fon_30).value.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top =dimensionResource(id = R.dimen.dim_1),  end = dimensionResource(id = R.dimen.dim_205), bottom = dimensionResource(id = R.dimen.dim_15))

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.completed),
                    fontSize = dimensionResource(id = R.dimen.fon_10).value.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_50),  end = dimensionResource(id = R.dimen.dim_205), bottom = dimensionResource(id = R.dimen.dim_15))

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dummy_duration),
                    fontSize = dimensionResource(id = R.dimen.fon_9).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_28),  start = dimensionResource(id = R.dimen.dim_203), end = dimensionResource(id = R.dimen.dim_20))

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_1)),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_75))
                        .padding(bottom = dimensionResource(id = R.dimen.dim_25), start = dimensionResource(id = R.dimen.dim_20))


                )
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.dim_200)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15)))
                .width(dimensionResource(id = R.dimen.dim_350))
                .height(dimensionResource(id = R.dimen.dim_100))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_350)),
                horizontalAlignment = Alignment.CenterHorizontally,

                )

            {
                Spacer(modifier = Modifier
                    .weight(2f)
                    .padding(dimensionResource(id = R.dimen.dim_10))

                )

                Text(
                    text = stringResource(id = R.string.health),
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom =dimensionResource(id = R.dimen.dim_5), start =dimensionResource(id = R.dimen.dim_5), end =  dimensionResource(id = R.dimen.dim_36))

                )

                Text(
                    text = stringResource(id = R.string.course_length),
                    fontSize = dimensionResource(id = R.dimen.fon_9).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_15),  start = dimensionResource(id = R.dimen.dim_41), end = dimensionResource(id = R.dimen.dim_20))

                )

                Spacer(modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen.dim_1)))

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.zero_percent),
                    fontSize = dimensionResource(id = R.dimen.fon_30).value.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top =dimensionResource(id = R.dimen.dim_1),  end = dimensionResource(id = R.dimen.dim_205), bottom = dimensionResource(id = R.dimen.dim_15))

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.completed),
                    fontSize = dimensionResource(id = R.dimen.fon_10).value.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_50),  end = dimensionResource(id = R.dimen.dim_205), bottom = dimensionResource(id = R.dimen.dim_15))

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dummy_duration),
                    fontSize = dimensionResource(id = R.dimen.fon_9).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_28),  start = dimensionResource(id = R.dimen.dim_203), end = dimensionResource(id = R.dimen.dim_20))

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_1)),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_75))
                        .padding(bottom = dimensionResource(id = R.dimen.dim_25), start = dimensionResource(id = R.dimen.dim_20))


                )
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.dim_420)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15)))
                .width(dimensionResource(id = R.dimen.dim_350))
                .height(dimensionResource(id = R.dimen.dim_100))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_350)),
                horizontalAlignment = Alignment.CenterHorizontally,

                )

            {
                Spacer(modifier = Modifier
                    .weight(2f)
                    .padding(dimensionResource(id = R.dimen.dim_10))

                )

                Text(
                    text = stringResource(id = R.string.flavours),
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom =dimensionResource(id = R.dimen.dim_5), start = dimensionResource(id = R.dimen.dim_55), end = dimensionResource(id = R.dimen.dim_71))

                )

                Text(
                    text = stringResource(id = R.string.course_length),
                    fontSize = dimensionResource(id = R.dimen.fon_9).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_15),  start = dimensionResource(id = R.dimen.dim_41), end = dimensionResource(id = R.dimen.dim_20))

                )


                Spacer(modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen.dim_1)))

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.zero_percent),
                    fontSize = dimensionResource(id = R.dimen.fon_30).value.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top =dimensionResource(id = R.dimen.dim_1),  end = dimensionResource(id = R.dimen.dim_205), bottom = dimensionResource(id = R.dimen.dim_15))

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.completed),
                    fontSize = dimensionResource(id = R.dimen.fon_10).value.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_50),  end = dimensionResource(id = R.dimen.dim_205), bottom = dimensionResource(id = R.dimen.dim_15))

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dummy_duration),
                    fontSize = dimensionResource(id = R.dimen.fon_9).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_28),  start = dimensionResource(id = R.dimen.dim_203), end = dimensionResource(id = R.dimen.dim_20))

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_1)),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_75))
                        .padding(bottom = dimensionResource(id = R.dimen.dim_25), start = dimensionResource(id = R.dimen.dim_20))


                )
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.dim_310)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15)))
                .width(dimensionResource(id = R.dimen.dim_350))
                .height(dimensionResource(id = R.dimen.dim_100))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_350)),
                horizontalAlignment = Alignment.CenterHorizontally,

                )

            {
                Spacer(modifier = Modifier
                    .weight(2f)
                    .padding(dimensionResource(id = R.dimen.dim_10))

                )

                Text(
                    text = stringResource(id = R.string.communities),
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom =dimensionResource(id = R.dimen.dim_5), start = dimensionResource(id = R.dimen.dim_55), end = dimensionResource(id = R.dimen.dim_19))

                )

                Text(
                    text = stringResource(id = R.string.course_length),
                    fontSize = dimensionResource(id = R.dimen.fon_9).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_15),  start = dimensionResource(id = R.dimen.dim_41), end = dimensionResource(id = R.dimen.dim_20))

                )


                Spacer(modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen.dim_1)))

            }


            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.zero_percent),
                    fontSize = dimensionResource(id = R.dimen.fon_30).value.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top =dimensionResource(id = R.dimen.dim_1),  end = dimensionResource(id = R.dimen.dim_205), bottom = dimensionResource(id = R.dimen.dim_15))

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.completed),
                    fontSize = dimensionResource(id = R.dimen.fon_10).value.sp,
                    fontFamily = montserratFontFamily,
                    color = customGreenColor,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_50),  end = dimensionResource(id = R.dimen.dim_205), bottom = dimensionResource(id = R.dimen.dim_15))

                )

            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_10)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dummy_duration),
                    fontSize = dimensionResource(id = R.dimen.fon_9).value.sp,
                    fontFamily = montserratFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_28),  start = dimensionResource(id = R.dimen.dim_203), end = dimensionResource(id = R.dimen.dim_20))

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dim_1)),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_75))
                        .padding(bottom = dimensionResource(id = R.dimen.dim_25), start = dimensionResource(id = R.dimen.dim_20))


                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimensionResource(id = R.dimen.dim_130)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15), dimensionResource(id = R.dimen.dim_15)))
                    .width(dimensionResource(id = R.dimen.dim_350))
                    .height(dimensionResource(id = R.dimen.dim_150))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_350)),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    )

                {
                    Spacer(
                        modifier = Modifier
                            .weight(2f)
                            .padding(dimensionResource(id = R.dimen.dim_10))

                    )

                    Text(
                        text = stringResource(id = R.string.click_to_vote),
                        fontSize = dimensionResource(id = R.dimen.fon_19).value.sp,
                        fontFamily = PlusJakartaSans,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            bottom =dimensionResource(id = R.dimen.dim_5),
                            start = dimensionResource(id = R.dimen.dim_135),
                            end = dimensionResource(id = R.dimen.dim_30)
                        )

                    )

                    Spacer(modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(id = R.dimen.dim_1)))

                    Button(
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_9)),

                        onClick = { /* TODO: ON CLICK STUFF */ },
                        modifier = Modifier
                            .padding(
                                bottom = dimensionResource(id = R.dimen.dim_25), start =  dimensionResource(id = R.dimen.dim_35)
                            )
                            .width(dimensionResource(id = R.dimen.dim_120))
                            .height(dimensionResource(id = R.dimen.dim_40)),

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
                        .padding(dimensionResource(id = R.dimen.dim_10)),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(id = R.drawable.temporary_image_placeholder),
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_185))
                            .padding(end = dimensionResource(id = R.dimen.dim_65))


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
        rememberNavController()
        LearningView()
    }
}

