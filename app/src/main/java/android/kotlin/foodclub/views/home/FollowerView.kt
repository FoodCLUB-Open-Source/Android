package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.home.FollowerFollowingViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodclub.viewmodels.home.ProfileViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val avenir = FontFamily(
    Font(R.font.avenirblack, FontWeight.Bold),
    Font(R.font.avenirbook, FontWeight.Medium)
)

val raleway = FontFamily(
    Font(R.font.ralewayextrabold, FontWeight.ExtraBold),
)

@Composable
fun FollowerView(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    val viewModel: FollowerFollowingViewModel = viewModel()


    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
    }
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 55.dp).background(Color.White)
        ) {
            Box(
                modifier = Modifier.background(Color.Transparent).padding(start = 20.dp),
                contentAlignment = Alignment.Center,
            ) {
                Button(                                                                                  //current
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(1.dp, Color(0xFFB8B8B8), shape = RoundedCornerShape(15.dp))
                        .clip(RoundedCornerShape(15.dp))
                        .align(Alignment.BottomCenter)
                        .width(40.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB8B8B8),
                        contentColor = Color.White
                    ), contentPadding = PaddingValues(5.dp),
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = "Back",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "My Followers", fontWeight = FontWeight.ExtraBold,
                fontFamily = raleway,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 20.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))

            var followerList = viewModel.getFollowersList();

            LazyColumn( modifier = Modifier.padding(bottom = 150.dp) ) {
                items(followerList.size) { index ->
                    Follower(
                        index = index,
                        imageRes = R.drawable.story_user,
                        username = "${followerList.get(index).userName} $index",
                        completeName = "${followerList.get(index).fullName} $index"
                    )
                }
            }
        }
    }
}

@Composable
fun Follower(index: Int, imageRes: Int, username: String, completeName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(vertical = 4.dp).clickable {  },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = username, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = avenir)
                Text(text = completeName, fontSize = 15.sp, fontWeight = FontWeight.Medium, fontFamily = avenir)
            }

    }
}
