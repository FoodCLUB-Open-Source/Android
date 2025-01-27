package live.foodclub.presentation.ui.authentication.loginWithEmail

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.presentation.navigation.auth.AuthScreen
import live.foodclub.utils.composables.AuthLayout
import live.foodclub.utils.composables.customComponents.ConfirmButton
import live.foodclub.utils.composables.customComponents.CustomPasswordTextField
import live.foodclub.utils.composables.customComponents.CustomTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LogInWithEmail(
    navController: NavHostController,
    events: LoginWithEmailEvents,
    state: LoginState
) {

    var username by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    var filledUsername by remember { mutableStateOf(false) }
    var filledPassword by remember { mutableStateOf(false) }

    AuthLayout(
        header = stringResource(id = R.string.welcome_back),
        subHeading = stringResource(id = R.string.welcome_back_subheading),
        onBackButtonClick = { navController.popBackStack() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy( dimensionResource(id = R.dimen.dim_16)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_4))) {
                CustomTextField(initialValue = username,
                    placeholder = stringResource(id = R.string.username),
                    keyboardType = KeyboardType.Text,
                    onCorrectnessStateChange = { filledUsername = !filledUsername },
                    onValueChange = { username = it }
                )

                CustomPasswordTextField(
                    placeholder = stringResource(id = R.string.password),
                    strengthValidation = false,
                    onCorrectnessStateChange = { filledPassword = !filledPassword },
                    onValueChange = { userPassword = it })

                Text(
                    text = state.loginStatus ?: "",
                    fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                    fontFamily = Montserrat,
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dim_10), vertical = dimensionResource(
                        id = R.dimen.dim_0
                    ))
                )

                ConfirmButton(
                    enabled = filledUsername && filledPassword,
                    text = stringResource(id = R.string.log_in),
                ) {
                    events.logInUser(username, userPassword, navController)
                }
            }

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    color = Color.Black,
                    text = stringResource(id = R.string.forgot_password_question),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                    modifier = Modifier.padding(end =dimensionResource(id = R.dimen.dim_5))
                )
                ClickableText(
                    text = AnnotatedString(stringResource(R.string.reset_here)),
                    onClick = {
                        navController.navigate(route = "${AuthScreen.Forgot.route}?email=$username")
                    },
                    style = TextStyle(
                        color = foodClubGreen,
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

