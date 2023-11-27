package android.kotlin.foodclub.views.authentication.forgotPassword

import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.utils.composables.CustomPasswordTextField
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.utils.helpers.FieldsValidation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ChangePasswordView(
    onValuesUpdate: (password: String, code: String) -> Unit,
    onBackButtonClick: () -> Unit,
    email: String, errorOccurred: State<Boolean>, message: State<String>
){
    var verificationCode by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var initialVerificationCodeCorrectnessState = FieldsValidation.checkEmail(verificationCode) == null
    var initialPasswordCorrectnessState = FieldsValidation.checkPassword(password) == null
    var filledVerificationCode by remember { mutableStateOf(false) }
    var filledPassword by remember { mutableStateOf(false) }

    AuthLayout(
        header = "Change Password",
        subHeading = "We've sent an email with password reset link to $email",
        errorOccurred = errorOccurred.value, message = message.value,
        onBackButtonClick = { onBackButtonClick() }) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

            CustomTextField(initialValue = verificationCode,
                placeholder = "Verification code",
                keyboardType = KeyboardType.NumberPassword,
                onCorrectnessStateChange = { filledVerificationCode = !filledVerificationCode },
                onValueChange = {
                    verificationCode = it
                    initialVerificationCodeCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkEmail(text) })

            CustomPasswordTextField(initialValue = password, placeholder = "Password",
                onCorrectnessStateChange = {
                    filledPassword = !filledPassword
                    initialPasswordCorrectnessState = false
                },
                onValueChange = { password = it })

            ConfirmButton(
                enabled = (filledVerificationCode || initialVerificationCodeCorrectnessState)
                        && (filledPassword || initialPasswordCorrectnessState),
                text = "Change Password"
            ) { onValuesUpdate(verificationCode, password) }
        }
    }
}
