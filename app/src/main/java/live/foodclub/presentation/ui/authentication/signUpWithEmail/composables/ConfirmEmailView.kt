package live.foodclub.presentation.ui.authentication.signUpWithEmail.composables

import live.foodclub.R
import live.foodclub.domain.models.auth.SignUpUser
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

@Composable
fun ConfirmEmailView(
    onValuesUpdate: () -> Unit, saveData: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    userSignUpInformation: SignUpUser,
    repeatedEmailState: String,
    error: String
) {
    val email by remember { mutableStateOf(userSignUpInformation.email) }
    var repeatedEmail by remember { mutableStateOf(repeatedEmailState) }
    var initialEmailCorrectnessState = email == repeatedEmail
    var filledEmail by remember { mutableStateOf(false) }

    AuthLayout(
        header = stringResource(id = R.string.confirm_email),
        subHeading = stringResource(id = R.string.confirm_email_subheading),
        errorOccurred = true,
        message = error,
        onBackButtonClick = {
            saveData(repeatedEmail)
            onBackButtonClick()
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_4))
        ) {
            CustomTextField(
                initialValue = repeatedEmail,
                placeholder = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                onCorrectnessStateChange = { filledEmail = !filledEmail },
                onValueChange = {
                    repeatedEmail = it
                    initialEmailCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.confirmEmail(text, email) }
            )

            ConfirmButton(
                enabled = filledEmail || initialEmailCorrectnessState,
                text = stringResource(id = R.string.continue_text)
            ) {
                saveData(repeatedEmail)
                onValuesUpdate()
            }
        }
    }
}