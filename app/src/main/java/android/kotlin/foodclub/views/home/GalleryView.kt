package android.kotlin.foodclub.views.home

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import android.content.Context
import android.graphics.Bitmap
import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.home.GalleryViewModel
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
public fun GalleryView(navController: NavController, firstImage: String,itemsPerRow: Int = 3)
{
    /*
    val context = LocalContext.current
    val previewView: PreviewView = remember { PreviewView(context) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp + 10.dp
    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxWidth().height(screenHeight).clip(RoundedCornerShape(20.dp))
    )
     */


    val viewModel: GalleryViewModel = viewModel()
    //val str = firstImage.toString()


    val ResourceIds : MutableList<Pair<String, String>> = viewModel.ResourceIds.toMutableList()

    ResourceIds.add(Pair(firstImage, "Video"))
    ResourceIds.add(Pair(firstImage, "Video"))
    ResourceIds.add(Pair(firstImage, "Video"))
    ResourceIds.add(Pair(firstImage, "Video"))

    var ResourceDrawables: MutableList<Int> = mutableListOf<Int>();
    var ResourceURI: MutableList<String> = mutableListOf<String>();

    ResourceIds.forEach()
    {
        (name, type) ->

        if (name.contains("mp4"))
        {
            ResourceURI.add(name)
        }

        if (type == "Image")
        {
            ResourceDrawables.add(name.toInt())
        }
        else
        {
            ResourceURI.add(name)
        }
    }


    //Toggles between Image and Video options
    val (selectedImageOption, OnOptionSelected) = remember {
        mutableStateOf(true)
    }

    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth())
    {
        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Button(onClick = {
                navController.navigate("CAMERA_VIEW")
            },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)
            )
            {
                Image(painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24), contentDescription = null)
            }
            Text(fontSize = 30.sp, text="Gallery", fontFamily = montserratFamily, fontWeight = FontWeight.Bold, modifier= Modifier.padding(25.dp))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black)){

                Button(onClick = {
                    if (!selectedImageOption)
                    {
                        OnOptionSelected(true)
                    }
                }, shape= RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .then(
                            if (selectedImageOption) {
                                Modifier.background(Color.Transparent)
                            } else {
                                Modifier.background(Color.DarkGray)
                            }
                        ),
                    contentPadding = PaddingValues(0.dp)
                    , colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )
                )
                {
                    Text(text="Images", fontFamily = montserratFamily, fontSize = 13.sp, fontWeight = FontWeight.Bold, color=Color.Black)
                }

                Button(onClick = {
                    if (selectedImageOption)
                    {
                        OnOptionSelected(false)
                    }
                }, shape= RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (!selectedImageOption) {
                                Modifier.background(Color.Transparent)
                            } else {
                                Modifier.background(Color.DarkGray)
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
                    Text(text="Video", fontFamily = montserratFamily, fontSize = 13.sp, fontWeight = FontWeight.Bold, color=Color.Black)
                }
            }

            if (selectedImageOption)
            {
                GalleryImageTab(images = ResourceDrawables, itemsPerRow = itemsPerRow)
            }
            else
            {
                GalleryVideoTab(videos = ResourceURI, itemsPerRow = itemsPerRow, navController = navController)
            }

        }

    }


}

@Composable
fun <E> GalleryTab(items: List<E>, itemsPerRow: Int = 3, navController: NavController)
{
    var itemRows: MutableList<MutableList<E>> = arrayListOf()
    val itemRow: MutableList<E> = arrayListOf()
    var count: Int = 0

    for (image in items)
    {
        count += 1
        itemRow.add(image)
        if (count == itemsPerRow)
        {
            itemRows.add(itemRow.toMutableList())
            count = 0
            itemRow.clear()
            continue
        }
    }

    if (itemRow.isNotEmpty())
    {
        itemRows.add(itemRow)
    }

    //
    //Displays items in a grid format with a certain number of items per row
    LazyColumn(modifier= Modifier
        .padding(5.dp)
        .fillMaxWidth()) {
        items(items = itemRows)
        {
                itemLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f);

                for (item in itemLine)
                {
                    VideoItem(ratioModifier, item.toString(), navController)
                }
            }
        }
    }
}

@Composable
fun GalleryImageTab(images: List<Int>, itemsPerRow: Int = 3)
{
    var imageRows: MutableList<MutableList<Int>> = arrayListOf()
    val imageRow: MutableList<Int> = arrayListOf()
    var count: Int = 0

    for (image in images)
    {
        count += 1
        imageRow.add(image)
        if (count == itemsPerRow)
        {
            imageRows.add(imageRow.toMutableList())
            count = 0
            imageRow.clear()
            continue
        }
    }

    if (imageRow.isNotEmpty())
    {
        imageRows.add(imageRow)
    }

    //
    //Displays items in a grid format with a certain number of items per row
    LazyColumn(modifier= Modifier
        .padding(5.dp)
        .fillMaxWidth()) {
        items(items = imageRows)
        {
                imageLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f);

                for (image in imageLine)
                {
                    ImageItem(ratioModifier, image)
                }
            }
        }
    }
}

@Composable
fun ImageItem(modifier: Modifier, imageID: Int = R.drawable.baseline_close_24)
{


    //To be altered with intrinsic measurements
    Card(
        modifier = Modifier
            //.height(32.dp)
            //.fillMaxWidth(0.32f)
            //.weight(1f, true)
            .aspectRatio(1f)
            .padding(start = 5.dp, top = 5.dp)
            .background(Color.Red)
            .clickable {

            }
            .then(modifier)
    ) {
        Image(
            painter = painterResource(id = imageID),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f, true)
                .padding(2.dp)
        )
    }

}

@Composable
fun GalleryVideoTab(videos: List<String>, itemsPerRow: Int = 3, navController: NavController)
{
    var videoRows: MutableList<MutableList<String>> = arrayListOf()
    val videoRow: MutableList<String> = arrayListOf()
    var count: Int = 0

    for (image in videos)
    {
        count += 1
        videoRow.add(image)
        if (count == itemsPerRow)
        {
            videoRows.add(videoRow.toMutableList())
            count = 0
            videoRow.clear()
            continue
        }
    }

    if (videoRow.isNotEmpty())
    {
        videoRows.add(videoRow)
    }

    //
    //Displays items in a grid format with a certain number of items per row
    LazyColumn(modifier= Modifier
        .padding(5.dp)
        .fillMaxWidth()) {
        items(items = videoRows)
        {
                videoLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f);

                for (video in videoLine)
                {
                    VideoItem(ratioModifier, video, navController)
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
fun VideoItem(modifier: Modifier, videoID: String = "", navController: NavController)
{
    val context = LocalContext.current
    val bitmap = createVideoThumb(context = context, videoID.toUri())?.asImageBitmap()
    //To be altered with intrinsic measurements
    Card(
        modifier = Modifier
            //.height(32.dp)
            //.fillMaxWidth(0.32f)
            //.weight(1f, true)
            .aspectRatio(1f)
            .padding(start = 5.dp, top = 5.dp)
            .background(Color.Red)
            .clickable {

                val uriEncoded = URLEncoder.encode(
                    videoID.toString(),
                    StandardCharsets.UTF_8.toString()
                )
                navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}")
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