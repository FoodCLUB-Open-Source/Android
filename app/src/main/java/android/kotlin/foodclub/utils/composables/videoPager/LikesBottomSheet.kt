package android.kotlin.foodclub.utils.composables.videoPager

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.home.VideoStats
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikesBottomSheet(
    videoStats: VideoStats,
    bottomSheetState: SheetState,
    userList: List<SimpleUserModel>,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.height(dimensionResource(id = R.dimen.dim_600)),
        sheetState = bottomSheetState,
        containerColor = Color.DarkGray
    ) {
        LikesContent(videoStats, userList = userList)
    }

}

@Composable
private fun LikesContent(videoStats: VideoStats, userList: List<SimpleUserModel>) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.dim_10))
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.dim_25))
            )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Image(
                painter = painterResource(id = R.drawable.liked),
                contentDescription = "",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_10))

            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))
            Text(
                text = videoStats.displayLike,
                modifier = Modifier,

                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.fon_15).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_28).value.sp,
                    fontFamily = FontFamily(Font(R.font.montserratmedium)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                )
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))
            Text(
                text = stringResource(id = R.string.Likes),
                modifier = Modifier,

                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_28).value.sp,
                    fontFamily = FontFamily(Font(R.font.montserratmedium)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                )
            )
        }


    }
    if (userList.isEmpty()) {

        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dim_16))
        )
    } else {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .background(Color.Black),
            content = {
                items(userList) { user ->
                    UserItem(user)
                }
            },
            userScrollEnabled = true
        )
    }

}

@Composable
fun UserItem(user: SimpleUserModel) {
    Row(
        Modifier
            .clickable {
                //TODO Navigation to profiles
            }
            .padding(dimensionResource(id = R.dimen.dim_5))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.profilepicture),
            contentDescription = "",
            Modifier
                .padding(dimensionResource(id = R.dimen.dim_8))
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = user.username,
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    fontFamily = FontFamily(Font(R.font.montserratmedium)),
                    fontWeight = FontWeight(700),
                    color = Color.White,
                )
            )
            Text(
                text = "@${user.username}",
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.fon_15).value.sp,
                    fontFamily = FontFamily(Font(R.font.montserratregular)),
                    fontWeight = FontWeight(400),
                    color = Color.LightGray,
                )
            )
        }
    }
}




