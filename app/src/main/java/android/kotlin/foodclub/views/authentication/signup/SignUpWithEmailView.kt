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

@Composable
fun SignUpWithEmailView(
    onValuesUpdate: (String, String) -> Unit, onBackButtonClick: () -> Unit,
    userSignUpInformation: State<SignUpUser>
) {
    AuthLayout(header = "New Here?\nNo problem!", onBackButtonClick = onBackButtonClick) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            var userEmail by remember { mutableStateOf(userSignUpInformation.value.email) }
            var userPassword by remember { mutableStateOf(userSignUpInformation.value.password) }

            var initialEmailCorrectnessState = FieldsValidation.checkEmail(userEmail) == null
            var initialPasswordCorrectnessState = FieldsValidation.checkPassword(userPassword) == null
            var filledEmail by remember { mutableStateOf(false) }
            var filledPassword by remember { mutableStateOf(false) }


            CustomTextField(initialValue = userEmail,
                placeholder = "Email",
                keyboardType = KeyboardType.Email,
                onCorrectnessStateChange = { filledEmail = !filledEmail },
                onValueChange = {
                    userEmail = it
                    initialEmailCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkEmail(text) })

            CustomPasswordTextField(initialValue = userPassword, placeholder = "Password",
                onCorrectnessStateChange = {
                    filledPassword = !filledPassword
                    initialPasswordCorrectnessState = false
                },
                onValueChange = { userPassword = it })

            ConfirmButton(
                enabled = (filledEmail || initialEmailCorrectnessState)
                        && (filledPassword || initialPasswordCorrectnessState),
                text = "Sign Up"
            ) { onValuesUpdate(userEmail, userPassword) }
//                Button(
//                    shape = RoundedCornerShape(10.dp),
//                    modifier = Modifier
//                        .height(56.dp)
//                        .clip(RoundedCornerShape(10.dp))
//                        .fillMaxWidth(),
//                    enabled = (filledEmail || initialEmailCorrectnessState)
//                            && (filledPassword || initialPasswordCorrectnessState),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF7EC60B),
//                        disabledContainerColor = Color(0xFFC9C9C9),
//                        disabledContentColor = Color.White,
//                        contentColor = Color.White
//                    ),
//                    onClick = { onValuesUpdate(userEmail, userPassword) }
//                ) {
//                    Text(
//                        text = "Sign Up",
//                        fontFamily = Montserrat,
//                        fontSize = 16.sp
//                    )
//                }
        }

//            Image(
//                painterResource(id = R.drawable.login_with),
//                contentDescription = "or login with",
//                modifier = Modifier.fillMaxWidth().height(20.dp),
//                alignment = Alignment.Center
//            )

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
//            ) {
//                AlternativeLoginOption(
//                    image = painterResource(R.drawable.facebook_ic),
//                    contentDescription = "Log in using facebook"
//                )
//                AlternativeLoginOption(
//                    image = painterResource(R.drawable.google_ic),
//                    contentDescription = "Log in using google"
//                )
//                AlternativeLoginOption(
//                    image = painterResource(R.mipmap.instagram_icon),
//                    contentDescription = "Log in using instagram"
//                )
//
//            }

    }
}