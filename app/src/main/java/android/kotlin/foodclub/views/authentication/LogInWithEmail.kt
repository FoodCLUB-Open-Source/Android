package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.utils.composables.CustomPasswordTextField
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.viewModels.authentication.LogInWithEmailViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LogInWithEmail(
    navController: NavHostController,
    viewModel: LogInWithEmailViewModel
) {
    val loginStatus by viewModel.loginStatus.observeAsState(null)

    var username by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    var filledUsername by remember { mutableStateOf(false) }
    var filledPassword by remember { mutableStateOf(false) }

    AuthLayout(header = "Welcome back!",
        subHeading = "Cooking just got social!",
        onBackButtonClick = { navController.popBackStack() }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                CustomTextField(initialValue = username,
                    placeholder = "Username", keyboardType = KeyboardType.Text,
                    onCorrectnessStateChange = { filledUsername = !filledUsername },
                    onValueChange = { username = it }
                )

                CustomPasswordTextField(
                    placeholder = "Password",
                    strengthValidation = false,
                    onCorrectnessStateChange = { filledPassword = !filledPassword },
                    onValueChange = { userPassword = it })

                Text(
                    text = loginStatus ?: "",
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                ConfirmButton(
                    enabled = filledUsername && filledPassword,
                    text = "Log in") {
                    viewModel.logInUser(username, userPassword, navController)
                }
            }

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    color = Color.Black,
                    text = "Forgot Password?",
                    fontFamily = Montserrat,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(end = 5.dp)
                )
                ClickableText(
                    text = AnnotatedString("Reset here"),
                    onClick = {
                        navController.navigate("FORGOT")
                    },
                    style = TextStyle(
                        color = Color(152, 209, 60),
                        fontFamily = Montserrat,
                        fontSize = 13.sp,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        //    Image(
//                painterResource(id = R.drawable.login_with),
//                contentDescription = "app_title",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(15.dp)
//
//
//            )

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(15.dp,Alignment.CenterHorizontally),
//            ) {
//                Button(
//                    shape = RectangleShape,
//                    modifier = Modifier
//                        .width(80.dp)
//                        .border(1.dp, Color(230, 230, 230, 255), shape = RoundedCornerShape(10.dp))
//                        .clip(RoundedCornerShape(10.dp)),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(218, 218, 218, 70)
//
//                    ), contentPadding = PaddingValues(15.dp),
//
//                    onClick = {
//
//                    }
//
//                ) {
//                    Image(
//                        painterResource(id = R.mipmap.facebook_icon),
//                        contentDescription = "",
//                        Modifier.size(20.dp)
//                    )
//                }
//
//                Button(
//                    shape = RectangleShape,
//                    modifier = Modifier
//                        .width(80.dp)
//                        .border(1.dp, Color(230, 230, 230, 255), shape = RoundedCornerShape(10.dp))
//                        .clip(RoundedCornerShape(10.dp)),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(218, 218, 218, 70)
//                    ), contentPadding = PaddingValues(15.dp),
//
//                    onClick = {
//
//                    }
//
//                ) {
//                    Image(
//                        painterResource(id = R.mipmap.instagram_icon),
//                        contentDescription = "",
//                        Modifier.size(20.dp)
//                    )
//                }
//            }
        }

    }
}

