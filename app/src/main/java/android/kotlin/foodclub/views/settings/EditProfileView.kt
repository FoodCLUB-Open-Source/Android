package android.kotlin.foodclub.views.settings

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//The main function of this EditProfile file. This arranges all components to build the screen
@Composable
fun EditProfileSetting(
    navController: NavController,
    userState: State<UserDetailsModel?>,
    viewModel: SettingsViewModel
    ){
    val user = userState.value

    SettingsLayout(label = "Edit Profile", onBackAction = { navController.navigateUp()}) {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        CustomTextField(placeholder = "Username", keyboardType = KeyboardType.Text, onValueChange ={ username= it })
        Spacer(modifier = Modifier.height(12.dp))
        CustomTextField(placeholder = "Email", keyboardType = KeyboardType.Text, onValueChange ={ email= it })
        Spacer(modifier = Modifier.height(16.dp))
        ConfirmButton(enabled = true, text = "Save") {
            val testUser = user!!.copy(
                phoneNumber = "07123931923"
            )
            viewModel.updateUserDetails(testUser.id, testUser)
        }
    }
}