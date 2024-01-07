package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay


@Composable
fun LoadingProgressBar(
    text:String,
    route:String,
    navController: NavController) {
    var progress by remember { mutableStateOf(0.1f) }

    LaunchedEffect(Unit) {
        for (i in 0..100) {
            progress = i / 100f
            delay(20)
        }
        navController.navigate(route)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding( dimensionResource(id = R.dimen.dim_16)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dim_150))

        )
        Text(text = text, color = Color.Gray)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.dim_250))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
                .height(dimensionResource(id = R.dimen.dim_8))
                .background(Color.Gray),
            contentAlignment = Alignment.CenterStart
        ) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12))),

                color = foodClubGreen,
                trackColor = Color.Gray
            )
        }
    }

}

