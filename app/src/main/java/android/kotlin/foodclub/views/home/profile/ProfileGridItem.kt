package android.kotlin.foodclub.views.home.profile

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.home.VideoModel
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun GridItem(
    dataItem: VideoModel,
    triggerShowDeleteRecipe: (Long) -> Unit
){
    Card(modifier = Modifier
        .height(272.dp)
        .width(178.dp)
        .padding(10.dp)
        ,shape = RoundedCornerShape(15.dp)
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()){
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
}