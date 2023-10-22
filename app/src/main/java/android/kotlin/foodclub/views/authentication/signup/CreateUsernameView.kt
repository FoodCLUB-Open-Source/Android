package android.kotlin.foodclub.views.authentication.signup

import android.kotlin.foodclub.domain.models.auth.SignUpUser
import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.utils.helpers.FieldsValidation
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun UsernameView(onValuesUpdate: () -> Unit, saveData: (String) -> Unit,
                 onBackButtonClick: () -> Unit, userSignUpInformation: State<SignUpUser>,
                 error: String) {
    var username by remember { mutableStateOf(userSignUpInformation.value.username) }
    var initialUsernameCorrectnessState = FieldsValidation.checkUsername(username) == null
    var filledUsername by remember { mutableStateOf(false) }

    AuthLayout(header = "Create a username!", subHeading = "So everyone can find you!",
        errorOccurred = true, message = error,
        onBackButtonClick = {
            saveData(username)
            onBackButtonClick()
        }) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomTextField(initialValue = username,
                placeholder = "Username", keyboardType = KeyboardType.Text,
                onCorrectnessStateChange = { filledUsername = !filledUsername },
                onValueChange = { username = it
                    initialUsernameCorrectnessState = false }, textValidation = true,
                validationMethod = { text -> FieldsValidation.checkUsername(text) }
            )

            ConfirmButton(
                enabled = filledUsername || initialUsernameCorrectnessState,
                text = "Create") {
                saveData(username)
                onValuesUpdate()
            }
        }
    }
}