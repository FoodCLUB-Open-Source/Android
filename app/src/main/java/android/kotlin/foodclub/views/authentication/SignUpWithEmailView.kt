package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import android.kotlin.foodclub.ui.theme.Montserrat
import android.kotlin.foodclub.ui.theme.PlusJakartaSans
import android.kotlin.foodclub.utils.composables.AlternativeLoginOption
import android.kotlin.foodclub.utils.composables.CustomPasswordTextField
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.utils.composables.TermsAndConditionsInfoFooter
import android.kotlin.foodclub.utils.helpers.FieldsValidation
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpWithEmailView(
    onValuesUpdate: (String, String) -> Unit, onBackButtonClick: () -> Unit,
    userSignUpInformation: State<UserSignUpInformation>
) {
    Column(Modifier.fillMaxSize().padding(start = 32.dp, end = 32.dp, top = 100.dp, bottom = 32.dp)) {
        Column(Modifier.weight(1F)) {
            Image(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = "go back",
                modifier = Modifier.width(36.dp).height(36.dp).offset(x = (-8).dp)
                    .clickable { onBackButtonClick() }
            )
            Column(Modifier.padding(top = 32.dp)) {
                Text(
                    text = "New Here?",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
                Text(
                    text = "No Problem!",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(2F).fillMaxSize()) {
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

                Button(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(56.dp).clip(RoundedCornerShape(10.dp)).fillMaxWidth(),
                    enabled = (filledEmail || initialEmailCorrectnessState)
                            && (filledPassword || initialPasswordCorrectnessState),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7EC60B),
                        disabledContainerColor = Color(0xFFC9C9C9),
                        disabledContentColor = Color.White,
                        contentColor = Color.White
                    ),
                    onClick = { onValuesUpdate(userEmail, userPassword) }
                ) {
                    Text(
                        text = "Sign Up",
                        fontFamily = Montserrat,
                        fontSize = 16.sp
                    )
                }
            }

            Image(
                painterResource(id = R.drawable.login_with),
                contentDescription = "or login with",
                modifier = Modifier.fillMaxWidth().height(20.dp),
                alignment = Alignment.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            ) {
                AlternativeLoginOption(
                    image = painterResource(R.drawable.facebook_ic),
                    contentDescription = "Log in using facebook"
                )
                AlternativeLoginOption(
                    image = painterResource(R.drawable.google_ic),
                    contentDescription = "Log in using google"
                )
                AlternativeLoginOption(
                    image = painterResource(R.mipmap.instagram_icon),
                    contentDescription = "Log in using instagram"
                )

            }

        }
        Box(Modifier.weight(1F)) { TermsAndConditionsInfoFooter() }
    }
}