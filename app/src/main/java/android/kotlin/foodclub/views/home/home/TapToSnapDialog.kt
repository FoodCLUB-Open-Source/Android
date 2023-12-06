package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TapToSnapDialog(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(18.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.taptosnapbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black, BlendMode.Overlay),
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    radiusX = 50.dp,
                    radiusY = 50.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
        )

        Image(
            painter = painterResource(id = R.drawable.taptosnaphand),
            contentDescription = stringResource(id = R.string.hand),
            modifier = Modifier.align(Alignment.BottomStart)
        )
        Text(
            text = stringResource(id = R.string.tap_to_snap_subheading),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                lineHeight = 26.sp
            ),
            modifier = Modifier.padding(28.dp)
        )
        Text(
            text = stringResource(id = R.string.tap_to_snap),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp
            ),
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomEnd)
        )
    }
}