package live.foodclub.utils.composables

import android.content.res.Configuration
import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.home.VideoUserInteraction
import live.foodclub.views.home.ui.theme.FoodClubTheme
import live.foodclub.views.home.ui.theme.Satoshi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecommendationVideos(
    gridHeight: Dp,
    recommendationVideosCount: Int,
    navController: NavController,
    dataItem: List<VideoModel>?,
    userName: String?,
    brush: Brush = shimmerBrush(),
    isShowVideo: (Long) -> Unit
) {
    // ***** Below is for test visualization *****
    LazyVerticalGrid(
        modifier = Modifier.height(gridHeight),
        columns = GridCells.Fixed(2),
        state = rememberLazyGridState(
            0,
            0
        ),
        userScrollEnabled = true,
        content = {
            items(dataItem ?: listOf()) { videoModel ->
//                Card(
//                    modifier = Modifier
//                        .height(dimensionResource(id = R.dimen.dim_272))
//                        .width(dimensionResource(id = R.dimen.dim_178))
//                        .padding(dimensionResource(id = R.dimen.dim_10)),
//                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color.Gray)
//                    )
//                }

                // ***** Below is for when backend is fixed and we get recommendation videos *****

                val thumbnailPainter = rememberAsyncImagePainter(
                    model = videoModel.thumbnailLink,
                )
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
                                if (thumbnailPainter.state is AsyncImagePainter.State.Loading) brush
                                else SolidColor(Color.Transparent)
                            )
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = thumbnailPainter,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    isShowVideo(videoModel.videoId)
                                },
                            contentScale = ContentScale.FillHeight
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                        ) {

                            Text(
                                text = videoModel.description,
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

                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(dimensionResource(id = R.dimen.dim_10)),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(
                                text = videoModel.videoStats.displayLike,
                                fontFamily = Satoshi,
                                color = Color.White,
                                fontSize = dimensionResource(id = R.dimen.fon_15).value.sp
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun RecommendationVideosPreview() {
    FoodClubTheme {
        RecommendationVideos(
            gridHeight = 272.dp,
            recommendationVideosCount = 2,
            navController = rememberNavController(),
            dataItem = listOf(
                VideoModel(
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
                    thumbnailLink = "asdasda"
                ),
                VideoModel(
                    videoId = 2L,
                    authorDetails = "author2",
                    videoLink = "link2",
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
                    description = "desc2",
                    thumbnailLink = "123124214"
                )
            ),
            userName = "userName",
            isShowVideo = {}
        )
    }
}