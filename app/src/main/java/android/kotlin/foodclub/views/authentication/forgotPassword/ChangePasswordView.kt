package android.kotlin.foodclub.views.authentication.forgotPassword

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.utils.composables.CustomPasswordTextField
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.utils.helpers.FieldsValidation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChangePasswordView(onValuesUpdate: (password: String, code: String) -> Unit,
                       onBackButtonClick: (String) -> Unit,
                       email: String, errorOccurred: State<Boolean>, message: State<String>
){
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 30.dp, end = 30.dp, top = 120.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        Button(
            shape = RectangleShape,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .width(20.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(5.dp),
            onClick = { onBackButtonClick(email) }
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "Back",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
        }

        Text(
            text = "Change Password", fontSize = 30.sp,
            fontFamily = PlusJakartaSans,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        )


        var verificationCode by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var initialVerificationCodeCorrectnessState = FieldsValidation.checkEmail(verificationCode) == null
        var initialPasswordCorrectnessState = FieldsValidation.checkPassword(password) == null
        var filledVerificationCode by remember { mutableStateOf(false) }
        var filledPassword by remember { mutableStateOf(false) }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CustomTextField(initialValue = verificationCode,
                placeholder = "Verification code",
                keyboardType = KeyboardType.NumberPassword,
                onCorrectnessStateChange = { filledVerificationCode = !filledVerificationCode },
                onValueChange = {
                    verificationCode = it
                    initialVerificationCodeCorrectnessState = false
                },
                textValidation = true,
                validationMethod = { text -> FieldsValidation.checkEmail(text) })

            CustomPasswordTextField(initialValue = password, placeholder = "Password",
                onCorrectnessStateChange = {
                    filledPassword = !filledPassword
                    initialPasswordCorrectnessState = false
                },
                onValueChange = { password = it })

            Text(text = (if(errorOccurred.value) "Error: " else "") + message.value,
                textAlign = TextAlign.Left, fontWeight = FontWeight.Normal, fontSize = 12.sp,
                fontFamily = Montserrat,
                color = if(errorOccurred.value) Color.Red else Color.Black
            )

            ConfirmButton(
                enabled = (filledVerificationCode || initialVerificationCodeCorrectnessState)
                        && (filledPassword || initialPasswordCorrectnessState),
                text = "Change Password"
            ) { onValuesUpdate(verificationCode, password) }
        }

    }

}
