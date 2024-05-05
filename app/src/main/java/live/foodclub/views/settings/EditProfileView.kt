package live.foodclub.views.settings

import live.foodclub.R
import live.foodclub.localdatasource.room.entity.UserDetailsModel
import live.foodclub.utils.composables.customComponents.ConfirmButton
import live.foodclub.utils.composables.customComponents.CustomTextField
import live.foodclub.utils.composables.SettingsLayout
import live.foodclub.viewModels.settings.SettingsEvents
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditProfileSetting(
    navController: NavController,
    user: UserDetailsModel?,
    events: SettingsEvents
) {
    SettingsLayout(
        label = stringResource(id = R.string.edit_profile),
        onBackAction = { navController.navigateUp() }) {
        var fullName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }



        CustomTextField(
            placeholder = "",
            label = stringResource(id = R.string.username),
            iconID = R.drawable.edit_icon,
            initialValue = user?.userName ?: "",
            keyboardType = KeyboardType.Text,
            onValueChange = { username = it })

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        CustomTextField(
            placeholder = "",
            label = stringResource(id = R.string.settings_full_name),
            iconID = R.drawable.edit_icon,
            initialValue = user?.fullName ?: "Test Name",
            keyboardType = KeyboardType.Text,
            onValueChange = { fullName = it })

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        CustomTextField(
            placeholder = "",
            label = stringResource(id = R.string.email),
            iconID = R.drawable.edit_icon,
            initialValue = user?.email ?: "",
            keyboardType = KeyboardType.Text,
            onValueChange = { email = it })


        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_6)))

        SettingsText(
            text = stringResource(id = R.string.edit_profile_warning),
            weight = FontWeight.W400,
            fontC = Color.Black,
            size = 13,
            lineHeight = 15.85.sp,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_32)))

        ConfirmButton(
            enabled = true,
            text = stringResource(id = R.string.update)
        ) {
            val testUser = user!!.copy(
                phoneNumber = "07123931923"
            )
            events.updateUserDetails(testUser.id, testUser)
        }
    }
}