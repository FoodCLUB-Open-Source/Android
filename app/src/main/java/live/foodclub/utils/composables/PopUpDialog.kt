package live.foodclub.utils.composables

import live.foodclub.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PopUpDialog(
    dialogText: String,
    delay: PopUpDialogDuration,
    dismissDialog: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
            .background(Color.Transparent.copy(alpha = 0.4f)),
    ) {
        Text(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_8)),
            text = dialogText,
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.dim_14).value.sp,
            fontWeight = FontWeight(integerResource(id = R.integer.int_400)),
            lineHeight = dimensionResource(id = R.dimen.dim_17).value.sp
        )
    }

    LaunchedEffect(Unit) {
        delay(delay.value)
        dismissDialog()
    }
}

enum class PopUpDialogDuration(val value: Long) {
    SHORT_DURATION(1000),
    MEDIUM_DURATION(3000),
    LONG_DURATION(5000)
}
