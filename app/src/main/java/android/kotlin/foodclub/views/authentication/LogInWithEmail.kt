package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.authentication.LogInWithEmailViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInWithEmail(navController: NavHostController) {
    // we need to make only one view model
    val viewModel: LogInWithEmailViewModel = viewModel()


    // API
    //val creditCards by viewModelApi.user.observeAsState(emptyList())

    // API we need to move this part in a onClick function. This will execute on runtime
    LaunchedEffect(Unit) {
      //  viewModelApi.fetchUserSignUp( /* there should be parameters since this is a post request */ )
        // after calling the idea would be to make a Log.D(response)
    }



    val montserratFamily = FontFamily(
        Font(R.font.montserratbold, FontWeight.Bold),
        Font(R.font.montserratbold, FontWeight.SemiBold),
        Font(R.font.montserratmedium, FontWeight.Medium)
    )

    val plusjakartasansFamily = FontFamily(

        Font(R.font.plusjakartasanssemibold, FontWeight.Bold),

    )

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp, start = 30.dp, end = 30.dp),
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
                fontFamily = plusjakartasansFamily,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Cooking just got social!",
                fontFamily = montserratFamily,
                fontSize = 18.sp,
                color = Color.Gray,

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
                      viewModel.logInUser(userEmail, userPassword);
                }


            ) {


                Text(
                    color = Color.White,
                    text = "Log in",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold
                )
            }


            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    color = Color.Black,
                    text = "Forgot Password?",
                    fontFamily = montserratFamily,
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
                        fontFamily = montserratFamily,
                        fontSize = 13.sp,
                        textDecoration = TextDecoration.Underline
                    )
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
                horizontalArrangement = Arrangement.spacedBy(15.dp,Alignment.CenterHorizontally),
            ) {
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .width(80.dp)
                        .border(1.dp, Color(230, 230, 230, 255), shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(218, 218, 218, 70)

                    ), contentPadding = PaddingValues(15.dp),

                    onClick = {

                    }

                ) {
                    Image(
                        painterResource(id = R.mipmap.facebook_icon),
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
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(218, 218, 218, 70)
                    ), contentPadding = PaddingValues(15.dp),

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

            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = Color.Gray,
                    text = "By using FoodCLUB you agree to our",
                    fontFamily = montserratFamily,
                    fontSize = 10.sp
                )

                ClickableText(
                    text = AnnotatedString("Terms & Conditions"),
                    onClick={

                    },
                    style = TextStyle(
                        color = Color.Gray,
                        fontFamily = montserratFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }


        }

    }

}

