package android.kotlin.foodclub.views.home

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.lang.Math.ceil

@Composable
fun GalleryPicker(itemsPerRow: Int = 3)
{
    //Toggles between Image and Video options
    val (selectedImageOption, OnOptionSelected) = remember {
        mutableStateOf(true)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
        Text(fontSize = 20.sp, text="Gallery", fontFamily = montserratFamily, fontWeight = FontWeight.Bold, modifier= Modifier.padding(5.dp))
        Row(modifier = Modifier.fillMaxWidth()){

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
                    )
            )
            {
                Text(text="Images", fontSize = 13.sp, fontFamily = montserratFamily, fontWeight = FontWeight.Bold)
            }

            Button(onClick = {
                if (selectedImageOption)
                {
                    OnOptionSelected(false)
                }
            }, shape= RectangleShape,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .then(
                        if (!selectedImageOption) {
                            Modifier.background(Color.Transparent)
                        } else {
                            Modifier.background(Color.DarkGray)
                        }

                    )
            )
            {
                Text(text="Video", fontSize = 13.sp, fontFamily = montserratFamily, fontWeight = FontWeight.Bold)
            }
        }

        if (selectedImageOption)
        {
            GalleryImageTab(images = arrayListOf(), itemsPerRow = itemsPerRow)
        }
        else
        {
            GalleryVideoTab()
        }

    }
}

@Composable
fun GalleryImageTab(images: List<Image>, itemsPerRow: Int = 3)
{
    var imageRows: MutableList<MutableList<Image>> = arrayListOf()
    val imageRow: MutableList<Image> = arrayListOf()
    var count: Int = 0

    for (image in images)
    {
        if (count == itemsPerRow)
        {
            count = 0
            imageRows.add(imageRow)
            imageRow.clear()
            continue
        }
        count += 1
        imageRow.add(image)
    }

    if (imageRow.isNotEmpty())
    {
        imageRows.add(imageRow)
    }


    //Displays items in a grid format with a certain number of items per row
    LazyColumn(modifier= Modifier
        .padding(5.dp)
        .fillMaxWidth()) {
        items(items = imageRows)
        {
                imageLine ->
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                for (image in imageLine)
                {
                    ImageItem(image)
                }
            }
        }
    }
}

@Composable
fun ImageItem(image: Image)
{
    //To be altered with intrinsic measurements
    Card(
        modifier = Modifier
            .height(32.dp)
            .fillMaxWidth(0.32f)
            .padding(start = 5.dp, top = 5.dp)
            .background(Color.Red)
            .clickable {

            }
    ) {
        Image(
            painter = painterResource(id = 0),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
                .padding(2.dp)
        )
    }

}

@Composable
fun GalleryVideoTab()
{

}

@Composable
fun VideoItem()
{

}