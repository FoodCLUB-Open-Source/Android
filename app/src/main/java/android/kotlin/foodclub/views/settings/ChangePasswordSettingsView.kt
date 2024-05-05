package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.customComponents.ConfirmButton
import android.kotlin.foodclub.utils.composables.customComponents.CustomPasswordTextField
import android.kotlin.foodclub.utils.composables.SettingsLayout
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun ChangePasswordSettings(
    error: String?, onBackAction: () -> Unit,
    sendData: (oldPassword: String, newPassword: String) -> Unit
) {

    SettingsLayout(
        label = stringResource(id = R.string.password),
        onBackAction = { onBackAction() }) {
        var oldPassword by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }
        var newPasswordConfirm by remember {
            mutableStateOf("")
        }

        var filledOldPassword by remember { mutableStateOf(false) }
        var filledNewPassword by remember { mutableStateOf(false) }
        var confirmedNewPassword by remember {
            mutableStateOf(false)
        }

        CustomPasswordTextField(
            placeholder = "",
            label = stringResource(id = R.string.password),
            strengthValidation = false,
            onCorrectnessStateChange = { filledOldPassword = !filledOldPassword },
            onValueChange = { oldPassword = it },
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        CustomPasswordTextField(
            placeholder = "",
            label = stringResource(id = R.string.new_password),
            strengthValidation = true,
            onCorrectnessStateChange = { filledNewPassword = !filledNewPassword },
            onValueChange = { newPassword = it },
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

        CustomPasswordTextField(
            placeholder = "",
            label = stringResource(id = R.string.new_password),
            strengthValidation = true,
            onCorrectnessStateChange = { confirmedNewPassword = !confirmedNewPassword },
            onValueChange = { newPasswordConfirm = it },
        )

        Text(
            text = error ?: "",
            fontSize = dimensionResource(id = R.dimen.fon_11).value.sp,
            color = Color.Red,
            fontFamily = Montserrat
        )

        ConfirmButton(
            enabled = (filledOldPassword && filledNewPassword) && (confirmedNewPassword && newPassword == newPasswordConfirm),
            text = stringResource(id = R.string.save)
        ) {
            sendData(oldPassword, newPassword)
        }
    }
}