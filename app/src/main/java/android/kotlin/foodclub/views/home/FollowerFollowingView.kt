package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.Users
import android.kotlin.foodclub.viewmodels.home.FollowerFollowingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController




@Composable
fun FollowerFollowingView() {

    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),

        )


    val viewModel: FollowerFollowingViewModel = viewModel()
    viewModel.getData()

     Column(
        Modifier
            .fillMaxSize()
            .padding(top = 70.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {

            Button(shape = RectangleShape,
                modifier = Modifier
                    .defaultMinSize(minWidth = 0.03.dp, minHeight = 0.03.dp)
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(15.dp))
                    .clip(RoundedCornerShape(15.dp)),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,

                    ),
                contentPadding = PaddingValues(),

                onClick = {

                    viewModel.backButton();

                }

            ) {

                Image(
                    painterResource(id = R.drawable.back_icon),
                    contentDescription = "back_icon",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .padding(0.dp),
                    contentScale = ContentScale.FillHeight,


                    )

            }

            Text(
                text = "My Followers", fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = montserratFamily,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            LazyColumn {
                items(viewModel.data) { dataItem ->
                    GridItem(dataItem)
                }
            }

        }





    }


}

@Composable
fun GridItem(user:Users) {
    Card(modifier = Modifier
        .height(100.dp)
        .padding(10.dp)
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple(bounded = true, color = Color.Black),
            onClick = {
                //   After click
            }

        ), shape = RoundedCornerShape(10.dp)) {



        Row( modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(id = R.drawable.profilepicture),
                contentDescription = "profile_picture",
                modifier = Modifier
                    .height(50.dp).width(50.dp).padding(start = 5.dp)
            )

            Column(modifier = Modifier

                .fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(fontFamily = montserratFamily ,
                    text = "${user.fullName}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 10.dp))

                Text(fontFamily = montserratFamily ,
                    text = "${user.userName}",
                    fontSize = 12.sp,

                    modifier = Modifier.padding(start = 10.dp))
            }
        }


    }
}

@Preview
@Composable
fun FollowerFollowingView1() {
    FollowerFollowingView()
}





