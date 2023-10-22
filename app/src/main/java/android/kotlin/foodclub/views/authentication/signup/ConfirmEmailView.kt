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
fun ConfirmEmailView(onValuesUpdate: () -> Unit, saveData: (String) -> Unit,
                     onBackButtonClick: () -> Unit, userSignUpInformation: State<SignUpUser>,
                     repeatedEmailState: State<String>, error: String) {
    val email by remember { mutableStateOf(userSignUpInformation.value.email) }
    var repeatedEmail by remember { mutableStateOf(repeatedEmailState.value) }
    var initialEmailCorrectnessState = email == repeatedEmail
    var filledEmail by remember { mutableStateOf(false) }

    AuthLayout(header = "Confirm email",
        subHeading = "So that you are sure you haven't mistyped it!", errorOccurred = true,
        message = error,
        onBackButtonClick = {
            saveData(repeatedEmail)
            onBackButtonClick()
        }) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomTextField(initialValue = repeatedEmail,
                placeholder = "Email", keyboardType = KeyboardType.Text,
                onCorrectnessStateChange = { filledEmail = !filledEmail },
                onValueChange = { repeatedEmail = it
                    initialEmailCorrectnessState = false }, textValidation = true,
                validationMethod = { text -> FieldsValidation.confirmEmail(text, email) }
            )

            ConfirmButton(
                enabled = filledEmail || initialEmailCorrectnessState,
                text = "Continue") {
                saveData(repeatedEmail)
                onValuesUpdate()
            }
        }
    }
}