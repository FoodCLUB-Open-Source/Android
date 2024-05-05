package live.foodclub.utils.helpers

import live.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProfilePicturePlaceHolder(onClick: () -> Unit)
{
    Image(
        painter = painterResource(R.drawable.profile_picture_change_icon),
        contentDescription = stringResource(id = R.string.profile_picture_edit),
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dim_46))
            .width(dimensionResource(id = R.dimen.dim_46))
            .offset(
                x = (cos(PI / 4) * 62 + 39).dp,
                y = (sin(PI / 4) * 62 + 39).dp
            ).clickable { onClick() }
    )
}