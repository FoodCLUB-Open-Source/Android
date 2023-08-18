package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.Users
import android.kotlin.foodclub.viewmodels.home.FollowerFollowingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController




@Composable
fun FollowerFollowingView(navHostController: NavController) {

    val montserratFamily = FontFamily(

        Font(R.font.montserratregular),

        )

    val ralewayFamily = FontFamily(

        Font(R.font.ralewayextrabold, FontWeight.ExtraBold),

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
                .padding(start = 10.dp, top = 30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {

            Button(shape = RectangleShape,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(36.dp)
                    .width(36.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(230, 230, 230, 255),

                    ),
                contentPadding = PaddingValues(8.dp),

                onClick = {



                }

            ) {

                Image(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "",
                    modifier = Modifier.height(20.dp).width(20.dp)
                    )

            }

            Text(
                text = "My Followers", fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = ralewayFamily,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            LazyColumn ( verticalArrangement = Arrangement.spacedBy(3.dp),){

                items(viewModel.data) { dataItem ->
                    GridItem(dataItem)
                }
            }

        }





    }


}

@Composable
fun GridItem(user:Users) {

    val avenirFamily = FontFamily(

        Font(R.font.avenirblack, FontWeight.Bold),
        Font(R.font.avenirbook, FontWeight.Medium),
        )

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
            .fillMaxHeight().background(Color.White), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(id = R.drawable.profilepicture),
                contentDescription = "profile_picture",
                modifier = Modifier
                    .height(40.dp).width(40.dp).padding(start = 5.dp)
            )

            Column(modifier = Modifier

                .fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(fontFamily = avenirFamily ,
                    text = "${user.fullName}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp))

                Text(fontFamily = avenirFamily ,
                    text = "${user.userName}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 10.dp))
            }
        }


    }
}

//@Preview
//@Composable
//fun FollowerFollowingView1() {
//    FollowerFollowingView(navHostController = rem)
//}





