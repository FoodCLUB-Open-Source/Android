package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.navigation.SettingsScreen
import android.kotlin.foodclub.utils.composables.SettingsLayout
import android.kotlin.foodclub.viewModels.settings.SettingsEvents
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

val colorGray = Color(android.graphics.Color.parseColor("#D0D0D0"))
val colorRed = Color(android.graphics.Color.parseColor("#C64E0B"))

@Composable
fun SettingsView(
    navController: NavHostController,
    events: SettingsEvents,
    state: SettingsState
) {

    SettingsLayout(
        label = stringResource(id = R.string.settings),
        onBackAction = { navController.navigateUp() }) {
        val screenSizeHeight =
            LocalConfiguration.current.screenHeightDp.dp

        state.user?.let {
            SettingsProfile(
                userName = it.userName,
                userImage = painterResource(id = R.drawable.story_user)
            )
        }

        Spacer(modifier = Modifier.height(screenSizeHeight * 0.1f))

        SettingRow(
            text = stringResource(id = R.string.edit_profile_information),
            iconId = R.drawable.editprofile,
            fontC = Color.Black,
            borderSize = 0,
            borderColor = Color.Gray,
            destination = SettingsScreen.EditProfile.route,
            navController = navController
        )

        SettingRow(
            text = stringResource(id = R.string.privacy_settings),
            iconId = R.drawable.privacysettings,
            fontC = Color.Black,
            borderSize = 0,
            borderColor = Color.Gray,
            destination = SettingsScreen.Privacy.route,
            navController = navController
        )

        Spacer(modifier = Modifier.height(screenSizeHeight * 0.03f))

        Column(
            modifier = Modifier.border(
                width = dimensionResource(id = R.dimen.dim_1),
                colorGray,
                RoundedCornerShape(dimensionResource(id = R.dimen.dim_8))
            )
        ) {
            SettingRow(
                text = stringResource(id = R.string.help_and_support),
                iconId = R.drawable.helpandsupport,
                Color.Black,
                borderSize = 0,
                borderColor = Color.Transparent,
                destination = "SETTINGS_PRIVACY",
                navController = navController
            )

            SettingRow(
                text = stringResource(id = R.string.contact_us),
                iconId = R.drawable.contactus,
                fontC = Color.Black,
                borderSize = 0,
                borderColor = Color.Transparent,
                destination = "SETTINGS_PRIVACY",
                navController = navController
            )

            SettingRow(
                text = stringResource(id = R.string.privacy_policy),
                iconId = R.drawable.privacypolicy,
                fontC = Color.Black,
                borderSize = 0,
                borderColor = Color.Transparent,
                destination = SettingsScreen.PrivacyPolicy.route,
                navController = navController
            )
        }

        Spacer(modifier = Modifier.height(screenSizeHeight * 0.03f))

        SettingRow(
            text = stringResource(id = R.string.log_out),
            iconId = R.drawable.logout,
            fontC = colorRed,
            borderSize = 0,
            borderColor = Color.Black,
            destination = Graph.AUTHENTICATION,
            navController = navController,
            onClick = { events.logout() }
        )
    }
}

@Composable
fun SettingsIcons(
    size: Int,
    icon: Int
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = stringResource(id = R.string.go_back),
        modifier = Modifier.size(size.dp)
    )
}

@Composable
fun SettingsText(
    text: String,
    size: Int,
    weight: FontWeight,
    fontC: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        fontSize = size.sp,
        color = fontC,
        fontFamily = Montserrat,
        fontWeight = weight,
        textAlign = textAlign
    )
}

@Composable
fun SettingsTopBar(
    label: String,
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .background(
                        colorGray,
                        RoundedCornerShape(dimensionResource(id = R.dimen.dim_8))
                    )
                    .size(dimensionResource(id = R.dimen.dim_35)),
                content = {
                    SettingsIcons(
                        size = 20,
                        icon = R.drawable.back_icon
                    )
                }
            )
        }

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_20)))

        Column {
            SettingsText(
                text = label,
                size = 28,
                weight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun SettingsProfile(
    userName: String,
    userImage: Painter
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                contentDescription = stringResource(id = R.string.user_images),
                painter = userImage,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_120))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_100)))
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_15)))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SettingsText(
                text = userName,
                size = 24,
                weight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun SettingRow(
    text: String,
    iconId: Int,
    fontC: Color = Color.Black,
    borderSize: Int = 1,
    borderColor: Color = colorGray,
    destination: String,
    navController: NavController,
    onClick: () -> Unit = {}
) {
    val rowSize = dimensionResource(id = R.dimen.dim_65)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(rowSize),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                onClick()
                navController.navigate(destination)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_8)),
            modifier = Modifier
                .height(rowSize)
                .border(
                    width = borderSize.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_8))
                )

        ) {
            SettingsIcons(size = 24, icon = iconId)

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_16)))

            SettingsText(text = text, size = 14, weight = FontWeight.Normal, fontC = fontC)

            Spacer(modifier = Modifier.weight(1f))

            SettingsIcons(size = 24, icon = R.drawable.forwardarrow)
        }
    }
}
