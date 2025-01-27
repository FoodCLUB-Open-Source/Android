package live.foodclub.presentation.ui.home.profile.composables

import live.foodclub.R
import live.foodclub.presentation.ui.home.profile.ProfileEvents
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun FollowButton(
    isFollowed: Boolean,
    events: ProfileEvents,
    sessionUserId: Long
) {
    val colors = if(isFollowed)
        ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color.Black
        )
    else
        ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7EC60B),
            contentColor = Color.White
        )

    val content = isFollowed(isFollowed)

    val modifier = if(isFollowed) Modifier
        .width(130.dp)
        .height(40.dp)
        .border(1.dp, Color.Black, RoundedCornerShape(40.dp))
        .clip(RoundedCornerShape(40.dp))
    else Modifier
        .width(130.dp)
        .height(40.dp)

    Button(
        onClick = { if(isFollowed) events.unfollowUser(sessionUserId)
        else events.followUser(sessionUserId) },
        shape = RoundedCornerShape(40.dp),
        modifier = modifier,
        colors = colors
    ) {
        Text(text = content)
    }
}
@Composable
fun isFollowed(isFollowed: Boolean): String {
    return if(isFollowed) stringResource(id = R.string.unfollow) else stringResource(id = R.string.follow)
}
