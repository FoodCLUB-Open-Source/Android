package live.foodclub.presentation.ui.authentication.forgotPassword

import live.foodclub.R
import live.foodclub.utils.composables.AuthLayout
import live.foodclub.utils.composables.customComponents.ConfirmButton
import live.foodclub.utils.composables.customComponents.CustomPasswordTextField
import live.foodclub.utils.composables.customComponents.CustomTextField
import live.foodclub.utils.helpers.FieldsValidation
import live.foodclub.presentation.ui.authentication.forgotPassword.forgotPasswordScreen.ForgotPasswordState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun ChangePasswordView(
    onValuesUpdate: (password: String, code: String) -> Unit,
    onBackButtonClick: () -> Unit,
    state: ForgotPasswordState,
){
    var verificationCode by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var initialVerificationCodeCorrectnessState = FieldsValidation.checkEmail(verificationCode) == null
    var initialPasswordCorrectnessState = FieldsValidation.checkPassword(password) == null
    var filledVerificationCode by remember { mutableStateOf(false) }
    var filledPassword by remember { mutableStateOf(false) }

    AuthLayout(
        header = stringResource(id = R.string.change_password),
        subHeading = stringResource(id = R.string.change_password_subheading, state.email),
        errorOccurred = state.errorOccurred,
        message = state.message,
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
                textValidation = false,
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
