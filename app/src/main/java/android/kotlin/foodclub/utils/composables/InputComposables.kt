package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import android.kotlin.foodclub.config.ui.textFieldCustomColors
import android.kotlin.foodclub.utils.helpers.FieldsValidation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomCodeTextField(onFillCallback: (Boolean, String) -> Unit) {
    var text by remember { mutableStateOf("") }
    BasicTextField(modifier = Modifier.fillMaxWidth(),
        value = text,
        singleLine = true,
        onValueChange = {
            if (it.length <= 6) {
                text = it
            }
            onFillCallback(it.length >= 6, text)
        },
        enabled = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        keyboardActions = KeyboardActions(),
        decorationBox = {
            Row(
                modifier = Modifier.size(352.dp, 72.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(6) { index ->
                    Box(
                        modifier = Modifier
                            .size(52.dp, 72.dp)
                            .border(
                                1.dp,
                                color = if (text.length == index) Color(0xFF7EC60B)
                                else Color(0xFF000000).copy(alpha = 0.3f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text.getOrNull(index)?.toString() ?: "",
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontFamily = PlusJakartaSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        })
}

@Composable
fun CustomTextField(initialValue: String = "",
    placeholder: String, keyboardType: KeyboardType, textValidation: Boolean = false, allowSpace: Boolean = false,
    validationMethod: (text: String) -> String? = { text -> text },
    onCorrectnessStateChange: () -> Unit = {}, onValueChange: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(initialValue) }
    var textValid by remember { mutableStateOf(false) }
    var errorMessage: String? by remember { mutableStateOf(null) }

    Column {
        TextField(
            value = text,
            onValueChange = {
                var textValidCurrent = true
                val currentVal = if(allowSpace) it else it.trim()
                if (textValidation) {
                    errorMessage = validationMethod(currentVal)
                    textValidCurrent = errorMessage.isNullOrBlank()
                }
                if (currentVal == "") {
                    errorMessage = null
                    textValidCurrent = false
                }
                if (textValid != textValidCurrent) onCorrectnessStateChange()
                text = currentVal
                onValueChange(currentVal)
                textValid = textValidCurrent
            },
            placeholder = { Text(text = placeholder, color = Color(0xFF939393)) },
            colors = if (errorMessage.isNullOrBlank()) textFieldCustomColors() else textFieldCustomColors(
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Red
            ),
            modifier = modifier
                .border(1.dp, Color(0xFFDADADA), RoundedCornerShape(percent = 20))
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF000000).copy(alpha = 0.04F))
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )

        Text(
            text = errorMessage.toString(),
            color = if (!textValid && !errorMessage.isNullOrBlank()) Color.Red else Color.Transparent,
            fontSize = 12.sp
        )
    }
}

@Composable
fun CustomPasswordTextField(initialValue: String = "",
                            placeholder: String, strengthValidation: Boolean = true,
                            onCorrectnessStateChange: () -> Unit,
                            onValueChange: (text: String) -> Unit = {},
                            label: String? = null,
                            textFieldColors: TextFieldColors = textFieldCustomColors(),
                            errorTextFieldColors: TextFieldColors = textFieldCustomColors(
                                focusedIndicatorColor = Color.Red,
                                unfocusedIndicatorColor = Color.Red
                            )
) {
    var password by remember { mutableStateOf(initialValue) }
    var passVisible by remember { mutableStateOf(false) }
    var passValid by remember { mutableStateOf(false) }
    var errorMessage: String? by remember { mutableStateOf(null) }

    val composableLabel: @Composable (() -> Unit)? = if(!label.isNullOrBlank()) {
        @Composable { Text(text = label) } } else null

    Column {
        TextField(
            value = password,
            onValueChange = {
                var passValidCurrent = true
                if (strengthValidation) {
                    errorMessage = FieldsValidation.checkPassword(it.trim())
                    passValidCurrent = errorMessage.isNullOrBlank()
                }
                if (it.trim() == "") {
                    errorMessage = null
                    passValidCurrent = false
                }
                if (passValid != passValidCurrent) onCorrectnessStateChange()
                password = it.trim()
                onValueChange(it.trim())
                passValid = passValidCurrent
            },
            label = composableLabel,
            placeholder = { Text(text = placeholder, color = Color(0xFF939393)) },
            colors = if (errorMessage.isNullOrBlank()) textFieldColors else errorTextFieldColors,
            modifier = Modifier
                .border(1.dp, Color(0xFFDADADA), RoundedCornerShape(percent = 20))
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF000000).copy(alpha = 0.04F))
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            trailingIcon = {
                if (passVisible) {
                    Button(
                        onClick = { passVisible = !passVisible },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.unhide),
                            contentDescription = null,
                        )
                    }
                } else {
                    Button(
                        onClick = { passVisible = !passVisible },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.hide),
                            contentDescription = null,
                        )
                    }
                }
            },
            visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Text(
            text = errorMessage.toString(),
            color = if (!passValid && !errorMessage.isNullOrBlank()) Color.Red else Color.Transparent,
            fontSize = 12.sp
        )


    }

}

@Composable
fun BackButton(onBackButtonClick: () -> Unit) {
    Button(
        shape = RectangleShape,
        modifier = Modifier.clip(RoundedCornerShape(15.dp))
            .width(36.dp).height(36.dp).offset(x = (-8).dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp),
        onClick = { onBackButtonClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = "Back",
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(36.dp).height(36.dp)
        )
    }
}

@Composable
fun AlternativeLoginOption(
    image: Painter,
    contentDescription: String
) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .border(1.dp, Color(0xFFDADADA), RoundedCornerShape(percent = 20))
            .height(56.dp)
            .width(105.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.White
        )
    ) {
        Image(
            painter = image,
            contentDescription = contentDescription,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Composable
fun TermsAndConditionsInfoFooter(onClick: () -> Unit = {}) {
    Row(
        Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            color = Color.Gray,
            text = "By using FoodCLUB you agree to our ",
            fontFamily = Montserrat,
            fontSize = 10.sp
        )

        ClickableText(
            text = AnnotatedString("Terms & Conditions"),
            onClick={ onClick() },
            style = TextStyle(
                color = Color.Gray,
                fontFamily = Montserrat,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@Composable
fun ConfirmButton(enabled: Boolean, text: String, onClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.height(56.dp).clip(RoundedCornerShape(10.dp)).fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7EC60B),
            disabledContainerColor = Color(0xFFC9C9C9),
            disabledContentColor = Color.White,
            contentColor = Color.White
        ),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            fontFamily = Montserrat,
            fontSize = 16.sp
        )
    }
}