package android.kotlin.foodclub.views.authentication

import android.content.Context
import android.kotlin.foodclub.R
import android.kotlin.foodclub.ui.theme.Montserrat
import android.kotlin.foodclub.ui.theme.PlusJakartaSans
import android.kotlin.foodclub.utils.composables.CustomAuthButton
import android.kotlin.foodclub.utils.composables.CustomCodeTextField
import android.kotlin.foodclub.viewmodels.authentication.SignupVerificationViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun SignupVerification(navController: NavHostController, username: String?) {
    if(username == null) {
        //Navigate to signup form and  show an error
    }
    val viewModel: SignupVerificationViewModel = viewModel()

    LaunchedEffect(key1 = Unit) {
        viewModel.setData(navController, username.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp, top = 80.dp, bottom = 50.dp),
    ) {
        SignupVerificationTopLayout(viewModel, modifier = Modifier.weight(1F))
        SignupVerificationMainLayout(viewModel, modifier = Modifier.weight(2F))
        SignupVerificationBottomLayout(modifier = Modifier.weight(1F))
    }
}

@Composable
fun SignupVerificationTopLayout(viewModel: SignupVerificationViewModel, modifier: Modifier = Modifier) {
    val message = viewModel.message.collectAsState()

    Box(modifier.fillMaxHeight()) {
        Column {
            Image(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = "go back",
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .offset(x = (-8).dp)
            )
            Box(modifier = Modifier.padding(top = 32.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
                    Text(text = "Enter code", textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold, fontSize = 30.sp, fontFamily = PlusJakartaSans
                    )
                    Text(text = message.value,
                        textAlign = TextAlign.Left, fontWeight = FontWeight.Normal, fontSize = 16.sp,
                        fontFamily = Montserrat
                    )
                }
            }
        }
    }
}

@Composable
fun SignupVerificationMainLayout(viewModel: SignupVerificationViewModel, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            var enableButton by remember { mutableStateOf(false) }
            var isTimerRunning by remember { mutableStateOf(true) }
            var currentTime by remember { mutableStateOf(TimeUnit.SECONDS.toMillis(62)) }
            var currentCode by remember { mutableStateOf("") }

            CustomCodeTextField { isEnabled, code ->
                enableButton = isEnabled
                currentCode = code
            }
            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                CustomAuthButton(enabled = enableButton, "Verify") {
                    viewModel.verifyCode(currentCode)
                    enableButton = false
                }
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if(isTimerRunning) "Wait " + TimeUnit.MILLISECONDS.toSeconds(currentTime)
                                + " seconds to " else "I didn't receive a code ",
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        color = Color(0xFF1E232C).copy(alpha = 0.5F)
                    )
                    ClickableText(
                        text = AnnotatedString("Resend"),
                        onClick = { if(!isTimerRunning) {
                            viewModel.sendVerificationCode()
                            currentTime = TimeUnit.SECONDS.toMillis(61)
                            isTimerRunning = true
                        } },
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Montserrat,
                            fontSize = 12.sp,
                            color = if(isTimerRunning) Color(0xFF1E232C).copy(alpha = 0.5F)
                            else Color(0xFF7EC60B)
                        )
                    )
                }
            }


            LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
                if(currentTime > 0 && isTimerRunning) {
                    delay(100L)
                    currentTime -= 100L
                }
                if(currentTime <= 0) isTimerRunning = false
            }
        }

    }
}

@Composable
fun SignupVerificationBottomLayout(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxSize()
    ) {
        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(
                text = "By using FoodClub you agree to our ",
                fontSize = 12.sp,
                color = Color(0xFF1E232C).copy(alpha = 0.5F)
            )
            ClickableText(
                text = AnnotatedString("Terms & Conditions"),
                onClick = {},
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color(0xFF1E232C).copy(alpha = 0.5F)
                )
            )
        }
    }
}
