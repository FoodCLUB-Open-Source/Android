package live.foodclub.views.authentication.signup

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
fun CreateFullNameView(
    onValuesUpdate: () -> Unit, saveData: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    userSignUpInformation: SignUpUser,
    error: String
) {
    var name by remember { mutableStateOf(userSignUpInformation.name) }
    var initialNameCorrectnessState = FieldsValidation.checkFullName(name) == null
    var filledName by remember { mutableStateOf(false) }

    AuthLayout(
        header = stringResource(id = R.string.put_your_name),
        subHeading = stringResource(id = R.string.put_your_name_subheading),
        errorOccurred = true,
        message = error,
        onBackButtonClick = {
            saveData(name)
            onBackButtonClick()
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_4))) {
            CustomTextField(
                initialValue = name,
                placeholder = stringResource(id = R.string.full_name),
                keyboardType = KeyboardType.Text,
                onCorrectnessStateChange = { filledName = !filledName },
                allowSpace = true,
                onValueChange = {
                    name = it
                    initialNameCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkFullName(text) }
            )

            ConfirmButton(
                enabled = filledName || initialNameCorrectnessState,
                text = stringResource(id = R.string.continue_text)
            ) {
                saveData(name)
                onValuesUpdate()
            }
        }
    }
}