package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.domain.enums.Reactions
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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

    val state = rememberPagerState {
        reactions.count()
    }
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(64.dp)
            .alpha(1f)

    ) {
        Image(painter = painter, contentDescription =null,
            modifier = Modifier
                .fillMaxWidth()
                .blur(
                    radiusX = 5.dp,
                    radiusY = 5.dp,
                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(32.dp))
                ),
            contentScale = ContentScale.FillWidth
        )

        LazyRow(
            modifier= Modifier.align(Alignment.Center),
        ){
            items(reactions){reaction->
                if(reaction != Reactions.ALL){
                    Image(
                        painter = painterResource(id =reaction.drawable), contentDescription = null, contentScale = ContentScale.FillHeight, modifier = Modifier
                            .fillMaxHeight()
                            .padding(5.dp)

                    )
                }
            }
        }
    }
}