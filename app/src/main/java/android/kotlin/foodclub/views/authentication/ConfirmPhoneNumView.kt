package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import android.kotlin.foodclub.config.ui.foodClubGreen
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
import androidx.compose.ui.res.dimensionResource
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
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
) {
    Box(modifier.fillMaxHeight(0.3f)) {
        Column {
            Image(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = stringResource(id = R.string.go_back),
                modifier = modifier
                    .width( dimensionResource(id = R.dimen.dim_32))
                    .height( dimensionResource(id = R.dimen.dim_32))
                    .offset(x = (-8).dp)
                    .clickable { navController?.popBackStack() }
            )
            Box(modifier = modifier.padding(top =  dimensionResource(id = R.dimen.dim_32))) {
                Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_28))) {
                    Text(
                        text = stringResource(id = R.string.confirm_identity),
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(id = R.dimen.fon_30).value.sp,
                        fontFamily = PlusJakartaSans
                    )
                    Text(
                        text = stringResource(id = R.string.confirm_identity_subheading),
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                        fontFamily = Montserrat
                    )
                }
            }

        }
    }
}

@Composable
fun ConfirmPhoneNumMainLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
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

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxHeight()) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding()) {
                Text(
                    text = stringResource(id = R.string.plus_sign),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                    fontWeight = FontWeight.Bold
                )

                BasicTextField(
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.dim_0))
                        .width(dimensionResource(id = R.dimen.dim_40)),
                    value = countryCode,
                    singleLine = true,
                    onValueChange = { onCodeUpdate(it.take(3)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                Text(
                    text = stringResource(id = R.string.vertical_line),
                    fontSize = dimensionResource(id = R.dimen.fon_25).value.sp,
                    fontFamily = Montserrat,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical =dimensionResource(id = R.dimen.dim_3), horizontal = dimensionResource(id = R.dimen.dim_10))
                )

                BasicTextField(
                    value = phoneNum,
                    singleLine = true,
                    onValueChange = { onPhoneUpdate(it.take(18)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.dim_0))
                        .fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                        color = Color.DarkGray
                    ),
                )
            }


            val offset: Dp by animateDpAsState(if (isError) dimensionResource(id = R.dimen.dim_0) else dimensionResource(id = R.dimen.dim_130), label = "")

            Box(
                modifier = modifier
                    .padding(start = dimensionResource(id = R.dimen.dim_74) + offset, bottom =dimensionResource(id = R.dimen.dim_3))
                    .width(dimensionResource(id = R.dimen.dim_280) - offset)
                    .height(dimensionResource(id = R.dimen.dim_3))
            )
            {
                val len: Float by animateFloatAsState(if (isError) 1f else 0.1666f, label = "")
                val alpha: Float by animateFloatAsState(if (isError) 1f else 0f, label = "")
                Box(
                    modifier
                        .fillMaxWidth(len)
                        .fillMaxHeight()
                        .graphicsLayer(alpha = alpha)
                        .background(Color.Red)
                )
            }

            Divider()

            Box(modifier = modifier.height(dimensionResource(id = R.dimen.dim_20)))
            {
                if (isError) {
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = dimensionResource(id = R.dimen.dim_75), top = dimensionResource(id = R.dimen.dim_2)),
                        text = stringResource(id = R.string.invalid_number),
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = Montserrat
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
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
                        checkedTrackColor = foodClubGreen,
                        uncheckedTrackColor = Color.LightGray,
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        uncheckedBorderColor = Color.LightGray,
                        checkedBorderColor = foodClubGreen
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
