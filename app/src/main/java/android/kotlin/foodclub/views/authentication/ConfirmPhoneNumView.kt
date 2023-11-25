package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ConfirmPhoneNumView(navController: NavHostController) {
    Box(modifier = Modifier.background(Color.White))
    {
        AuthLayout(
            header = stringResource(id = R.string.confirm_identity),
            subHeading = stringResource(id = R.string.confirm_identity_subheading),
            onBackButtonClick = { navController.popBackStack() }) {
            ConfirmPhoneNumMainLayout()
        }
    }
}

@Composable
fun ConfirmPhoneNumTopLayout(
    navController: NavHostController? = null,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxHeight(0.3f)) {
        Column {
            Image(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = stringResource(id = R.string.go_back),
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .offset(x = (-8).dp)
                    .clickable { navController?.popBackStack() }
            )
            Box(modifier = Modifier.padding(top = 32.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
                    Text(
                        text = stringResource(id = R.string.confirm_identity),
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        fontFamily = PlusJakartaSans
                    )
                    Text(
                        text = stringResource(id = R.string.confirm_identity_subheading),
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        fontFamily = Montserrat
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPhoneNumMainLayout(
    navController: NavHostController? = null,
    modifier: Modifier = Modifier
) {

    val (countryCode, onCodeUpdate) = remember {
        mutableStateOf("")
    }

    val (phoneNum, onPhoneUpdate) = remember {
        mutableStateOf("")
    }

    val (syncContacts, onSyncUpdate) = remember {
        mutableStateOf(false)
    }

    val (enableButton, onButtonUpdate) = rememberSaveable {
        mutableStateOf(false)
    }

    if (phoneNum.isNotEmpty() && countryCode.isNotEmpty()) {
        onButtonUpdate(true)
    } else {
        onButtonUpdate(false)
    }

    val (isError, onErrorUpdate) = rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding()) {
                Text(
                    text = stringResource(id = R.string.plus_sign),
                    fontFamily = Montserrat,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                BasicTextField(
                    modifier = Modifier
                        .padding(0.dp)
                        .width(40.dp),
                    value = countryCode,
                    singleLine = true,
                    onValueChange = { onCodeUpdate(it.take(3)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = Montserrat,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                Text(
                    text = stringResource(id = R.string.vertical_line),
                    fontSize = 25.sp,
                    fontFamily = Montserrat,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp)
                )

                BasicTextField(
                    value = phoneNum,
                    singleLine = true,
                    onValueChange = { onPhoneUpdate(it.take(18)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = Montserrat,
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    ),
                )
            }


            val offset: Dp by animateDpAsState(if (isError) 0.dp else 130.dp, label = "")

            Box(
                modifier = Modifier
                    .padding(start = 74.dp + offset, bottom = 3.dp)
                    .width(280.dp - offset)
                    .height(3.dp)
            )
            {
                val len: Float by animateFloatAsState(if (isError) 1f else 0.1666f, label = "")
                val alpha: Float by animateFloatAsState(if (isError) 1f else 0f, label = "")
                Box(
                    Modifier
                        .fillMaxWidth(len)
                        .fillMaxHeight()
                        .graphicsLayer(alpha = alpha)
                        .background(Color.Red)
                )
            }

            Divider()

            Box(modifier = Modifier.height(20.dp))
            {
                if (isError) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 75.dp, top = 2.dp),
                        text = stringResource(id = R.string.invalid_number),
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = Montserrat
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.sync_contacts),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )

                Switch(
                    checked = syncContacts,
                    onCheckedChange = { onSyncUpdate(it) },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color(0xFF7EC60B),
                        uncheckedTrackColor = Color.LightGray,
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        uncheckedBorderColor = Color.LightGray,
                        checkedBorderColor = Color(0xFF7EC60B)
                    )
                )
            }
        }

        ConfirmButton(
            enabled = enableButton,
            text = stringResource(id = R.string.continue_text),
        ) {
            if ((isValidNumber(countryCode) && isValidNumber(phoneNum))) {
                if (phoneNum.length >= 9) {
                    onErrorUpdate(false)
                } else {
                    onErrorUpdate(true)
                }

            } else {
                onErrorUpdate(true)
            }

        }

    }

}

fun isValidNumber(text: String): Boolean {
    return text.matches(Regex("[0-9]+"))
}

@Composable
@Preview
fun ConfirmPhoneNumPreview() {
    Box(modifier = Modifier.background(Color.White))
    {
        AuthLayout(
            header = stringResource(id = R.string.confirm_identity),
            subHeading = stringResource(id = R.string.confirm_identity_subheading),
            onBackButtonClick = { /*TODO*/ }) {
            ConfirmPhoneNumMainLayout()
        }
    }

}
