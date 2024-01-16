package android.kotlin.foodclub.views.home.gallery

import android.Manifest
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.net.Uri
import android.os.Build
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
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
fun GalleryView(
    navController: NavController,
    state: GalleryState,
    stateEncoded: String,
    itemsPerRow: Int = 3
) {

    var galleryState = ""

    if (stateEncoded.contains(GalleryType.STORY.state)) {
        galleryState = GalleryType.STORY.state
    }
    if (stateEncoded.contains(GalleryType.RECIPE.state)) {
        galleryState = GalleryType.RECIPE.state
    }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        )
    )

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    if (state.resourceUri.size < 150) {
        // TODO fill or remove
    }

    val (selectedImageOption, onOptionSelected) = remember {
        mutableStateOf(true)
    }

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = { /* ... */ },
        permissionsNotAvailableContent = { /* ... */ },
    )
    {

        Box(
            modifier = Modifier
                .background(Color(0xFF031622))
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dim_10))
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
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
                        colors = ButtonDefaults.buttonColors(foodClubGreen),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0)),
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dim_50))
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = null,
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.dim_20))
                                .height(dimensionResource(id = R.dimen.dim_20))
                        )
                    }
                    Text(
                        fontSize = dimensionResource(id = R.dimen.fon_30).value.sp,
                        text = stringResource(id = R.string.gallery),
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_25)),
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
                                    onOptionSelected(true)
                                }
                            },
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)),
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.dim_170))
                                .border(
                                    dimensionResource(id = R.dimen.dim_1),
                                    Color.White,
                                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                                )
                                .then(
                                    if (selectedImageOption) {
                                        Modifier.background(
                                            foodClubGreen,
                                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                                        )
                                    } else {
                                        Modifier.background(
                                            Color.Transparent,
                                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                                        )
                                    }
                                ),
                            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent,
                                contentColor = Color.Transparent
                            )
                        )
                        {
                            Text(
                                text = stringResource(id = R.string.images),
                                fontFamily = Montserrat,
                                fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = {
                                if (selectedImageOption) {
                                    onOptionSelected(false)
                                }
                            }, shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)),
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.dim_170))
                                .border(
                                    dimensionResource(id = R.dimen.dim_1),
                                    Color.White,
                                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                                )
                                .then(
                                    if (!selectedImageOption) {
                                        Modifier.background(
                                            foodClubGreen,
                                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                                        )
                                    } else {
                                        Modifier.background(
                                            Color.Transparent,
                                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                                        )
                                    }

                                ),
                            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent,
                                contentColor = Color.Transparent
                            )
                        )
                        {
                            Text(
                                text = stringResource(id = R.string.video),
                                fontFamily = Montserrat,
                                fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    if (selectedImageOption) {
                        GalleryTab(
                            items = state.resourceDrawables,
                            itemsPerRow = itemsPerRow,
                            navController = navController,
                            itemThumbNails = state.imageThumbNails,
                            galleryState = galleryState,
                            itemType = ItemType.IMAGE.type
                        )
                    } else {
                        GalleryTab(
                            items = state.resourceUri,
                            itemsPerRow = itemsPerRow,
                            navController = navController,
                            itemThumbNails = state.videoThumbNails,
                            galleryState = galleryState,
                            itemType = ItemType.VIDEO.type
                        )
                    }

                }

            }
        }
    }

}


@Composable
fun GalleryTab(
    items: List<Uri>,
    itemsPerRow: Int = 3,
    itemThumbNails: List<ImageBitmap>,
    navController: NavController,
    itemType: String,
    galleryState: String
) {

    var itemRows: MutableList<MutableList<Pair<Uri, ImageBitmap>>> = arrayListOf()
    val itemRow: MutableList<Pair<Uri, ImageBitmap>> = arrayListOf()
    var count: Int = 0

    items.zip(itemThumbNails).forEach{
        count += 1
        itemRow.add(it)
        if (count == itemsPerRow) {
            itemRows.add(itemRow.toMutableList())
            count = 0
            itemRow.clear()
        }
    }

    if (itemRow.isNotEmpty()) {
        itemRows.add(itemRow)
    }

    LazyColumn(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dim_5))
            .fillMaxWidth()
    ) {
        items(items = itemRows)
        { itemLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f)

                if (itemType == ItemType.IMAGE.type) {
                    for (item in itemLine) {
                        ImageItem(
                            modifier = ratioModifier,
                            imageID = item.second
                        )
                    }
                } else {
                    for (item in itemLine) {
                        VideoItem(ratioModifier, item.first, navController, galleryState = galleryState, thumbNail = item.second)
                    }
                }
            }
        }
    }
}



@Composable
fun GalleryImageTab(
    images: List<Uri>,
    itemsPerRow: Int = 3,
    imageThumbNails: List<ImageBitmap>
) {
    val imageRows: MutableList<MutableList<ImageBitmap>> = arrayListOf()
    val imageRow: MutableList<ImageBitmap> = arrayListOf()
    var count = 0

    for (image in imageThumbNails) {
        count += 1
        imageRow.add(
           image
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

    LazyColumn(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dim_5))
            .fillMaxWidth()
    ) {
        items(items = imageRows)
        { imageLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f)

                for (image in imageLine) {
                    ImageItem(ratioModifier, image)
                }
            }
        }
    }
}

@Composable
fun ImageItem(modifier: Modifier, imageID: ImageBitmap) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(
                start = dimensionResource(id = R.dimen.dim_5),
                top = dimensionResource(id = R.dimen.dim_5)
            )
            .clickable {
                // TODO add functionality
            }
            .then(modifier)
    ) {
        Image(
            bitmap = imageID,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f, true)
                .padding(dimensionResource(id = R.dimen.dim_2))
        )

    }

}

@Composable
fun GalleryVideoTab(
    videos: List<Uri>,
    itemsPerRow: Int = 3,
    navController: NavController,
    videoThumbNails: List<ImageBitmap>,
    galleryState: String
) {
    var videoRows: MutableList<MutableList<Pair<Uri, ImageBitmap>>> = arrayListOf()
    val videoRow: MutableList<Pair<Uri, ImageBitmap>> = arrayListOf()
    var count: Int = 0

    /*
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
     */

    videos.zip(videoThumbNails).forEach {
        count += 1
        videoRow.add(it)
        if (count == itemsPerRow) {
            videoRows.add(videoRow.toMutableList())
            count = 0
            videoRow.clear()
        }
    }


    if (videoRow.isNotEmpty()) {
        videoRows.add(videoRow)
    }

    LazyColumn(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dim_5))
            .fillMaxWidth()
    ) {
        items(items = videoRows)
        { videoLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                val ratioModifier: Modifier = Modifier.weight(1f)

                for (video in videoLine) {
                    VideoItem(ratioModifier, video.first, navController, galleryState, video.second)
                }
            }
        }
    }
}

/*
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

 */

@Composable
fun VideoItem(
    modifier: Modifier,
    videoID: Uri = ("").toUri(),
    navController: NavController,
    galleryState: String,
    thumbNail: ImageBitmap
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(
                start = dimensionResource(id = R.dimen.dim_5),
                top = dimensionResource(id = R.dimen.dim_5)
            )
            .clickable {

                val uriEncoded = URLEncoder.encode(
                    videoID.toString(),
                    StandardCharsets.UTF_8.toString()
                )
                navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}/${galleryState.encodeUtf8()}")
            }
            .then(modifier)
    ) {
        Image(
            bitmap = thumbNail,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f, true)
                .padding(dimensionResource(id = R.dimen.dim_2))
        )

    }
}

enum class ItemType(val type: String) {
    IMAGE("image"),
    VIDEO("video")
}

enum class GalleryType(val state: String) {
    STORY("story"),
    RECIPE("recipe")
}