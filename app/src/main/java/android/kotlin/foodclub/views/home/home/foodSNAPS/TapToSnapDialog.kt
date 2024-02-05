package android.kotlin.foodclub.views.home.home.foodSNAPS

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
import androidx.compose.ui.res.dimensionResource
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
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_18)))
                .align(Alignment.Center)
                .border(
                    width = dimensionResource(id = R.dimen.dim_1),
                    color = Color.Black,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_18))
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.taptosnapbg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Black, BlendMode.Overlay),
                modifier = Modifier
                    .blur(
                        radiusX = dimensionResource(id = R.dimen.dim_50),
                        radiusY = dimensionResource(id = R.dimen.dim_50),
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
                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    lineHeight = dimensionResource(id = R.dimen.fon_26).value.sp
                ),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_28))
            )
            Text(
                text = stringResource(id = R.string.tap_to_snap),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    lineHeight = dimensionResource(id = R.dimen.fon_24).value.sp
                ),
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dim_32))
                    .align(Alignment.BottomEnd)
            )
        }
    }
}