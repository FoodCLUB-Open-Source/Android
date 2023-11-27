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
import android.kotlin.foodclub.views.v1.learning.FutureTopicVoteViewUI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Alignment
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
fun UploadingView(navController: NavController) {
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
                .size(80.dp)
                .offset(y = -100.dp)

        )

        Text(
            text = stringResource(id = R.string.uploading),
            fontSize = 22.sp,
            fontFamily = montserratFontFamily,
            modifier = Modifier.padding(top = 40.dp, bottom = 50.dp)
        )

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .offset(y = 60.dp)
                .width(280.dp)
                .height(8.dp)
                .padding(horizontal = 16.dp),
            color = customGreenColor
        )
    }
}



@Preview(showBackground = true)
@Composable
fun UploadingViewPreview() {
    FoodClubTheme {
        val navController = rememberNavController()
        UploadingView(navController)
    }
}