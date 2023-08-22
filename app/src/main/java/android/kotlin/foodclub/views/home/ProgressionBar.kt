package android.kotlin.foodclub.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Function to Create the progression bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressionBar(durationTime: Long){
    var progress by remember { mutableStateOf(0f) }
    var timeDelay:Long = 10;
    var iterations = durationTime/timeDelay;
    var changeIterations = 0;
    val progressionRate = (1f/iterations)
    val coroutineScope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
//    val calculatedSliderWidth = screenWidth - (desiredMargin * 2)

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment =Alignment.Center,
    ) {

        Slider(
            value = progress,
            onValueChange = {
                progress = it
                changeIterations=(it/progressionRate).toInt()

            },
            modifier = Modifier
//                .fillMaxWidth()
                .width(screenWidth)
                .padding(top=100.dp) ,
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = colorGreen,
                inactiveTrackColor = colorGray,
            ),
            thumb ={

                Box(
                    modifier = Modifier
                        .size(0.dp)
                        .shadow(0.dp, clip = true)
                        .background(colorGreen)
                )
            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    while((iterations+changeIterations)>=0){
                        delay(timeDelay)
                        progress += progressionRate

                    }
                }


            }
        ) {
            Text("Start")
        }

}
}

@Composable
fun ProgressionBar1(durationTime: Long){
    var timeDelay:Long = 10;
    var iterations = durationTime/timeDelay;
    var progress by remember { mutableStateOf(0f) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch{
                    var progressionRate = (1f/iterations)
                    for (i in 0..iterations) {
                        delay(timeDelay)
                        progress += (progressionRate)
                    } } }) {
                Text("Start")
            }

    }


}


//    val context = LocalContext.current
//    val exoPlayer = ExoPlayer.Builder(context).build()
//    val videoFile = File("../../../../assets/recipeVid.mp4")
//
//    exoPlayer.
//        val videoUri = LocalContext.current.resources.getUri(R.raw.your_video_resource)
//
//    val videoPlayerState = rememberVideoPlayerState();
//    val videoProgress by videoPlayerState.videoProgress.collectAsState();

