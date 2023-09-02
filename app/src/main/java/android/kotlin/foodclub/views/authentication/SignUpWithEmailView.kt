package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.viewmodels.authentication.LogInWithEmailViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodclub.viewmodels.authentication.SignupViewWithEmailViewModel
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpWithEmailView(navController: NavHostController) {


    val viewModel: SignupViewWithEmailViewModel = viewModel()

    val montserratFamily = FontFamily(

        Font(R.font.montserratbold, FontWeight.Bold),
        Font(R.font.montserratmedium, FontWeight.Medium)

    )

    val plusjakartasansFamily = FontFamily(

        Font(R.font.plusjakartasanssemibold, FontWeight.Bold),


        )


    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 25.dp, end = 25.dp, top = 30.dp, bottom = 50.dp),
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
                    .width(30.dp)
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
                text = "New Here?",
                fontFamily = plusjakartasansFamily,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 10.dp)

            )
            Text(
                text = "No Problem!",
                fontFamily = plusjakartasansFamily,
                fontSize = 32.sp,

                )
        }
        Column(

            Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            var userEmail by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var userPassword by remember { mutableStateOf("") }

            TextField(
                value = userEmail,
                onValueChange = {
                    userEmail = it;
                },
                modifier = Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(218, 218, 218, 70))
                    .fillMaxWidth(),

                placeholder = {
                    Text(
                        text = "Email",
                        fontFamily = montserratFamily,
                        color = Color(218, 218, 218, 238)
                    )
                },

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 158),
                    unfocusedBorderColor = Color(218, 218, 218, 140)
                )

            )
            TextField(
                value = username,
                onValueChange = {
                    username = it;
                },
                modifier = Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(218, 218, 218, 70))
                    .fillMaxWidth(),

                placeholder = {
                    Text(
                        text = "Username",
                        fontFamily = montserratFamily,
                        color = Color(218, 218, 218, 238)
                    )
                },

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 158),
                    unfocusedBorderColor = Color(218, 218, 218, 140)
                )

            )

            TextField(
                value = userPassword,
                onValueChange = {
                    userPassword = it;
                },

                placeholder = {
                    Text(
                        text = "Password",
                        fontFamily = montserratFamily,
                        color = Color(218, 218, 218, 238)
                    )
                },

                modifier =
                Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(218, 218, 218, 70))
                    .fillMaxWidth(),


                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 158),
                    unfocusedBorderColor = Color(218, 218, 218, 140)
                )


            )

//            val coroutineScope = rememberCoroutineScope()




            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(126, 198, 11, 255),

                    ), contentPadding = PaddingValues(15.dp),

                onClick = {
//                cooutineScope.launch {
                    val requestBody = UserSignUpInformation(username,userEmail,userPassword)
                    viewModel.signUpUser(requestBody, navController)

//                }

                }


            ) {


                Text(
                    color = Color.White,
                    text = "Sign Up",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold
                )
            }


            Image(
                painterResource(id = R.drawable.login_with),
                contentDescription = "app_title",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)

            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
            ) {
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .width(80.dp)
                        .border(1.dp, Color(230, 230, 230, 255), shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(218, 218, 218, 70)

                    ),
                    contentPadding = PaddingValues(15.dp),

                    onClick = {

                    }

                ) {
                    Image(
                        painterResource(id = R.drawable.facebook_ic),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                }

                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .width(80.dp)
                        .border(1.dp, Color(230, 230, 230, 255), shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp)),
                    contentPadding = PaddingValues(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(218, 218, 218, 70)

                    ),

                    onClick = {

                    }

                ) {
                    Image(
                        painterResource(id = R.drawable.google_ic),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                }

                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .width(80.dp)
                        .border(1.dp, Color(230, 230, 230, 255), shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp)),
                    contentPadding = PaddingValues(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(218, 218, 218, 70)

                    ),

                    onClick = {

                    }

                ) {
                    Image(
                        painterResource(id = R.mipmap.instagram_icon),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                }
            }

        }

    }


}