package live.foodclub.presentation.ui.home.home.foodSNAPS

import live.foodclub.R
import live.foodclub.domain.enums.Reactions
import live.foodclub.domain.models.home.VideoModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import live.foodclub.config.ui.Montserrat

@Composable
fun FoodSNAPSPage(
    index: Int,
    storyListData: List<VideoModel>,
    //showMemoriesReel: Boolean,
    selectReaction: (Reactions) -> Unit,
    reactionsClickable: Boolean
) {

    val reactionsVerticalOffset = -260

    Box{
        AsyncImage(
            model = storyListData[index].thumbnailLink,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        //if(!showMemoriesReel) {
            SnapReactionsView(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            placeable.place(IntOffset(0, reactionsVerticalOffset))
                        }
                    }
                    .padding(bottom = dimensionResource(id = R.dimen.dim_30)),
                reactions = Reactions.entries.toTypedArray(),
                painter = rememberAsyncImagePainter(
                    model = storyListData[index].thumbnailLink
                ),
                selectReaction = selectReaction,
                reactionsClickable = reactionsClickable
            )
       // }
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(dimensionResource(id = R.dimen.dim_15))
        ) {
            AuthorDetailsView(
                storyListData,
                index
            )
        }
    }
}

@Composable
fun AuthorDetailsView(
    storyListData: List<VideoModel>,
    index: Int
){
    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_15))
        ) {
            Image(
                painter = painterResource(id = R.drawable.story_user),
                contentDescription = stringResource(id = R.string.profile_image),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_35))
                    .clip(shape = CircleShape)
                    .alpha(0.7f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))
            Text(
                storyListData[index].authorDetails.username,
                color = Color.Black,
                fontFamily = Montserrat,
                fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dim_2))
                    .alpha(0.7f)
            )
        }

        Text(
            text = storyListData[index].createdAt,
            color = Color.Black,
            fontFamily = Montserrat,
            fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.dim_2))
                .alpha(0.7f)
        )
    }
}