package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.utils.composables.SettingsLayout
import android.kotlin.foodclub.viewModels.home.SettingsViewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EditProfileSetting(
    navController: NavController,
    userState: State<UserDetailsModel?>,
    viewModel: SettingsViewModel
) {
    val user = userState.value

    SettingsLayout(
        label = stringResource(id = R.string.edit_profile),
        onBackAction = { navController.navigateUp() }) {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        CustomTextField(
            placeholder = stringResource(id = R.string.username),
            keyboardType = KeyboardType.Text,
            onValueChange = { username = it })

        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            placeholder = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Text,
            onValueChange = { email = it })

        Spacer(modifier = Modifier.height(16.dp))

        ConfirmButton(
            enabled = true,
            text = stringResource(id = R.string.save)
        ) {
            val testUser = user!!.copy(
                phoneNumber = "07123931923"
            )
            viewModel.updateUserDetails(testUser.id, testUser)
        }
    }
}