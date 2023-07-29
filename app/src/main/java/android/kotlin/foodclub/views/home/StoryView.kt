package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.home.StoryViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun StoryView(stories: List<Int>) {
    val viewModel: StoryViewModel = viewModel()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Add Story button
        item {
            Column(
            verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(start = 20.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.story_border_white),
                        contentDescription = "Story",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .width(65.dp)
                            .height(65.dp)
                            .clip(CircleShape)
                            .background(Color(android.graphics.Color.parseColor("#979797")))
                            .clickable {
                                // Do something when the box is clicked
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Story",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(35.dp)
                            .height(35.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Add Story",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start= 15.dp)
                )
            }
        }

        // Display Stories
        items(stories.size) { story ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.story_border),
                        contentDescription = "Story",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(CircleShape)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.story_user),
                        contentDescription = "Story",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(65.dp)
                            .height(65.dp)
                            .clip(CircleShape)
                            .clickable {
                                // Do something when the box is clicked
                            }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Julien",
                    color = Color.White,
                    fontSize = 15.sp,
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewInstagramStoryView() {
    MaterialTheme {
        StoryView(
            stories = listOf(
                R.drawable.nav_home_icon,
                R.drawable.nav_home_icon,
                R.drawable.nav_home_icon,
                R.drawable.nav_home_icon
            )
        )
    }
}

