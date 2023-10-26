package android.kotlin.foodclub.views.authentication.forgotPassword

import android.annotation.SuppressLint
import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.utils.helpers.FieldsValidation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.State
import androidx.compose.ui.text.input.KeyboardType

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ForgotPasswordView(onValuesUpdate: (email: String) -> Unit,
                       onBackButtonClick: () -> Unit, email: String,
                       errorOccurred: State<Boolean>, message: State<String>
) {
    var userEmail by remember { mutableStateOf(email) }

    var initialEmailCorrectnessState = FieldsValidation.checkEmail(userEmail) == null
    var filledEmail by remember { mutableStateOf(false) }

    AuthLayout(header = "Forgot Password",
        subHeading = "Don’t worry! It happens. Please enter the email associated with your account.",
        errorOccurred = errorOccurred.value, message = message.value,
        onBackButtonClick = { onBackButtonClick() }) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomTextField(initialValue = userEmail,
                placeholder = "Email",
                keyboardType = KeyboardType.Email,
                onCorrectnessStateChange = { filledEmail = !filledEmail },
                onValueChange = {
                    userEmail = it
                    initialEmailCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkEmail(text) })

            ConfirmButton(
                enabled = (filledEmail || initialEmailCorrectnessState) || true,
                text = "Send Code"
            ) { onValuesUpdate(userEmail) }
        }
    }
}
