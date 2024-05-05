package live.foodclub.utils.composables

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp

@Composable
fun MemoriesItemView(
    modifier: Modifier,
    painter:Painter,
    date:String
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent,Color(0xFF181818)),
        startY = sizeImage.height.toFloat()/5,
        endY = sizeImage.height.toFloat()
    )
    Column(
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier= modifier
                .size(height = dimensionResource(id = R.dimen.dim_118), width = dimensionResource(id = R.dimen.dim_146))
                .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
        ){
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned {
                    sizeImage = it.size
                }
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(gradient)
            )

        }
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.dim_12)))
        Text(
           text=date,
            style = TextStyle(
                fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                fontFamily = Montserrat,
                fontStyle = FontStyle.Normal,
                color = Color.Black
            ),
            modifier=Modifier.alpha(0.5f)
        )
    }
}
