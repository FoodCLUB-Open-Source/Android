package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.CustomCodeTextField
import android.kotlin.foodclub.viewModels.authentication.SignupVerificationViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.navigation.auth.AuthScreen
import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import androidx.compose.runtime.mutableLongStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.auth0.jwt.interfaces.Verification

@Composable
fun SignupVerification(
    navController: NavHostController, email: String?, username: String?, password: String?
) {
    val viewModel: SignupVerificationViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.setData(navController, username, password)
    }

    val errorOccurred = viewModel.errorOccurred.collectAsState()
    val message = viewModel.message.collectAsState()

    AuthLayout(
        header = stringResource(id = R.string.verification_title),
        subHeading = verificationSubheading(email = email),
        errorOccurred = errorOccurred.value, message = message.value,
        onBackButtonClick = {
            navController.navigate(AuthScreen.Login.route) {
                popUpTo(Graph.AUTHENTICATION) { inclusive = true }
            }
        }
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            var enableButton by remember { mutableStateOf(false) }
            var isTimerRunning by remember { mutableStateOf(true) }
            var currentTime by remember {
                mutableLongStateOf(TimeUnit.SECONDS.toMillis(62))
            }
            var currentCode by remember { mutableStateOf("") }

            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_8)),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_12))
            ) {
                CustomCodeTextField { isEnabled, code ->
                    enableButton = isEnabled
                    currentCode = code
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_18))) {
                ConfirmButton(
                    enabled = enableButton,
                    text = stringResource(id = R.string.verify),
                ) {
                    viewModel.verifyCode(currentCode)
                    enableButton = false
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = verificationCodeResponse(isTimerRunning, currentTime),
                        fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                        fontFamily = Montserrat,
                        color = Color(0xFF1E232C).copy(alpha = 0.5F)
                    )
                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.resend)),
                        onClick = {
                            if (!isTimerRunning) {
                                viewModel.sendVerificationCode()
                                currentTime = TimeUnit.SECONDS.toMillis(61)
                                isTimerRunning = true
                            }
                        },
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Montserrat,
                            fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                            color = if (isTimerRunning) Color(0xFF1E232C).copy(alpha = 0.5F)
                            else foodClubGreen
                        )
                    )
                }
            }

            LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
                if (currentTime > 0 && isTimerRunning) {
                    delay(100L)
                    currentTime -= 100L
                }
                if (currentTime <= 0) isTimerRunning = false
            }
        }
    }
}

@Composable
fun verificationSubheading(email: String?): String {
    return if (email.isNullOrEmpty()) {
        stringResource(id = R.string.verification_subheading_alt)
    } else {
        stringResource(id = R.string.verification_subheading, email)
    }
}

@Composable
fun verificationCodeResponse(isTimerRunning: Boolean, currentTime: Long): String {
    return if (isTimerRunning) {
        stringResource(id = R.string.wait_time, TimeUnit.MILLISECONDS.toSeconds(currentTime))

    } else {
        stringResource(id = R.string.no_code)
    }
}
