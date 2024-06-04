package live.foodclub.presentation.ui.v1.learning

import live.foodclub.R
import live.foodclub.config.ui.FoodClubTheme
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun FutureTopicVoteView() {
    FutureTopicVoteViewUI()
}

@Composable
fun FutureTopicVoteViewUI(modifier: Modifier = Modifier) {


    val riceTextButton = stringArrayResource(id = R.array.dummy_rice_list).toList()
    val montserratFontFamily = FontFamily(
        Font(R.font.montserratbold, FontWeight.Normal)
    )

    val customGreenColor = Color(0xFF80C40C)

    val customGreyColor = Color(0xFF5C5C5C)

    val customLightGreyColor = Color(0xFFD0CCCC)

    Text(
        text = stringResource(id = R.string.vote_for_future_topics),
        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
        fontFamily = montserratFontFamily,
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_110), start =  dimensionResource(id = R.dimen.dim_40))
    )

    Text(
        text = stringResource(id = R.string.choose_from_the_options_below),
        fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
        fontFamily = montserratFontFamily,
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_230), start =  dimensionResource(id = R.dimen.dim_40)),
        color = customGreyColor,
    )

    Box(
        modifier = Modifier
            .offset( dimensionResource(id = R.dimen.dim_34), dimensionResource(id = R.dimen.dim_50))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
            .size( dimensionResource(id = R.dimen.dim_37))
            .background(customLightGreyColor)


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
            .padding(top = dimensionResource(id = R.dimen.dim_240), start = dimensionResource(id = R.dimen.dim_18), end = dimensionResource(id = R.dimen.dim_18))
    ) {
        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( dimensionResource(id = R.dimen.dim_16))
            ) {
                repeat(5) { index ->
                    Button(
                        shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16)),
                        onClick = {
                            //TODO add click functionality
                        },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dim_200))
                            .height(dimensionResource(id = R.dimen.dim_70))
                            .padding(top = dimensionResource(id = R.dimen.dim_15), end = dimensionResource(id = R.dimen.dim_55))
                            .clip(
                                RoundedCornerShape(1)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = riceTextButton.getOrNull(index) ?: stringResource(id = R.string.default_text),
                            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.dim_240), start =dimensionResource(id = R.dimen.dim_1), end =dimensionResource(id = R.dimen.dim_1)),
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(id = R.dimen.dim_205), top = dimensionResource(id = R.dimen.dim_16)),
            ) {
                repeat(times = 5) { index ->
                    Button(
                        shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16)),
                        onClick = {
                            // TODO add click functionality
                        },

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.White
                        ),

                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dim_170))
                            .height(dimensionResource(id = R.dimen.dim_70))
                            .padding(top = dimensionResource(id = R.dimen.dim_15), end = dimensionResource(id = R.dimen.dim_30))
                    ) {
                        Text(
                            text = riceTextButton.getOrNull(index) ?: stringResource(id = R.string.default_text),
                            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                            color = Color.Black
                        )
                    }
                }
            }

        }


    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.dim_770))
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16)),
                onClick = {
                    // TODO add click functionality
                },
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_327))
                    .height(dimensionResource(id = R.dimen.dim_50))
                    .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_16))),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customGreenColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(id = R.string.send_votes),
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,

                    )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun FutureTopicVoteViewPreview() {
    FoodClubTheme {
        rememberNavController()
        FutureTopicVoteView()
    }
}