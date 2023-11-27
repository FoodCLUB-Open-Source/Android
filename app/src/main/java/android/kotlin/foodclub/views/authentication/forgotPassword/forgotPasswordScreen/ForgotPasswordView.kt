package android.kotlin.foodclub.views.authentication.forgotPassword.forgotPasswordScreen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import android.kotlin.foodclub.R

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ForgotPasswordView(
    onValuesUpdate: (email: String) -> Unit,
    onBackButtonClick: () -> Unit,
    onEmailChange: (email: String) -> Unit,
    state: ForgotPasswordState
) {
    //var userEmail by remember { mutableStateOf(email) }

    var initialEmailCorrectnessState = FieldsValidation.checkEmail(state.email) == null
    var filledEmail by remember { mutableStateOf(false) }

    AuthLayout(
        header = stringResource(id = R.string.forgot_password),
        subHeading = stringResource(id = R.string.forgot_password_subheading),
        errorOccurred = state.errorOccurred,
        message = state.message,
        onBackButtonClick = { onBackButtonClick() }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomTextField(
                initialValue = state.email,
                placeholder = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                onCorrectnessStateChange = { filledEmail = !filledEmail },
                onValueChange = {
                    onEmailChange(it)
                    initialEmailCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkEmail(text) })

            ConfirmButton(
                enabled = (filledEmail || initialEmailCorrectnessState) || true,
                text = stringResource(id = R.string.send_code)
            ) { onValuesUpdate(state.email) }
        }
    }
}

