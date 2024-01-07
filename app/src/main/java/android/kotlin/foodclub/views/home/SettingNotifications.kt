package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.utils.composables.SettingsLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun SettingNotifications(navController: NavController) {
    SettingsLayout(
        label= "Notifications",
        onBackAction = { navController.navigateUp() }) {
            NotificationRow(stringResource(id = R.string.general_notifications), titlePadding =dimensionResource(id = R.dimen.dim_4))
            NotificationRow(stringResource(id = R.string.system_and_service), isSubtitle = true)
            NotificationRow(stringResource(id = R.string.app_updates))
            NotificationRow(stringResource(id = R.string.payment_request))
    }
}

@Composable
fun NotificationRow(
    title: String,
    isSubtitle: Boolean = false,
    titlePadding: Dp = dimensionResource(id = R.dimen.dim_8)
) {
    var isNotificationEnabled by remember {
        mutableStateOf(false)
    }

    val verticalPadding = if (isSubtitle) dimensionResource(id = R.dimen.dim_4) else dimensionResource(id = R.dimen.dim_8)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSubtitle) FontWeight.Bold else FontWeight.Normal
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = titlePadding)
            )

            if (!isSubtitle) {
                Switch(
                    checked = isNotificationEnabled,
                    onCheckedChange = {
                        isNotificationEnabled = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = foodClubGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFFBDBDBD),
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun SettingNotificationsPreview(){
    SettingNotifications(navController = rememberNavController())
}