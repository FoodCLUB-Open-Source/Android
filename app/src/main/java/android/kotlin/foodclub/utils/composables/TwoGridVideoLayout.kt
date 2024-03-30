package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.home.VideoModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController

@Composable
fun RecommendationVideos(
    gridHeight: Dp,
    recommandationVideosCount: Int,
    navController: NavController,
    dataItem: List<VideoModel>?,
    userName: String?,
    brush: Brush = shimmerBrush(),
    isShowVideo: (Long) -> Unit
){
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
            items(recommandationVideosCount) {
                Card(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dim_272))
                        .width(dimensionResource(id = R.dimen.dim_178))
                        .padding(dimensionResource(id = R.dimen.dim_10)),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                }

                // ***** Below is for when backend is fixed and we get recommendation videos *****

//                val thumbnailPainter = rememberAsyncImagePainter(dataItem.thumbnailLink)
//                Card(
//                    modifier = Modifier
//                        .height(dimensionResource(id = R.dimen.dim_272))
//                        .width(dimensionResource(id = R.dimen.dim_178))
//                        .padding(dimensionResource(id = R.dimen.dim_10)),
//                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .background(
//                                if (thumbnailPainter.state is AsyncImagePainter.State.Loading) brush
//                                else SolidColor(Color.Transparent)
//                            )
//                            .fillMaxSize()
//                    ) {
//
//                        val thumbnailPainterOrDefault = if (thumbnailPainter != null) {
//                            thumbnailPainter
//                        } else {
//                            painterResource(id = R.color.gray)
//                        }
//
//                        Image(
//                            painter = thumbnailPainterOrDefault,
//                            contentDescription = null,
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .clickable {
//                                    isShowPost(dataItem.videoId)
//                                },
//                            contentScale = ContentScale.FillHeight
//                        )
//
//                        Column(
//                            Modifier
//                                .fillMaxSize()
//                                .padding(dimensionResource(id = R.dimen.dim_10)),
//                            verticalArrangement = Arrangement.Bottom
//                        ) {
//                            Text(
//                                text = dataItem.videoStats.displayLike,
//                                fontFamily = Satoshi,
//                                color = Color.White,
//                                fontSize = dimensionResource(id = R.dimen.fon_15).value.sp
//                            )
//                        }
//                    }
//                }
            }
        }
    )
}