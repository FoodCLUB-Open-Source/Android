package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 80.dp, bottom = 50.dp)
        ) {
            ConfirmPhoneNumTopLayout()//modifier = Modifier.weight(1F))
            ConfirmPhoneNumMainLayout()//modifier = Modifier.weight(1F))
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
                contentDescription = "go back",
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .offset(x = (-8).dp)
                    .clickable { navController?.popBackStack() }
            )
            Box(modifier = Modifier.padding(top = 32.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
                    Text(
                        text = "Confirm Your Identity", textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold, fontSize = 30.sp, fontFamily = PlusJakartaSans
                    )
                    Text(
                        text = "Please confirm your country code and enter in your phone number",
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
    val interactionSource = remember { MutableInteractionSource() }


    /*
    val (test, testChange) = remember { mutableStateOf(false) }

    Button(onClick = {testChange(!test)})
    {
        Text(text="Test")
    }

     */



    Box(modifier) {
        Column {

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding()) {
                /*
                Image(
                    painter = painterResource(id = R.drawable.nav_create_icon),
                    contentDescription = "CountryCode",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp),
                    contentScale = ContentScale.Crop
                )
                 */


                /*
                BasicTextField(value = countryCode,
                    onValueChange = {
                        Log.d("TAG", countryCode)
                        onCodeUpdate(it.take(3))},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(60.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.Blue, fontFamily = montserratFamily, fontSize = 10.sp, fontWeight = FontWeight.Bold),

                )

                 */

                Text(text="+", fontFamily= Montserrat, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                BasicTextField(modifier = Modifier
                    .padding(0.dp)
                    .width(40.dp),
                    value = countryCode,
                    singleLine = true,
                    onValueChange = { onCodeUpdate(it.take(3)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    //colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    textStyle = LocalTextStyle.current.copy(fontFamily = Montserrat, fontSize = 18.sp, fontWeight = FontWeight.Bold),
                )


                /*
                val mod: Modifier = if (isError) Modifier.indicatorLine(
                    enabled = true,
                    interactionSource = interactionSource,
                    isError = true,
                    colors = TextFieldDefaults.textFieldColors(MaterialTheme.colorScheme.error),
                    focusedIndicatorLineThickness = 3.dp,
                    unfocusedIndicatorLineThickness = 3.dp
                ) else Modifier

                 */
                Text(text = "|", fontSize = 25.sp, fontFamily = Montserrat, color = Color.LightGray, modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp))
                BasicTextField(
                    value = phoneNum,
                    singleLine = true,
                    onValueChange = { onPhoneUpdate(it.take(18)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    //colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent), ,
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth()
                        //.then(mod)
                       ,
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = Montserrat,
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    ),
                )
            }


            val offset: Dp by animateDpAsState(if (isError) 0.dp else 130.dp, label = "")
            Box(modifier = Modifier
                .padding(start = 74.dp + offset, bottom = 3.dp)
                .width(280.dp - offset)
                .height(3.dp))
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
                        text = "Invalid number",
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = Montserrat
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sync Contacts", fontFamily = Montserrat, fontWeight = FontWeight.Bold)
                Switch(checked = syncContacts,
                    onCheckedChange = { onSyncUpdate(it) },
                    colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFF7EC60B),
                        uncheckedTrackColor = Color.LightGray,
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        uncheckedBorderColor = Color.LightGray,
                        checkedBorderColor = Color(0xFF7EC60B))
                )
            }

            Button(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
                    .padding(top = 40.dp)
                    .padding(horizontal = 10.dp),
                onClick = {
                    if ((isValidNumber(countryCode) && isValidNumber(phoneNum))) {
                        if (phoneNum.length >= 9) {
                            onErrorUpdate(false)
                        }
                        else
                        {
                            onErrorUpdate(true)
                        }

                    } else {
                        onErrorUpdate(true)
                    }


                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7EC60B),
                    contentColor = Color(0xFF7EC60B)
                ),
                contentPadding = PaddingValues(0.dp),
                enabled = enableButton
            )
            {
                Text(
                    text = "Continue",
                    fontSize = 15.sp,
                    color = Color.White,
                    fontFamily = Montserrat
                )
            }
        }
    }
}

fun isValidNumber(text: String): Boolean {
    // Add your custom validation rules here
    return text.matches(Regex("[0-9]+"))
}

@Composable
@Preview
fun ConfirmPhoneNumPreview() {
    Box(modifier = Modifier.background(Color.White))
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 80.dp, bottom = 50.dp)
        ) {
            ConfirmPhoneNumTopLayout()//modifier = Modifier.weight(1F))
            ConfirmPhoneNumMainLayout()//modifier = Modifier.weight(1F))
        }
    }

}
