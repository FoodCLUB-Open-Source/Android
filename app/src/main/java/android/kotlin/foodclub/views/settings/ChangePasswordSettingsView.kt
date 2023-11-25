package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.textFieldCustomColors
import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.utils.composables.CustomPasswordTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

        var filledOldPassword by remember { mutableStateOf(false) }
        var filledNewPassword by remember { mutableStateOf(false) }

        val textFieldColors = textFieldCustomColors(textColor = Color.Black)
        val errorTextFieldColors = textFieldCustomColors(
            textColor = Color.Black,
            focusedIndicatorColor = Color.Red,
            unfocusedIndicatorColor = Color.Red
        )

        CustomPasswordTextField(
            placeholder = "",
            label = stringResource(id = R.string.password),
            strengthValidation = false,
            onCorrectnessStateChange = { filledOldPassword = !filledOldPassword },
            onValueChange = { oldPassword = it },
            textFieldColors = textFieldColors,
            errorTextFieldColors = errorTextFieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))

        CustomPasswordTextField(
            placeholder = "",
            label = stringResource(id = R.string.new_password),
            strengthValidation = true,
            onCorrectnessStateChange = { filledNewPassword = !filledNewPassword },
            onValueChange = { newPassword = it },
            textFieldColors = textFieldColors,
            errorTextFieldColors = errorTextFieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = error ?: "",
            fontSize = 11.sp,
            color = Color.Red
        )

        ConfirmButton(
            enabled = filledOldPassword && filledNewPassword,
            text = stringResource(id = R.string.save)
        ) {
            sendData(oldPassword, newPassword)
        }
    }
}