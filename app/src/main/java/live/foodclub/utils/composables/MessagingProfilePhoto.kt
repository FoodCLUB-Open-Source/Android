package live.foodclub.utils.composables

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import coil.compose.SubcomposeAsyncImage
import live.foodclub.R

@Composable
fun MessagingProfilePhoto(
    photoUrl: String?,
    @DimenRes photoSize: Int = R.dimen.dim_50
) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .size(dimensionResource(id = photoSize))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_32))),
        model = photoUrl,
        error = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )

}