package android.kotlin.foodclub.views.authentication.forgotPassword

import android.kotlin.foodclub.R
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
        header = stringResource(id = R.string.change_password),
        subHeading = stringResource(id = R.string.change_password_subheading, email),
        errorOccurred = errorOccurred.value, message = message.value,
        onBackButtonClick = { onBackButtonClick() }) {
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_4))
        ) {
            CustomTextField(
                initialValue = verificationCode,
                placeholder = stringResource(id = R.string.verification_code),
                keyboardType = KeyboardType.NumberPassword,
                onCorrectnessStateChange = { filledVerificationCode = !filledVerificationCode },
                onValueChange = {
                    verificationCode = it
                    initialVerificationCodeCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkEmail(text) })

            CustomPasswordTextField(
                initialValue = password,
                placeholder = stringResource(id = R.string.password),
                onCorrectnessStateChange = {
                    filledPassword = !filledPassword
                    initialPasswordCorrectnessState = false
                },
                onValueChange = { password = it })

            ConfirmButton(
                enabled = (filledVerificationCode || initialVerificationCodeCorrectnessState)
                        && (filledPassword || initialPasswordCorrectnessState),
                text = stringResource(id = R.string.change_password)
            ) { onValuesUpdate(verificationCode, password) }
        }
    }
}
