package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.navigation.SettingsScreen
import android.kotlin.foodclub.utils.composables.LogOutDialog
import android.kotlin.foodclub.utils.composables.SettingsLayout
import android.kotlin.foodclub.viewModels.settings.SettingsEvents
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

val colorGray = Color(android.graphics.Color.parseColor("#D0D0D0"))
val colorRed = Color(android.graphics.Color.parseColor("#C64E0B"))

@Composable
fun SettingsView(
    navController: NavHostController,
    events: SettingsEvents,
    state: SettingsState
) {
    var isDialog by remember {
        mutableStateOf(false)
    }

    var imageUri: Uri? by remember { mutableStateOf(null) }
    LaunchedEffect(key1 = true) {
        state.dataStore?.getImage()?.collect { image ->
            if (image != null) {
                imageUri = Uri.parse(image)
            } else {
                imageUri = null
                Log.e("SettingView", "NULL IMG URI")
            }
        }
    }

    SettingsLayout(
        label = stringResource(id = R.string.settings),
        onBackAction = { navController.navigateUp() }) {
        val screenSizeHeight =
            LocalConfiguration.current.screenHeightDp.dp

        SettingsProfile(
            userName = state.user?.userName,
            userImage = imageUri,
            fullName = state.user?.fullName
        )


        Spacer(modifier = Modifier.height(height = dimensionResource(id = R.dimen.dim_30)))

        Column(
            modifier = Modifier.border(
                width = dimensionResource(id = R.dimen.dim_1),
                colorGray,
                RoundedCornerShape(dimensionResource(id = R.dimen.dim_8))
            )
        ) {

            SettingRow(
                text = stringResource(id = R.string.edit_profile_information),
                iconId = R.drawable.editprofile,
                fontC = Color.Black,
                size = integerResource(id = R.integer.int_16),
                lineHeight = dimensionResource(id = R.dimen.fon_19_5).value.sp,
                fontWeight = FontWeight.Black,
                borderSize = integerResource(id = R.integer.int_0),
                borderColor = Color.Transparent,
                destination = SettingsScreen.EditProfile.route,
                navController = navController
            )

            SettingRow(
                text = stringResource(id = R.string.privacy_settings),
                iconId = R.drawable.privacysettings,
                fontC = Color.Black,
                size = integerResource(id = R.integer.int_16),
                lineHeight = dimensionResource(id = R.dimen.fon_19_5).value.sp,
                fontWeight = FontWeight.Black,
                borderSize = integerResource(id = R.integer.int_0),
                borderColor = Color.Transparent,
                destination = SettingsScreen.Privacy.route,
                navController = navController
            )
        }

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
                fontC = Color.Black,
                size = integerResource(id = R.integer.int_16),
                lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontWeight = FontWeight.Black,
                borderSize = integerResource(id = R.integer.int_0),
                borderColor = Color.Transparent,
                destination = SettingsScreen.HelpAndSupport.route,
                navController = navController
            )

            SettingRow(
                text = stringResource(id = R.string.privacy_policy),
                iconId = R.drawable.privacypolicy,
                fontC = Color.Black,
                size = integerResource(id = R.integer.int_16),
                lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontWeight = FontWeight.Black,
                borderSize = integerResource(id = R.integer.int_0),
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
            fontWeight = FontWeight(integerResource(id = R.integer.int_600)),
            size = integerResource(id = R.integer.int_16),
            lineHeight = dimensionResource(id = R.dimen.fon_19_5).value.sp,
            borderSize = integerResource(id = R.integer.int_1),
            borderColor = colorGray,
            onClick = { isDialog = true }
        )

        if (isDialog) {
            LogOutDialog(onDismissRequest = { isDialog = !isDialog }) {
                events.logout()
                navController.navigate(Graph.AUTHENTICATION)
            }
        }

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
    modifier: Modifier = Modifier,
    text: String,
    size: Int,
    weight: FontWeight,
    fontC: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Center,
    lineHeight: TextUnit? = null,

    ) {
    Text(
        text = text,
        fontSize = size.sp,
        color = fontC,
        fontFamily = Montserrat,
        fontWeight = weight,
        textAlign = textAlign,
        lineHeight = lineHeight ?: TextUnit.Unspecified,
        maxLines = integerResource(id = R.integer.int_2),
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
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
    userName: String?,
    userImage: Uri?,
    fullName: String?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (userImage != null){
                AsyncImage(
                    contentDescription = stringResource(id = R.string.user_images),
                    model = userImage,
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_60)))
                        .height(dimensionResource(id = R.dimen.dim_124))
                        .width(dimensionResource(id = R.dimen.dim_124)),
                    contentScale = ContentScale.Crop
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.story_user),
                    contentDescription = stringResource(id = R.string.user_images),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_120))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_100)))
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_15)))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SettingsText(
                text = fullName ?: "",
                size = 24,
                weight = FontWeight.W600,
                lineHeight = dimensionResource(id = R.dimen.fon_40).value.sp,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dim_20))
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SettingsText(
                text = ("#$userName") ?: "",
                size = 16,
                weight = FontWeight.W600,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dim_20)),
                fontC = colorGray
            )
        }
    }
}



@Composable
fun SettingRow(
    text: String,
    iconId: Int? = null,
    fontC: Color = Color.Black,
    size: Int,
    lineHeight: TextUnit? = null,
    fontWeight: FontWeight = FontWeight.Normal,
    borderSize: Int = 1,
    borderColor: Color = colorGray,
    destination: String? = null,
    navController: NavController? = null,
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
                if (navController != null && destination != null) {
                    navController.navigate(destination)
                }
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
            if (iconId != null) {
                SettingsIcons(size = 24, icon = iconId)
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_16)))

            SettingsText(
                text = text,
                size = size,
                weight = fontWeight,
                fontC = fontC,
                lineHeight = lineHeight ?: TextUnit.Unspecified
            )

            Spacer(modifier = Modifier.weight(1f))

            SettingsIcons(size = 24, icon = R.drawable.forwardarrow)
        }
    }
}
