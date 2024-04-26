package live.foodclub.views.home.profile

import android.content.res.Configuration
import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.home.VideoUserInteraction
import live.foodclub.utils.composables.shimmerBrush
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun GridItem(
    brush: Brush,
    isInternetConnected: Boolean,
    dataItem: VideoModel,
    triggerShowDeleteRecipe: () -> Unit
) {
    val thumbnailPainter = rememberAsyncImagePainter(dataItem.thumbnailLink)

    Card(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dim_272))
            .width(dimensionResource(id = R.dimen.dim_178))
            .padding(dimensionResource(id = R.dimen.dim_10)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (thumbnailPainter.state is AsyncImagePainter.State.Loading || !isInternetConnected) brush
                    else SolidColor(Color.Transparent)
                )
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Image(painter = painterResource(id = R.drawable.salad_ingredient),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        triggerShowDeleteRecipe()
                    })
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black, Color.Transparent),
                        ), alpha = 0.4f
                    )
            ) {
                Text(

                    text = dataItem.description,
                    fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Montserrat,
                    lineHeight = dimensionResource(id = R.dimen.fon_17).value.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.dim_32)
                        ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun GridItemPreview() {
    GridItem(brush = shimmerBrush(),
        isInternetConnected = true,
        dataItem = VideoModel(
            videoId = 1L,
            authorDetails = "author1",
            videoLink = "link1",
            videoStats = VideoStats(
                like = 0L,
                comment = 0L,
                share = 0L,
                favourite = 0L,
            ),
            currentViewerInteraction = VideoUserInteraction(
                isLiked = false,
                isBookmarked = false
            ),
            description = "desc1",
            thumbnailLink = ""
        ),
        triggerShowDeleteRecipe = {}
        )
}