package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import okio.ByteString.Companion.encodeUtf8

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun SnapStories(
    storyListData: List<VideoModel>,
    navController: NavHostController,
    scrollState: ScrollState,
    snapPagerState: PagerState,
    snapPagerFling: SnapFlingBehavior
) {
    if (storyListData.isEmpty()) {
        Log.d("TAG", "SnapScreen: storyListData is empty")

        TapToSnapDialog(
            modifier = Modifier
                .layoutId(stringResource(id = R.string.tap_to_snap_string))
                .clickable {
                    navController.navigate("CAMERA_VIEW/${"story".encodeUtf8()}")
                }
                .aspectRatio(0.9f, true)
                .background(color = Color.Red)
        )
    } else {
        Log.d("TAG", "SnapScreen: storyListData is not empty")

        SnapStoryView(
            storyListData = storyListData,
            modifier = Modifier
                .scrollable(
                    state = scrollState,
                    reverseDirection = true,
                    orientation = Orientation.Vertical,
                )
                .layoutId(stringResource(id = R.string.snap_story_view))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layoutId(stringResource(id = R.string.stories_view))

        )
        {
            VerticalPager(
                state = snapPagerState,
                flingBehavior = snapPagerFling,
                beyondBoundsPageCount = 1,
                modifier = Modifier,
            ) {
                Box {
                    AsyncImage(
                        model = storyListData[it].thumbnailLink,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    SnapReactionsView(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = dimensionResource(id = R.dimen.dim_150)),
                        reactions = Reactions.entries.toTypedArray(),
                        painter = rememberAsyncImagePainter(
                            model = storyListData[it].thumbnailLink
                        ),
                        selectReaction = {}
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(dimensionResource(id = R.dimen.dim_15))
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth()
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
                                        .clip(CircleShape)
                                        .alpha(0.7f)
                                )
                                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))
                                Text(
                                    storyListData[it].authorDetails,
                                    color = Color.Black,
                                    fontFamily = Montserrat,
                                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                                    modifier = Modifier
                                        .padding(dimensionResource(id = R.dimen.dim_2))
                                        .alpha(0.7f)
                                )
                            }

                            Text(
                                storyListData[it].createdAt,
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
                }
            }
        }
    }
}