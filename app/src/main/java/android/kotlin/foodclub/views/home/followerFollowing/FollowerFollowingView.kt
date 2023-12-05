package android.kotlin.foodclub.views.home.followerFollowing

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Avenir
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.viewModels.home.FollowerFollowingViewModel
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import android.kotlin.foodclub.config.ui.Raleway
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun FollowerView(
    navController: NavController,
    viewModel: FollowerFollowingViewModel,
    state: FollowerFollowingState,
    viewType: String,
    userId: Long
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
    }

    LaunchedEffect(Unit) {
        if(viewType == FollowViewType.FOLLOWERS.type) viewModel.getFollowersList(userId)
        if(viewType == FollowViewType.FOLLOWING.type) viewModel.getFollowingList(userId)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.dim_55))
            .background(Color.White)) {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(start = dimensionResource(id = R.dimen.dim_20)),
                contentAlignment = Alignment.Center,
            ) {
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(dimensionResource(id = R.dimen.dim_1), Color(0xFFB8B8B8), shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                        .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                        .align(Alignment.BottomCenter)
                        .width(dimensionResource(id = R.dimen.dim_40))
                        .height(dimensionResource(id = R.dimen.dim_40)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB8B8B8),
                        contentColor = Color.White
                    ), contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_5)),
                    onClick = { navController.navigateUp() }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = stringResource(id = R.string.go_back),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dim_20))
                            .height(dimensionResource(id = R.dimen.dim_20))
                    )
                }
            }

            Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_16)))

            Text(
                text = state.title,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = Raleway,
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dim_20)),
            )

            Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_16)))

            val userList: List<SimpleUserModel> = when(viewType) {
                FollowViewType.FOLLOWERS.type -> state.followersList
                FollowViewType.FOLLOWING.type -> state.followingList
                else -> listOf()
            }


            LazyColumn( modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_150)) ) {
                items(userList.size) { index ->
                    Follower(
                        navController = navController,
                        userId = userList[index].userId,
                        imageUrl = userList[index].profilePictureUrl ?: "",
                        username = userList[index].username,
                        completeName = userList[index].username + stringResource(id = R.string.no_name_found)
                    )
                }
            }
        }
    }
}

@Composable
fun Follower(
    navController: NavController, userId: Int, imageUrl: String,
    username: String, completeName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dim_75))
            .padding(vertical =dimensionResource(id = R.dimen.dim_4))
            .clickable {
                navController.navigate(
                    BottomBarScreenObject.Profile.route + "?userId=$userId"
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width( dimensionResource(id = R.dimen.dim_16)))

        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dim_50))
                .clip(CircleShape)
                .background(Color.White)
        )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_12)))
            Column {
                Text(
                    text = username,
                    fontSize = dimensionResource(id = R.dimen.fon_15).value.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Avenir
                )
                Text(
                    text = completeName,
                    fontSize = dimensionResource(id = R.dimen.fon_15).value.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Avenir
                )
            }

    }
}

enum class FollowViewType(val type: String) {
    FOLLOWERS("followers"),
    FOLLOWING("following")
}