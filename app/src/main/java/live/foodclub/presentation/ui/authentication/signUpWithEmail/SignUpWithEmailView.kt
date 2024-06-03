package live.foodclub.presentation.ui.authentication.signUpWithEmail

import live.foodclub.R
import live.foodclub.domain.models.auth.SignUpUser
import live.foodclub.utils.composables.AuthLayout
import live.foodclub.utils.composables.customComponents.ConfirmButton
import live.foodclub.utils.composables.customComponents.CustomPasswordTextField
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

@Composable
fun SignUpWithEmailView(
    onValuesUpdate: (String, String) -> Unit,
    onBackButtonClick: () -> Unit,
    userSignUpInformation: SignUpUser
) {
    AuthLayout(
        header = stringResource(id = R.string.new_here),
        onBackButtonClick = onBackButtonClick
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_4))) {
            var userEmail by remember { mutableStateOf(userSignUpInformation.email) }
            var userPassword by remember { mutableStateOf(userSignUpInformation.password) }

            var initialEmailCorrectnessState = FieldsValidation.checkEmail(userEmail) == null
            var initialPasswordCorrectnessState =
                FieldsValidation.checkPassword(userPassword) == null
            var filledEmail by remember { mutableStateOf(false) }
            var filledPassword by remember { mutableStateOf(false) }


            CustomTextField(
                initialValue = userEmail,
                placeholder = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                onCorrectnessStateChange = { filledEmail = !filledEmail },
                onValueChange = {
                    userEmail = it
                    initialEmailCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkEmail(text) })

            CustomPasswordTextField(
                initialValue = userPassword,
                placeholder = stringResource(id = R.string.password),
                onCorrectnessStateChange = {
                    filledPassword = !filledPassword
                    initialPasswordCorrectnessState = false
                },
                onValueChange = { userPassword = it })

            ConfirmButton(
                enabled = (filledEmail || initialEmailCorrectnessState)
                        && (filledPassword || initialPasswordCorrectnessState),
                text = stringResource(id = R.string.sign_up)
            ) { onValuesUpdate(userEmail, userPassword) }
        }

    }
}