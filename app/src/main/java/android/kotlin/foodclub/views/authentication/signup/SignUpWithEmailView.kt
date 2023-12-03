package android.kotlin.foodclub.views.authentication.signup

import android.kotlin.foodclub.domain.models.auth.SignUpUser
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
import android.kotlin.foodclub.R
import androidx.compose.ui.res.stringResource

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
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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