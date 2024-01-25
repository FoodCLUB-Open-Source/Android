package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.snapsTopbar
import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.utils.composables.MemoriesItemView
import android.kotlin.foodclub.views.home.home.HomeState
import android.kotlin.foodclub.views.home.home.SnapReactionsView
import android.kotlin.foodclub.views.home.home.SnapStoryView
import android.kotlin.foodclub.views.home.home.TapToSnapDialog
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import okio.ByteString.Companion.encodeUtf8

@Composable
fun MemoriesView(
    modifier: Modifier,
    storyListEmpty: Boolean,
    state: HomeState,
    onShowMemoriesChanged: (Boolean) -> Unit,
    updateCurrentMemoriesModel: (MemoriesModel) -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_90)))

        Text(
            text = stringResource(id = R.string.memories),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontFamily = Montserrat
            ),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.dim_15))
        )

        if (state.memories.isEmpty()) {
            MemoriesItemView(
                modifier = Modifier
                    .clickable { onShowMemoriesChanged(true) },
                painter = painterResource(id = R.drawable.nosnapsfortheday),
                date = ""
            )
        } else {
            LazyRow(
                modifier = Modifier
            ) {
                items(state.memories) {
                    val painter: Painter =
                        rememberAsyncImagePainter(model = it.stories[0].imageUrl)
                    MemoriesItemView(
                        modifier = Modifier.clickable {
                            onShowMemoriesChanged(true)
                            updateCurrentMemoriesModel(it)
                        },
                        painter = painter,
                        date = it.dateTime
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_12)))
                }
            }
        }

        if (storyListEmpty) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_15)))
        } else {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.dim_15)),
                color = snapsTopbar
            )
        }
    }
}