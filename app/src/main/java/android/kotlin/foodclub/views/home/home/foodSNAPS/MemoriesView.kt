package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.snapsTopbar
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.utils.composables.MemoriesItemView
import android.kotlin.foodclub.views.home.home.HomeState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

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