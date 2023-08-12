package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBasketView() {
    Column(
        modifier = Modifier
            .background(color = Color(0xFFF0F0F0))
            .fillMaxSize()
            .padding(start = 15.dp, top = 60.dp, end = 15.dp, bottom = 70.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp),
                //.background(Color.Transparent),
                contentAlignment = Alignment.Center,
            ) {
                // Add content to the Box if needed

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(30.dp))
                    Text("My Basket", fontSize = 30.sp, fontWeight = FontWeight.Bold,color = Color.Black,
                        modifier = Modifier.padding(end = 150.dp)
                    )
                    /*Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        tint = Color.Black, // Customize the icon color
                        modifier = Modifier.size(30.dp) // Customize the icon size
                   */
                    Image(painter = painterResource(id = R.drawable.delete_bin_5_line__2_), contentDescription = "",
                         contentScale = ContentScale.FillHeight, modifier  = Modifier.size(50.dp))

                }


            }
            Divider(
                color = Color(android.graphics.Color.parseColor("#E8E8E8")),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(1.dp, Color(126, 198, 11, 255), shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .width(145.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color(126, 198, 11, 255)
                    ), contentPadding = PaddingValues(15.dp),
                    onClick = {}
                ) {
                    Text(
                        "Add items +", color = Color(126, 198, 11, 255),
                    )
                }
            }
            LazyColumn {
                // TODO: Replace with actual data
                items(10) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray)
                    ){
                        Text ("Item1")
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewMyBasketView() {
     MyBasketView()
}

