package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.enums.Reactions
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnapReactionsView(
    modifier: Modifier,
    reactions: Array<Reactions>,
    painter: Painter
) {

    var clickedItem by remember {
        mutableStateOf(Reactions.ALL)
    }
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(84.dp)
            .alpha(1f)

    ) {
        Image(painter = painter, contentDescription =null,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .blur(
                    radiusX = 5.dp,
                    radiusY = 5.dp,
                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(32.dp))
                )
                .align(Alignment.BottomCenter)
            ,
            contentScale = ContentScale.FillWidth
        )

        LazyRow(
            modifier= Modifier
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.Bottom,
        ){
            items(reactions){reaction->
                if(reaction != Reactions.ALL){
                    if(clickedItem!=reaction){
                        Image(
                            painter = painterResource(id =reaction.drawable), contentDescription = null, contentScale = ContentScale.FillHeight, modifier = Modifier
                                .height(58.dp)
                                .padding(5.dp)
                                .clickable {
                                   clickedItem = reaction
                                }
                        )
                    }
                    else{
                        Column(
                            modifier= Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id =reaction.drawable), contentDescription = null, contentScale = ContentScale.FillHeight, modifier = Modifier
                                    .height(58.dp)
                                    .clickable {
                                        //
                                        clickedItem = Reactions.ALL
                                    }
                            )
                            Image(painter = painterResource(id = R.drawable.baseline_circle_24), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(8.dp))
                        }
                    }
                }
            }
        }
    }
}