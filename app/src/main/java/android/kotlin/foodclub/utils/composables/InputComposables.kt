package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Black
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import android.kotlin.foodclub.config.ui.borderBlue
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.config.ui.textFieldCustomColors
import android.kotlin.foodclub.utils.helpers.FieldsValidation
import android.kotlin.foodclub.views.authentication.TermsAndConditionsSimplified
import android.kotlin.foodclub.views.settings.colorGray
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onTextChange: (String) -> Unit, placeholder: String) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it; onTextChange(it) },
        placeholder = {
            Text(
                text = placeholder,
                color = Color.Black.copy(alpha = 0.4f),
                letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
                fontFamily = Montserrat,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon_ingredients),
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color.White,
            cursorColor = foodClubGreen,
            focusedTextColor = Color.Black.copy(alpha = 0.4f),
            unfocusedTextColor = Color.Black.copy(alpha = 0.4f)
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_16)))
            .border(1.dp, borderBlue, RoundedCornerShape(dimensionResource(id = R.dimen.dim_16)))
            .padding(
                horizontal = dimensionResource(id = R.dimen.dim_4),
                vertical = dimensionResource(id = R.dimen.dim_2)
            )
            .fillMaxWidth()
    )
}


/**
 * Custom code verification text field
 *
 * Custom code verification text field is a text field for 6-digit verification code
 *
 * @param onFillCallback Executes when all 6 fields were filled with numbers
 */
@Composable
fun CustomCodeTextField(
    onFillCallback: (Boolean, String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    var maxWidthTextField by remember { mutableFloatStateOf(1f) }
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
            BoxWithConstraints(

            ) {
                maxWidthTextField = maxWidth.value
                Row(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dim_72))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    repeat(6) { index ->
                        Box(
                            modifier = Modifier
                                .width((maxWidthTextField / 6.5).dp)
                                .fillMaxHeight()
                                .border(
                                    dimensionResource(id = R.dimen.dim_1),
                                    color = if (text.length == index) foodClubGreen
                                    else Black.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = text.getOrNull(index)?.toString() ?: "",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontFamily = PlusJakartaSans,
                                    fontSize = dimensionResource(id = R.dimen.fon_32).value.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
        })
}

/**
 * Custom text field
 *
 * Custom text field is a FoodCLUB style text field which can be used for data like email, name
 * and other similar. You can use verification function to validate filled text and disable
 * whitespaces.
 *
 * @param placeholder Text field placeholder
 * @param keyboardType [KeyboardType] which should be allowed for this text field
 * @param modifier Optional [Modifier] for the text field
 * @param initialValue Optional initial value of the text field
 * @param textValidation Boolean if the text validation should true of false
 * @param allowSpace Boolean if the whitespaces can be typed
 * @param validationMethod Executes every time text changes. Should return null if text is valid,
 * otherwise should return Error message displayed under the text field.
 * @param onCorrectnessStateChange Executes when valid state changes (from false to true or
 * the other way around)
 * @param onValueChange Executes when text changes
 */
@Composable
fun CustomTextField(
    placeholder: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    initialValue: String = "",
    iconID: Int? = null,
    textValidation: Boolean = false,
    allowSpace: Boolean = false,
    validationMethod: (text: String) -> String? = { text -> text },
    onCorrectnessStateChange: () -> Unit = {},
    onValueChange: (text: String) -> Unit,
) {
    var text by remember { mutableStateOf(initialValue) }
    var textValid by remember { mutableStateOf(false) }
    var errorMessage: String? by remember { mutableStateOf(null) }

    val composableLabel: @Composable (() -> Unit)? = if (!label.isNullOrBlank()) {
        @Composable {
            Text(
                modifier = Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.dim_5)
                ),
                text = label
            )
        }
    } else null

    Column {
        TextField(
            value = text,
            label = composableLabel,
            textStyle = TextStyle(fontFamily = Montserrat),
            enabled = enabled,
            onValueChange = {
                var textValidCurrent = true
                val currentVal = if (allowSpace) it else it.trim()
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
            placeholder = { Text(text = placeholder, color = Color(0xFF939393), fontFamily = Montserrat) },
            colors = if (errorMessage.isNullOrBlank()) textFieldCustomColors(textColor = Color.Black) else textFieldCustomColors(
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Red
            ),
            shape =  RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
            trailingIcon = {
                           if (iconID != null)
                           {
                               Image(painter = painterResource(id = iconID), contentDescription = "Edit", modifier = Modifier.size(
                                   dimensionResource(id = R.dimen.dim_15)),
                                   colorFilter = ColorFilter.tint(Color.Black)
                               )
                           }
            },
            modifier = modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                .background(Black.copy(alpha = 0.06F))
                .border(
                    width = dimensionResource(id = R.dimen.dim_1),
                    color = colorGray,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))
                )
                .padding(
                    //horizontal = dimensionResource(id = R.dimen.dim_10),
                    //vertical = dimensionResource(id = R.dimen.dim_5)
                )
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )

        Text(
            text = errorMessage.toString(),
            color = if (!textValid && !errorMessage.isNullOrBlank()) Color.Red else Color.Transparent,
            fontSize = dimensionResource(id = R.dimen.fon_12).value.sp
        )
    }
}

/**
 * Custom password text field
 *
 * Custom password text field is a FoodCLUB style text field which can be used for passwords.
 * You can use verification function to validate password strength.
 *
 * @param initialValue Optional initial value of the text field
 * @param placeholder Text field placeholder
 * @param strengthValidation Boolean if the password strength should be checked
 * @param onCorrectnessStateChange Executes when valid state changes (from false to true or
 * the other way around)
 * @param onValueChange Optional function which executes when text changes
 * @param label Optional [String] which adds text field label
 * @param textFieldColors Optional [TextFieldColors]
 */
@Composable
fun CustomPasswordTextField(
    initialValue: String = "",
    placeholder: String,
    strengthValidation: Boolean = true,
    onCorrectnessStateChange: () -> Unit,
    onValueChange: (text: String) -> Unit = {},
    label: String? = null,
    textFieldColors: TextFieldColors = textFieldCustomColors(textColor = Color.Black),
    passwordColors: TextFieldColors = textFieldCustomColors(textColor = Color.Gray),
    errorTextFieldColors: TextFieldColors = textFieldCustomColors(
        focusedIndicatorColor = Color.Red,
        unfocusedIndicatorColor = Color.Red,
        textColor = Color.Black
    ),
    errorPasswordColors: TextFieldColors = textFieldCustomColors(
        focusedIndicatorColor = Color.Red,
        unfocusedIndicatorColor = Color.Red,
        textColor = Color.Gray
    )
) {
    var password by remember { mutableStateOf(initialValue) }
    var passVisible by remember { mutableStateOf(false) }
    var passValid by remember { mutableStateOf(false) }
    var errorMessage: String? by remember { mutableStateOf(null) }

    val composableLabel: @Composable (() -> Unit)? = if (!label.isNullOrBlank()) {
        @Composable {
            Text(
                modifier = Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.dim_1),
                ),
                text = label,
                fontFamily = Montserrat
            )
        }
    } else null

    Column {
        TextField(
            value = password,
            textStyle = TextStyle(fontFamily = Montserrat, letterSpacing = if (passVisible) TextUnit.Unspecified else dimensionResource(id = R.dimen.fon_5).value.sp),
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
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFF939393),
                    fontFamily = Montserrat
                )
            },
            colors = if (errorMessage.isNullOrBlank()) { if (passVisible) textFieldColors else passwordColors} else if (passVisible) {errorTextFieldColors} else {errorPasswordColors},
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                .background(Black.copy(alpha = 0.06F))
                .padding(
                )
                .border(
                    width = dimensionResource(id = R.dimen.dim_1),
                    color = colorGray,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))
                )
                .fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
            trailingIcon = {
                if (passVisible) {
                    Button(
                        onClick = { passVisible = !passVisible },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.unhide),
                            contentDescription = null,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_15))
                        )
                    }
                } else {
                    Button(
                        onClick = { passVisible = !passVisible },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.hide_alt_1),
                            contentDescription = null,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_15))
                        )
                    }
                }
            },
            visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation('\u25CF'),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Text(
            text = errorMessage.toString(),
            color = if (!passValid && !errorMessage.isNullOrBlank()) Color.Red else Color.Transparent,
            fontSize = dimensionResource(id = R.dimen.fon_12).value.sp
        )


    }

}

/**
 * Custom back button (left black arrow icon)
 *
 * Back button used in many composables. It's a left black arrow.
 *
 * @param onBackButtonClick Executes when the button is clicked.
 */
@Composable
fun BackButton(
    onBackButtonClick: () -> Unit, backgroundTransparent: Boolean = false,
    buttonColor: Color = Color.Black
) {
    Button(
        shape = RectangleShape,
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
            .width(dimensionResource(id = R.dimen.dim_36))
            .height(dimensionResource(id = R.dimen.dim_36))
            .offset(x = (-8).dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (backgroundTransparent) Color.Transparent else Color.White,
            contentColor = if (backgroundTransparent) Color.Transparent else Color.White
        ),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0)),
        onClick = { onBackButtonClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = stringResource(id = R.string.go_back),
            tint = buttonColor,
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.dim_36))
                .height(dimensionResource(id = R.dimen.dim_36))
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
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
        modifier = Modifier
            .border(
                dimensionResource(id = R.dimen.dim_1),
                Color(0xFFDADADA),
                RoundedCornerShape(percent = 20)
            )
            .height(dimensionResource(id = R.dimen.dim_56))
            .width(dimensionResource(id = R.dimen.dim_105))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.White
        )
    ) {
        Image(
            painter = image,
            contentDescription = contentDescription,
            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_26))
        )
    }
}

/**
 * Terms and Conditions Footer
 *
 * Footer with text "By using FoodClub you agree to our Terms & Conditions" with clickable
 * "Terms & Conditions". When clicked, it opens screen where user can read FoodCLUB T&C.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsInfoFooter() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    Row(
        Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            color = Color.Gray,
            text = stringResource(id = R.string.by_using),
            fontFamily = Montserrat,
            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.dim_5))
        )

        ClickableText(
            text = AnnotatedString(text = stringResource(id = R.string.terms_and_conditions)),
            onClick = { showBottomSheet = true },
            style = TextStyle(
                color = Color.Gray,
                fontFamily = Montserrat,
                fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = bottomSheetState,
            content = {
                TermsAndConditionsSimplified()
            }
        )
    }
}

/**
 * Confirm Button
 *
 * FoodCLUB styled big green confirm button used in many composables. You can enable/disable
 * the button and set what should be displayed inside it.
 *
 * @param enabled Boolean if the button should be enabled
 * @param text Text which should be displayed inside the button
 * @param onClick Executes when button is clicked
 */
@Composable
fun ConfirmButton(
    enabled: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dim_56))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
            .fillMaxWidth(),
        enabled = enabled,
        colors = defaultButtonColors(),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            fontFamily = Montserrat,
            fontSize = dimensionResource(id = R.dimen.fon_16).value.sp
        )
    }
}