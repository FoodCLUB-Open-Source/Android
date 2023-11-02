package android.kotlin.foodclub.views.home

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.viewModels.home.GalleryViewModel
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Size
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import okio.ByteString.Companion.encodeUtf8
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
public fun GalleryView(navController: NavController, stateEncoded:String, itemsPerRow: Int = 3) {
    val viewModel: GalleryViewModel = viewModel()
    //val str = firstImage.toString()

    val context = LocalContext.current

    var state:String = ""

    if (stateEncoded.contains("story"))
    {
        state = "story"
    }
    if (stateEncoded.contains("recipe"))
    {
        state = "recipe"
    }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        )
    )

    val ResourceIds: MutableList<Pair<Uri, String>> = mutableListOf()
    val ResourceDrawables: MutableList<Uri> = mutableListOf<Uri>();
    val ResourceURI: MutableList<Uri> = mutableListOf<Uri>();

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }


    //Try get 150 videos and images each loaded
    val uris = viewModel.getMediaContent(context = context, limitSize = true, 150)
    uris.addAll(viewModel.getVideoMediaContent(context = context, limitSize = true, 150))
    val y = 0;
    for (uri in uris) {
        val type = context.contentResolver.getType(uri)
        val s = type?.let { type.substring(0, it.indexOf("/")) }
        ResourceIds.add(Pair(uri, s) as Pair<Uri, String>)
    }
    val x = 0;

    ResourceIds.forEach()
    { (name, type) ->
        if (type == "image") {
            //val drawable = Drawable.createFromStream(context.contentResolver.openInputStream(name.toUri()), name)
            ResourceDrawables.add(name)
        } else {
            ResourceURI.add(name)
        }
    }

    if (ResourceURI.size < 150) {

    }


    //Toggles between Image and Video options
    val (selectedImageOption, OnOptionSelected) = remember {
        mutableStateOf(true)
    }

    /*
    if (firstImage == "image")
    {
        OnOptionSelected(true)
    }
    else if (firstImage == "video")
    {
        OnOptionSelected(false)
    }

     */

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = { /* ... */ },
        permissionsNotAvailableContent = { /* ... */ },
    )
    {

        Box(modifier = Modifier
            .background(Color(0xFF031622))
            .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF131622))
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF7EC60B)),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(50.dp)
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = null,
                            modifier = Modifier
                                //.aspectRatio(1f)
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }
                    Text(
                        fontSize = 30.sp,
                        text = "Gallery",
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(25.dp),
                        color = Color.White
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(
                            onClick = {
                                if (!selectedImageOption) {
                                    OnOptionSelected(true)
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .width(170.dp)
                                .border(1.dp, Color.White, shape = RoundedCornerShape(20.dp))
                                .then(
                                    if (selectedImageOption) {
                                        Modifier.background(
                                            Color(0xFF7EC60B),
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                    } else {
                                        Modifier.background(
                                            Color.Transparent,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                    }
                                ),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent,
                                contentColor = Color.Transparent
                            )
                        )
                        {
                            Text(
                                text = "Images",
                                fontFamily = Montserrat,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = {
                                if (selectedImageOption) {
                                    OnOptionSelected(false)
                                }
                            }, shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .width(170.dp)
                                .border(1.dp, Color.White, shape = RoundedCornerShape(20.dp))
                                .then(
                                    if (!selectedImageOption) {
                                        Modifier.background(
                                            Color(0xFF7EC60B),
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                    } else {
                                        Modifier.background(
                                            Color.Transparent,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                    }

                                ),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent,
                                contentColor = Color.Transparent
                            )
                        )
                        {
                            Text(
                                text = "Video",
                                fontFamily = Montserrat,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    if (selectedImageOption) {
                        GalleryImageTab(
                            images = ResourceDrawables,
                            itemsPerRow = itemsPerRow,
                            context = context
                        )
                    } else {
                        GalleryVideoTab(
                            videos = ResourceURI,
                            itemsPerRow = itemsPerRow,
                            navController = navController,
                            context = context,
                            state = state,
                        )
                    }

                }

            }
        }
    }

}

@Composable
fun <E> GalleryTab(items: List<E>, itemsPerRow: Int = 3, context: Context,  navController: NavController, itemType: String, state:String) {
    var itemRows: MutableList<MutableList<E>> = arrayListOf()
    val itemRow: MutableList<E> = arrayListOf()
    var count: Int = 0

    for (image in items) {
        count += 1
        itemRow.add(image)
        if (count == itemsPerRow) {
            itemRows.add(itemRow.toMutableList())
            count = 0
            itemRow.clear()
            continue
        }
    }

    if (itemRow.isNotEmpty()) {
        itemRows.add(itemRow)
    }

    //
    //Displays items in a grid format with a certain number of items per row
    LazyColumn(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        items(items = itemRows)
        { itemLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f);

                if (itemType === "image")
                {
                    for (item in itemLine)
                    {
                        ImageItem(modifier = ratioModifier, imageID = (context).contentResolver.loadThumbnail(item.toString().toUri(), Size(480, 480), null).asImageBitmap())
                    }
                }
                else
                {
                    for (item in itemLine) {
                        VideoItem(ratioModifier, item.toString().toUri(), navController, state)
                    }
                }
            }
        }
    }
}

@Composable
fun GalleryImageTab(images: List<Uri>, itemsPerRow: Int = 3, context: Context) {
    var imageRows: MutableList<MutableList<ImageBitmap>> = arrayListOf()
    val imageRow: MutableList<ImageBitmap> = arrayListOf()
    var count: Int = 0

    for (image in images) {
        count += 1
        imageRow.add(
            //Try make more efficient
            //Drawable.createFromStream(context.contentResolver.openInputStream(image), image.toString()) as BitmapDrawable
            (context).contentResolver.loadThumbnail(image, Size(480, 480), null).asImageBitmap()
        )
        if (count == itemsPerRow) {
            imageRows.add(imageRow.toMutableList())
            count = 0
            imageRow.clear()
            continue
        }
    }

    if (imageRow.isNotEmpty()) {
        imageRows.add(imageRow)
    }

    //
    //Displays items in a grid format with a certain number of items per row
    LazyColumn(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        items(items = imageRows)
        { imageLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f);

                for (image in imageLine) {
                    ImageItem(ratioModifier, image)
                }
            }
        }
        //Box(modifier = Modifier.fillMaxWidth().padding(5.dp))
    }
}

@Composable
fun ImageItem(modifier: Modifier, imageID: ImageBitmap) {


    //To be altered with intrinsic measurements
    Card(
        modifier = Modifier
            //.height(32.dp)
            //.fillMaxWidth(0.32f)
            //.weight(1f, true)
            .aspectRatio(1f)
            .padding(start = 5.dp, top = 5.dp)
            //.background(Color.Red)
            .clickable {

            }
            .then(modifier)
    ) {
        Image(
            bitmap = imageID,//.bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f, true)
                .padding(2.dp)
        )

    }

}

@Composable
fun GalleryVideoTab(
    videos: List<Uri>,
    itemsPerRow: Int = 3,
    navController: NavController,
    context: Context,
    state: String
) {
    var videoRows: MutableList<MutableList<Uri>> = arrayListOf()
    val videoRow: MutableList<Uri> = arrayListOf()
    var count: Int = 0

    for (image in videos) {
        count += 1
        videoRow.add(image)
        if (count == itemsPerRow) {
            videoRows.add(videoRow.toMutableList())
            count = 0
            videoRow.clear()
            continue
        }
    }

    if (videoRow.isNotEmpty()) {
        videoRows.add(videoRow)
    }

    //
    //Displays items in a grid format with a certain number of items per row
    LazyColumn(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        items(items = videoRows)
        { videoLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f);

                for (video in videoLine) {
                    VideoItem(ratioModifier, video, navController, state)
                }
            }
        }
    }
}

fun createVideoThumb(context: Context, uri: Uri): Bitmap? {
    try {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, uri)
        return mediaMetadataRetriever.frameAtTime
    } catch (ex: Exception) {
        Toast
            .makeText(context, "Error retrieving bitmap", Toast.LENGTH_SHORT)
            .show()
    }
    return null
}

@Composable
fun VideoItem(modifier: Modifier, videoID: Uri = ("").toUri(), navController: NavController, state:String) {
    val context = LocalContext.current
    val bitmap = createVideoThumb(context = context, videoID)?.asImageBitmap()
    //To be altered with intrinsic measurements
    Card(
        modifier = Modifier
            //.height(32.dp)
            //.fillMaxWidth(0.32f)
            //.weight(1f, true)
            .aspectRatio(1f)
            .padding(start = 5.dp, top = 5.dp)
            //.background(Color.Red)
            .clickable {

                val uriEncoded = URLEncoder.encode(
                    videoID.toString(),
                    StandardCharsets.UTF_8.toString()
                )
                navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}/${state.encodeUtf8()}")
            }
            .then(modifier)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap,//painterResource(id = R.drawable.baseline_close_24),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f, true)
                    .padding(2.dp)
            )
        }
    }
}