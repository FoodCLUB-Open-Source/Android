package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.home.StoryViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun StoriesContainerView(stories: List<Int>, callbackEnableStoryView: (offset: IntOffset) -> Unit, navController: NavHostController) {
    val listOfImages = listOf(R.drawable.story_user, R.drawable.story_user, R.drawable.story_user, R.drawable.story_user, R.drawable.story_user)
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
                            .width(70.dp)
                            .height(70.dp)
                            .clip(CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .width(55.dp)
                            .height(55.dp)
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
                    fontFamily = montserratFamily,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start= 15.dp)
                )
            }
        }

        // Display Stories
        items(stories.size) { story ->
            var xOffset by remember { mutableStateOf(0f) }
            var yOffset by remember { mutableStateOf(0f) }

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
                            .width(70.dp)
                            .height(70.dp)
                            .clip(CircleShape)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.story_user),
                        contentDescription = "Story",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(55.dp)
                            .height(55.dp)
                            .clip(CircleShape)
                            .onGloballyPositioned {
                                xOffset = it.positionInRoot().x + it.size.width / 2
                                yOffset = it.positionInRoot().y + it.size.height / 2
                            }
                            .clickable {
                                val offset = IntOffset(xOffset.toInt(), yOffset.toInt())
                                //pass item index to scroll lazyRow to current viewed story
                                callbackEnableStoryView(offset) }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Julien",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontFamily = montserratFamily,
                    )
            }

        }
    }
}