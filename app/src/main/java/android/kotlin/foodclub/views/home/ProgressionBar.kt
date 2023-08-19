package android.kotlin.foodclub.views.home

import android.provider.MediaStore.Video
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// This composable is a temporary one to test whether the progression bar works
@Composable
fun TestFun(){

}

// Function to Create the progression bar
@Composable
fun ProgressionBar(iterations: Int, timeDelay: Long){
    var progress by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = progress, // Progress value between 0 and 1
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {coroutineScope.launch{
            var progressionRate = (1f/iterations)
            for (i in 0..iterations) {
                delay(timeDelay) // Wait for 1 second
                // Increment progress by 10% each time
                progress += (progressionRate)
            }

        } }) {
            Text("Increase Progress")
        }
    }
}

