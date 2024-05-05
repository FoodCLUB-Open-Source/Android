package live.foodclub.views.home.home.foodSNAPS

import live.foodclub.R
import live.foodclub.domain.enums.Reactions
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource

@Composable
fun SnapReactionsView(
    modifier: Modifier,
    reactions: Array<Reactions>,
    painter: Painter,
    selectReaction: (Reactions) -> Unit,
    reactionsClickable: Boolean
) {

    var clickedItem by remember {
        mutableStateOf(Reactions.ALL)
    }
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(dimensionResource(id = R.dimen.snap_reactions_height))
            .alpha(1f)

    ) {
        Image(painter = painter, contentDescription =null,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.snap_reactions_inner_height))
                .blur(
                    radiusX = dimensionResource(id = R.dimen.snap_reactions_blur_radius),
                    radiusY = dimensionResource(id = R.dimen.snap_reactions_blur_radius),
                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(dimensionResource(id = R.dimen.snap_reactions_blur_edge_radius)))
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
                            painter = painterResource(id =reaction.drawable),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.snap_reactions_reaction_height))
                                .padding(dimensionResource(id = R.dimen.dim_5))
                                .clickable {
                                    if (reactionsClickable) {
                                        clickedItem = reaction
                                        selectReaction(reaction)
                                    }
                                }
                        )
                    }
                    else{
                        Column(
                            modifier= Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id =reaction.drawable),
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .height(dimensionResource(id = R.dimen.snap_reactions_reaction_height))
                                    .clickable {
                                        if (reactionsClickable) {
                                            clickedItem = Reactions.ALL
                                        }
                                    }
                            )
                            Image(
                                painter = painterResource(id = R.drawable.baseline_circle_24),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.dim_8)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}