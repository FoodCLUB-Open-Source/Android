package live.foodclub.views.home.profile

import android.content.Context
import live.foodclub.R
import live.foodclub.config.ui.foodClubGreen
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun ContactUsFooter(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Divider(
            thickness = dimensionResource(id = R.dimen.dim_1),
            color = colorResource(id = R.color.divider_color),
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.dim_8)
            )
        )
        OutlinedButton(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_16)),
            onClick = {
                openUrlInCustomTabs(context)
            },
            shape = CircleShape.copy(
                all = CornerSize(dimensionResource(id = R.dimen.dim_8))
            ),
            border = BorderStroke(
                dimensionResource(id = R.dimen.dim_1), color = foodClubGreen,
            ),
        ) {
            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_12)),
                text = stringResource(id = R.string.contact_us),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                color = foodClubGreen
            )
        }
        Text(
            text = stringResource(R.string.help_and_support_fill_out_form),
            textAlign = TextAlign.Center,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
            color = colorResource(id = R.color.help_and_support_fill_out_form_color)
        )
    }
}

private fun openUrlInCustomTabs(context: Context) {
    val intent = CustomTabsIntent.Builder()
        .build()
    intent.launchUrl(context, Uri.parse(context.getString(R.string.help_and_support_page_url)))
}
