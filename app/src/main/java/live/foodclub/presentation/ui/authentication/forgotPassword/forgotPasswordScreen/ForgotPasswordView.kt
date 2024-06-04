package live.foodclub.presentation.ui.authentication.forgotPassword.forgotPasswordScreen

import android.annotation.SuppressLint
import live.foodclub.R
import live.foodclub.utils.composables.AuthLayout
import live.foodclub.utils.composables.customComponents.ConfirmButton
import live.foodclub.utils.composables.customComponents.CustomTextField
import live.foodclub.utils.helpers.FieldsValidation
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

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ForgotPasswordView(
    onValuesUpdate: (email: String) -> Unit,
    onBackButtonClick: () -> Unit,
    onEmailChange: (email: String) -> Unit,
    state: ForgotPasswordState
) {

    var initialEmailCorrectnessState = FieldsValidation.checkEmail(state.email) == null
    var filledEmail by remember { mutableStateOf(false) }

    AuthLayout(
        header = stringResource(id = R.string.forgot_password),
        subHeading = stringResource(id = R.string.forgot_password_subheading),
        errorOccurred = state.errorOccurred,
        message = state.message,
        onBackButtonClick = { onBackButtonClick() }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_4))) {
            CustomTextField(
                initialValue = state.email,
                placeholder = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                onCorrectnessStateChange = { filledEmail = !filledEmail },
                onValueChange = {
                    onEmailChange(it)
                    initialEmailCorrectnessState = false
                },
                textValidation = false,
                validationMethod = { text -> FieldsValidation.checkEmail(text) })

            ConfirmButton(
                enabled = (filledEmail || initialEmailCorrectnessState) || true,
                text = stringResource(id = R.string.send_code)
            ) { onValuesUpdate(state.email) }
        }
    }
}

