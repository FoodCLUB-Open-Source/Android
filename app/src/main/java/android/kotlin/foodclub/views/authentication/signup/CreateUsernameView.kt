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
import android.kotlin.foodclub.R
import androidx.compose.ui.res.stringResource

@Composable
fun UsernameView(
    onValuesUpdate: () -> Unit,
    saveData: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    userSignUpInformation: SignUpUser,
    error: String
) {
    var username by remember { mutableStateOf(userSignUpInformation.username) }
    var initialUsernameCorrectnessState = FieldsValidation.checkUsername(username) == null
    var filledUsername by remember { mutableStateOf(false) }

    AuthLayout(
        header = stringResource(id = R.string.create_username),
        subHeading = stringResource(id = R.string.create_username_subheading),
        errorOccurred = true, message = error,
        onBackButtonClick = {
            saveData(username)
            onBackButtonClick()
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CustomTextField(
                initialValue = username,
                placeholder = stringResource(id = R.string.username),
                keyboardType = KeyboardType.Text,
                onCorrectnessStateChange = { filledUsername = !filledUsername },
                onValueChange = {
                    username = it
                    initialUsernameCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkUsername(text) }
            )

            ConfirmButton(
                enabled = filledUsername || initialUsernameCorrectnessState,
                text = stringResource(id = R.string.create)
            ) {
                saveData(username)
                onValuesUpdate()
            }
        }
    }
}