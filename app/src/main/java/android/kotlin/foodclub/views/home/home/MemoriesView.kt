package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.utils.composables.MemoriesItemView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import okio.ByteString.Companion.encodeUtf8

@Composable
fun MemoriesView(
    modifier: Modifier,
    memories: List<MemoriesModel>,
    showStories: Boolean,
    currentMemoriesModel: MemoriesModel,
    navController: NavHostController
) {
    var showStories1 = showStories
    var currentMemoriesModel1 = currentMemoriesModel

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = modifier.size(90.dp))
        Text(
            text = "Memories",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = Montserrat
            )
        )
        Spacer(modifier = modifier.size(12.dp))

        if (memories.isEmpty()) {
            MemoriesItemView(
                modifier = Modifier.clickable {
                    showStories1 = !showStories1
                },
                painter = painterResource(id = R.drawable.nosnapsfortheday),
                date = ""
            )
        } else {
            LazyRow() {
                items(memories) {
                    val painter: Painter = rememberAsyncImagePainter(model = it.stories[0].imageUrl)
                    MemoriesItemView(
                        modifier = Modifier.clickable {
                            showStories1 = !showStories1
                            currentMemoriesModel1 = it
                        },
                        painter = painter,
                        date = it.dateTime
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
        TapToSnapDialog(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp)
                .clickable {
                    navController.navigate("CAMERA_VIEW/${"story".encodeUtf8()}")
                }
        )
    }
}