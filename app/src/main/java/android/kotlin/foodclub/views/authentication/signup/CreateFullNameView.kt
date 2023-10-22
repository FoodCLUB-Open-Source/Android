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
fun CreateFullNameView(onValuesUpdate: () -> Unit, saveData: (String) -> Unit,
                       onBackButtonClick: () -> Unit, userSignUpInformation: State<SignUpUser>,
                       error: String) {
    var name by remember { mutableStateOf(userSignUpInformation.value.name) }
    var initialNameCorrectnessState = FieldsValidation.checkFullName(name) == null
    var filledName by remember { mutableStateOf(false) }

    AuthLayout(header = "Put your name!", subHeading = "So others know how to call you!",
        errorOccurred = true, message = error,
        onBackButtonClick = {
            saveData(name)
            onBackButtonClick()
        }) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomTextField(initialValue = name,
                placeholder = "Full name", keyboardType = KeyboardType.Text,
                onCorrectnessStateChange = { filledName = !filledName },
                allowSpace = true,
                onValueChange = { name = it
                    initialNameCorrectnessState = false }, textValidation = true,
                validationMethod = { text -> FieldsValidation.checkFullName(text) }
            )

            ConfirmButton(
                enabled = filledName || initialNameCorrectnessState,
                text = "Continue") {
                saveData(name)
                onValuesUpdate()
            }
        }
    }
}