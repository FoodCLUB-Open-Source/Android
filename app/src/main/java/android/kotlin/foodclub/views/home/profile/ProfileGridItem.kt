package android.kotlin.foodclub.views.home.profile

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.home.VideoModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun GridItem(
    brush: Brush,
    isInternetConnected:Boolean,
    dataItem: VideoModel,
    triggerShowDeleteRecipe: (Long) -> Unit
){
    val thumbnailPainter = rememberAsyncImagePainter(dataItem.thumbnailLink)

    Card(modifier = Modifier
        .height(dimensionResource(id = R.dimen.dim_272))
        .width(dimensionResource(id = R.dimen.dim_178))
        .padding(dimensionResource(id = R.dimen.dim_10))
        ,shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
    ) {

    if(isInternetConnected) {
    Box(
        modifier = Modifier
            .background(
                if (thumbnailPainter.state is AsyncImagePainter.State.Loading) brush
                else SolidColor(Color.Transparent)
            )
            .fillMaxWidth()
            .fillMaxHeight()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.salad_ingredient),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    triggerShowDeleteRecipe(dataItem.videoId)
                }
        )
    }
}
        else{Box(
    modifier = Modifier
        .background(brush)
        .fillMaxWidth()
        .fillMaxHeight()
)

        }
    }
}