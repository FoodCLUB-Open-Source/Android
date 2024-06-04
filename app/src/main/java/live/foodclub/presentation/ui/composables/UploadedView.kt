package live.foodclub.presentation.ui.composables

import live.foodclub.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import live.foodclub.config.ui.FoodClubTheme

@Composable
fun UploadedView() {
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
                    .offset( dimensionResource(id = R.dimen.dim_34), dimensionResource(id = R.dimen.dim_50))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
                    .size( dimensionResource(id = R.dimen.dim_37))
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
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontFamily = montserratFontFamily,
                modifier = Modifier.offset(dimensionResource(id = R.dimen.dim_130), dimensionResource(id = R.dimen.dim_56))
            )


            Box(
                modifier = Modifier
                    .offset(dimensionResource(id = R.dimen.dim_230), dimensionResource(id = R.dimen.dim_50))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
                    .size( dimensionResource(id = R.dimen.dim_35))
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
                    .offset(dimensionResource(id = R.dimen.dim_160), dimensionResource(id = R.dimen.dim_250))
            ) {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                        .background(Color.Gray.copy(alpha = 0.5f))
                        .size(dimensionResource(id = R.dimen.dim_60))
                )

                Image(
                    painter = painterResource(id = R.drawable.done_tick),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_60))
                )

                Text(
                    text = stringResource(id = R.string.uploaded),
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    color = Color.Gray, // MEANT TO BE WHITE BUT YOU WONT SEE IT, SO CHANGE WHEN THUMBNAIL IS IMPLEMENTED ETC
                    fontFamily = montserratFontFamily,
                    modifier = Modifier
                        .offset(-dimensionResource(id = R.dimen.dim_75), dimensionResource(id = R.dimen.dim_56))
                        .align(Alignment.Center)
                )
            }

            Row(
                modifier = modifier
                    .offset(dimensionResource(id = R.dimen.dim_0), -dimensionResource(id = R.dimen.dim_55))
                    .height(dimensionResource(id = R.dimen.dim_25)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.sharing_thanks),
                    fontSize = dimensionResource(id = R.dimen.fon_11).value.sp,
                    fontFamily = montserratFontFamily,
                    modifier = Modifier.offset(dimensionResource(id = R.dimen.dim_105), dimensionResource(id = R.dimen.dim_56))
                )

                Image(
                    painter = painterResource(id = R.drawable.cloud_tick),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(-dimensionResource(id = R.dimen.dim_140), dimensionResource(id = R.dimen.dim_56))
                        .width(dimensionResource(id = R.dimen.dim_20))
                        .height(dimensionResource(id = R.dimen.dim_20)),
                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = dimensionResource(id = R.dimen.dim_170)),
                verticalArrangement = Arrangement.Bottom,
            ) {

                Button(
                    shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16)),
                    onClick = {
                        // TODO Add functionality to button
                    },
                    modifier = Modifier
                        .offset( dimensionResource(id = R.dimen.dim_32), dimensionResource(id = R.dimen.dim_96))
                        .width(dimensionResource(id = R.dimen.dim_327))
                        .height(dimensionResource(id = R.dimen.dim_50))
                        .border(dimensionResource(id = R.dimen.dim_0pt5), Color.Black, shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16))),
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
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_45))
                        )
                        Spacer(modifier = Modifier.width( dimensionResource(id = R.dimen.dim_15)))
                        Text(
                            text= stringResource(id = R.string.instagram_share),
                            fontSize = dimensionResource(id = R.dimen.fon_18).value.sp
                        )
                    }
                }


                Button(
                    shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16)),
                    onClick = {
                        // TODO Add functionality to button
                    },
                    modifier = Modifier
                        .offset( dimensionResource(id = R.dimen.dim_32), dimensionResource(id = R.dimen.dim_126))
                        .width(dimensionResource(id = R.dimen.dim_327))
                        .height(dimensionResource(id = R.dimen.dim_50))
                        .border(dimensionResource(id = R.dimen.dim_0pt5), Color.Black, shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16))),
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
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_45))
                        )
                        Spacer(modifier = Modifier.width( dimensionResource(id = R.dimen.dim_35)))

                        Text(
                            text = stringResource(id = R.string.tiktok_share),
                            fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                            modifier = Modifier
                                .offset(-dimensionResource(id = R.dimen.dim_25), dimensionResource(id = R.dimen.dim_0))

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
        rememberNavController()
        UploadedView()
    }
}