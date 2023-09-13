package android.kotlin.foodclub.utils.composables.engine.trim

//import android.content.Context
//import android.kotlin.foodclub.R
//import android.net.Uri
//import android.os.Bundle
//import android.os.Environment
//import android.widget.FrameLayout
//import android.widget.Toast
//import android.widget.VideoView
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.*
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.view.isVisible
//import com.video.trimmer.interfaces.OnCropVideoListener
//import com.video.trimmer.interfaces.OnTrimVideoListener
//import com.video.trimmer.utils.TrimVideoUtils
//import com.video.trimmer.utils.TrimVideoUtils.startTrim
////import com.video.trimmer.view.VideoTrimmerView
//import com.video.trimmer.view.*
//import kotlinx.coroutines.launch
//import java.io.File
//
//class VideoTrim(private val context: Context) : ComponentActivity(), OnTrimVideoListener {
//
//    // PICK VIDEO
//    private val pickVideo = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        uri?.let {
//            onTrimStarted(uri)
//        }
//    }
//
//    // VAR
//    private var trimmerView: VideoTrimmer? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            VideoTrimScreen()
//        }
//    }
//
//    override fun onTrimStarted() {
//        // FROM GITHUB LIBRARY STUFF
//        val trimmerInstance = VideoTrimmerView(context).apply {
//            setOnTrimVideoListener(this@VideoTrim)
//            setVideoURI(uri) // Assuming uri is defined elsewhere
//            setVideoInformationVisibility(true)
//            setMaxDuration(10)
//            setMinDuration(2)
//            setDestinationPath(
//                Environment.getExternalStorageDirectory()
//                    .toString() + File.separator + "temp" + File.separator + "Videos" + File.separator
//            )
//        }
//
//        trimmerView = trimmerInstance
//    }
//
//    // GETTING THE RESULTS
//    override fun getResult(uri: Uri) {
//        val videoView = findViewById<VideoView>(R.id.videoView) // TO DO
//        videoView.setVideoURI(uri)
//        videoView.start()
//    }
//
//    // WHEN USER CANCELS ACTION
//    override fun cancelAction() {
//        Toast.makeText(this, "Edit Cancelled", Toast.LENGTH_SHORT).show()
//    }
//
//    // ERROR MESSAGE
//    override fun onError(message: String) {
//        AlertDialog.Builder(this)
//            .setTitle("Error")
//            .setMessage(message)
//            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//            .show()
//    }
//
//    @Composable
//    fun VideoTrimScreen() {
//        val openVideoPicker: () -> Unit = {
//            pickVideo.launch("video/*")
//        }
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            // BUTTON ON CLICK
//            Button(
//                onClick = openVideoPicker,
//                modifier = Modifier.padding(16.dp)
//            ) {
//                // SELECT VIDEO
//                Text("Select Video")
//            }
//
//            // FACTORY VIEW (ADDING UI)
//            AndroidView(factory = { context ->
//                FrameLayout(context).apply {
//                    val trimmerView = VideoTrimmer(context).apply {  // TO DO
//                        isVisible = false
//                        layoutParams = FrameLayout.LayoutParams(
//                            FrameLayout.LayoutParams.MATCH_PARENT,
//                            FrameLayout.LayoutParams.WRAP_CONTENT
//                        )
//                    }
//
//                    // ADDING VIEW TO UI
//                    addView(trimmerView)
//                }
//            })
//        }
//    }
//}
//
//// https://github.com/tizisdeepan/VideoEditor
