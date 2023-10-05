package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.ui.theme.textFieldCustomColors
import android.kotlin.foodclub.utils.composables.CustomPasswordTextField
import android.kotlin.foodclub.utils.composables.SettingsLayout
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChangePasswordSettings(error: String?, onBackAction: () -> Unit,
                           sendData: (oldPassword: String, newPassword: String) -> Unit) {

    SettingsLayout(label = "Password", onBackAction = { onBackAction() }) {
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
            label = "Password",
            strengthValidation = false,
            onCorrectnessStateChange = { filledOldPassword = !filledOldPassword },
            onValueChange = { oldPassword = it },
            textFieldColors = textFieldColors,
            errorTextFieldColors = errorTextFieldColors)
        Spacer(modifier = Modifier.height(12.dp))

        CustomPasswordTextField(
            placeholder = "",
            label = "New Password",
            strengthValidation = true,
            onCorrectnessStateChange = { filledNewPassword = !filledNewPassword },
            onValueChange = { newPassword = it },
            textFieldColors = textFieldColors,
            errorTextFieldColors = errorTextFieldColors)
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = error ?: "",
            fontSize = 11.sp,
            color = Color.Red
        )

        Button(
            onClick = { sendData(oldPassword, newPassword) },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.height(56.dp).clip(RoundedCornerShape(10.dp)).fillMaxWidth(),
            enabled = filledOldPassword && filledNewPassword,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7EC60B),
                disabledContainerColor = Color(0xFFC9C9C9),
                disabledContentColor = Color.White,
                contentColor = Color.White
            )
        ) {
            Text(text = "Save", fontSize = 16.sp)
        }
    }
}