package android.kotlin.foodclub.views.authentication.signup

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.auth.SignUpUser
import android.kotlin.foodclub.ui.theme.Montserrat
import android.kotlin.foodclub.ui.theme.PlusJakartaSans
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.utils.composables.TermsAndConditionsInfoFooter
import android.kotlin.foodclub.utils.helpers.FieldsValidation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateFullNameView(onValuesUpdate: (String) -> Unit, onBackButtonClick: (String) -> Unit,
                 userSignUpInformation: State<SignUpUser>, error: String) {
    var name by remember { mutableStateOf(userSignUpInformation.value.name) }
    var initialNameCorrectnessState = FieldsValidation.checkFullName(name) == null
    var filledName by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()          // MAKING IT SO IT FILLS THE MAX SCREEN SIZE
            .padding(start = 32.dp, end = 32.dp, top = 100.dp, bottom = 32.dp) // ADDING PADDING TO SHIFT IT UP/DOWN
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.weight(1F)) {
            Column {
                // BACK BUTTON
                Image(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "go back",
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp)
                        .offset(x = (-8).dp)
                        .clickable { onBackButtonClick(name) }
                )
                Column(Modifier.padding(top = 32.dp)) {
                    // USERNAME TEXTVIEW
                    Text(
                        text = "Put your name!",
                        fontSize = 32.sp,
                        fontFamily = PlusJakartaSans,  // AS ITS A CLEAN CUT FONT
                        fontWeight = FontWeight.Bold       // TO MAKE IT STAND OUT
                    )

                    // SUB TEXT VIEW
                    Text(
                        text = "So others know how to call you!",
                        fontSize = 18.sp,
                        fontFamily = Montserrat,  // AS ITS A CLEAN CUT FONT
                        color = Color(0xFF000000).copy(alpha = 0.4f)
                    )
                }
            }

            Text(
                text = if(error.isNotEmpty()) "Error: $error" else "",
                fontFamily = Montserrat,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
        Column(Modifier.weight(2F)) {
            // TEXT FIELD STUFF
            CustomTextField(initialValue = name,
                placeholder = "Full name", keyboardType = KeyboardType.Text,
                onCorrectnessStateChange = { filledName = !filledName },
                allowSpace = true,
                onValueChange = { name = it
                    initialNameCorrectnessState = false }, textValidation = true,
                validationMethod = { text -> FieldsValidation.checkFullName(text) }
            )

            // BUTTON
            Button(
                onClick = { onValuesUpdate(name) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(56.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                enabled = filledName || initialNameCorrectnessState,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7EC60B),
                    disabledContainerColor = Color(0xFFC9C9C9),
                    disabledContentColor = Color.White,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Continue", fontSize = 16.sp)
            }

        }
        Box(Modifier.weight(1F)) {
            TermsAndConditionsInfoFooter()
        }
    }

    }