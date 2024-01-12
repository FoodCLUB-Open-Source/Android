package android.kotlin.foodclub.views.authentication.signupVerification

import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.CustomCodeTextField
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.navigation.auth.AuthScreen
import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.viewModels.authentication.signupVerification.SignupVerificationEvents
import androidx.activity.compose.BackHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource

@Composable
fun SignupVerification(
    navController: NavHostController,
    email: String?,
    username: String?,
    password: String?,
    state : SignupVerificationState,
    events : SignupVerificationEvents,
) {

    LaunchedEffect(Unit) {
        events.setData(navController, username, password)
    }

    BackHandler {
        navController.navigate(AuthScreen.MainLogInAndSignUp.route) {
            popUpTo(Graph.AUTHENTICATION) { inclusive = true }
        }
    }

    AuthLayout(
        header = stringResource(id = R.string.verification_title),
        subHeading = verificationSubheading(email = email),
        errorOccurred = state.errorOccurred,
        message = state.message,
        onBackButtonClick = {
            navController.navigate(AuthScreen.MainLogInAndSignUp.route) {
                popUpTo(Graph.AUTHENTICATION) { inclusive = true }
            }
        }
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            var enableButton by remember { mutableStateOf(false) }
            var isTimerRunning by remember { mutableStateOf(true) }
            var currentTime by remember {
                mutableStateOf(TimeUnit.SECONDS.toMillis(62))
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
                    events.verifyCode(
                        code = currentCode,
                        navController = navController
                    )

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
                                events.sendVerificationCode(navController = navController)
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
