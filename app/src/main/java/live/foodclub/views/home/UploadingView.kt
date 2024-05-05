package live.foodclub.views.home

import live.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import live.foodclub.config.ui.FoodClubTheme

@Composable
fun UploadingView() {
    UploadingViewUI(progress = 0.5f)
}

@Composable
fun UploadingViewUI(modifier: Modifier = Modifier, progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        val montserratFontFamily = FontFamily(
            Font(R.font.montserratbold, FontWeight.Normal)
        )

        val customGreenColor = Color(0xFF80C40C)

        Image(
            painter = painterResource(id = R.drawable.welcome_logo),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dim_80))
                .offset(y = -dimensionResource(id = R.dimen.dim_100))

        )

        Text(
            text = stringResource(id = R.string.uploading),
            fontSize = dimensionResource(id = R.dimen.fon_22).value.sp,
            fontFamily = montserratFontFamily,
            modifier = Modifier.padding(top =  dimensionResource(id = R.dimen.dim_40), bottom = dimensionResource(id = R.dimen.dim_50))
        )

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .offset(y = dimensionResource(id = R.dimen.dim_60))
                .width(dimensionResource(id = R.dimen.dim_280))
                .height(dimensionResource(id = R.dimen.dim_8))
                .padding(horizontal = dimensionResource(id = R.dimen.dim_16)),
            color = customGreenColor
        )
    }
}



@Preview(showBackground = true)
@Composable
fun UploadingViewPreview() {
    FoodClubTheme {
        rememberNavController()
        UploadingView()
    }
}