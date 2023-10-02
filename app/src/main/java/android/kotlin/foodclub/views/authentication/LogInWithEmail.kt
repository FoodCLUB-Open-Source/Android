package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.ui.theme.Montserrat
import android.kotlin.foodclub.ui.theme.PlusJakartaSans
import android.kotlin.foodclub.utils.composables.CustomPasswordTextField
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.viewmodels.authentication.LogInWithEmailViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInWithEmail(navController: NavHostController) {
    // we need to make only one view model
    val viewModel: LogInWithEmailViewModel = hiltViewModel()

    var errorMessage by remember { mutableStateOf<String?>(null) }
    val loginStatus by viewModel.loginStatus.observeAsState(null)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 30.dp, end = 30.dp, top = 80.dp, bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .width(20.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ), contentPadding = PaddingValues(5.dp),
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Back",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }

            Text(
                text = "Welcome Back!",
                fontFamily = PlusJakartaSans,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Cooking just got social!",
                fontFamily = Montserrat,
                fontSize = 18.sp,
                color = Color.Gray,

            )
        }
        Column(

            Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                var username by remember { mutableStateOf("") }
                var userPassword by remember { mutableStateOf("") }

                var filledUsername by remember { mutableStateOf(false) }
                var filledPassword by remember { mutableStateOf(false) }

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
                    fontSize = 11.sp,
                    color = Color.Red
                )

                Button(
                    onClick = { viewModel.logInUser(username, userPassword, navController) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(),
                    enabled = filledUsername && filledPassword,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7EC60B),
                        disabledContainerColor = Color(0xFFC9C9C9),
                        disabledContentColor = Color.White,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Log in", fontSize = 16.sp)
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

            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = Color.Gray,
                    text = "By using FoodCLUB you agree to our",
                    fontFamily = Montserrat,
                    fontSize = 10.sp
                )

                ClickableText(
                    text = AnnotatedString("Terms & Conditions"),
                    onClick={

                    },
                    style = TextStyle(
                        color = Color.Gray,
                        fontFamily = Montserrat,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }

//            Image(
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
//@Composable
//@Preview
//fun LogInWithEmail() {
//    LogInWithEmail(rememberNavController())
//}

