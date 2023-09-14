package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.ui.theme.Montserrat
import android.kotlin.foodclub.ui.theme.PlusJakartaSans
import android.kotlin.foodclub.utils.composables.CustomAuthButton
import android.kotlin.foodclub.utils.composables.CustomCodeTextField
import android.kotlin.foodclub.utils.composables.TermsAndConditionsInfoFooter
import android.kotlin.foodclub.viewmodels.authentication.SignupVerificationViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.graphs.Graph
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun SignupVerification(navController: NavHostController, username: String?, resendCode: Boolean?) {
    val viewModel: SignupVerificationViewModel = viewModel()

    LaunchedEffect(key1 = Unit) {
        viewModel.setData(navController, username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp, top = 80.dp, bottom = 50.dp),
    ) {
        SignupVerificationTopLayout(navController, modifier = Modifier.weight(1F))
        SignupVerificationMainLayout(viewModel, resendCode, modifier = Modifier.weight(2F))
        Box(Modifier.weight(1F)) { TermsAndConditionsInfoFooter() }
    }
}

@Composable
fun SignupVerificationTopLayout(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxHeight()) {
        Column {
            Image(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = "go back",
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .offset(x = (-8).dp)
                    .clickable { navController.navigate(Graph.AUTHENTICATION) }
            )
            Box(modifier = Modifier.padding(top = 32.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
                    Text(text = "Enter code", textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold, fontSize = 30.sp, fontFamily = PlusJakartaSans
                    )
                    Text(text = "We’ve sent an SMS with an activation code to your phone +44 7503759410",
                        textAlign = TextAlign.Left, fontWeight = FontWeight.Normal, fontSize = 16.sp,
                        fontFamily = Montserrat
                    )
                }
            }

        }
    }
}

@Composable
fun SignupVerificationMainLayout(viewModel: SignupVerificationViewModel, resendCode: Boolean?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 80.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxSize()) {
            var enableButton by remember { mutableStateOf(false) }
            var isTimerRunning by remember { mutableStateOf(true) }
            var currentTime by remember { mutableStateOf(TimeUnit.SECONDS.toMillis(62)) }
            var currentCode by remember { mutableStateOf("") }

            val errorOccurred = viewModel.errorOccurred.collectAsState()
            val message = viewModel.message.collectAsState()

            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 12.dp)) {
                //Error or info messages
                Text(text = (if(errorOccurred.value) "Error: " else "") + message.value,
                    textAlign = TextAlign.Left, fontWeight = FontWeight.Normal, fontSize = 16.sp,
                    fontFamily = Montserrat,
                    color = if(errorOccurred.value) Color.Red else Color.Black
                )
                CustomCodeTextField { isEnabled, code ->
                    enableButton = isEnabled
                    currentCode = code
                }
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

            var resendCodeValue by remember { mutableStateOf(resendCode) }

            LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
                if(resendCodeValue == true) {
                    viewModel.sendVerificationCode()
                    resendCodeValue = false
                }
                if(currentTime > 0 && isTimerRunning) {
                    delay(100L)
                    currentTime -= 100L
                }
                if(currentTime <= 0) isTimerRunning = false
            }
        }

    }
}
