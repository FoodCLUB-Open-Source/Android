package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.authentication.LogInWithEmailViewModel
import android.kotlin.foodclub.viewmodels.authentication.MainLogInAndSignUpViewModel
import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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


    val viewModel: LogInWithEmailViewModel = viewModel()


    var interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()


    if (!isPressed) {
        viewModel.reverseButtonUi()
    }

    var interactionSource1 = remember { MutableInteractionSource() }
    val isPressed1 by interactionSource1.collectIsPressedAsState()


    if (!isPressed1) {
        viewModel.reverseButtonUi()
    }


    val montserratFamily = FontFamily(

        Font(R.font.montserratbold, FontWeight.Bold),
        Font(R.font.montserratbold, FontWeight.SemiBold),
        Font(R.font.montserratmedium, FontWeight.Medium)

    )


    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 25.dp, end = 25.dp, top = 30.dp, bottom = 60.dp),
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
            Text(
                text = "Welcome Back!",
                fontFamily = montserratFamily,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = "Cooking just got social!",
                fontFamily = montserratFamily,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier
            )
        }
        Column(

            Modifier
                .fillMaxSize().padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {

            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .background(Color(218, 218, 218, 1))
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(),

                placeholder = {
                    Text(text = "Email", fontFamily = montserratFamily, color = Color(218, 218, 218, 228))
                },

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 128),
                    unfocusedBorderColor = Color(218, 218, 218, 110)
                )

            )

            TextField(
                value = "",
                onValueChange = {},

                placeholder = {
                    Text(text = "Password", fontFamily = montserratFamily, color = Color(218, 218, 218, 228))
                },

                modifier =
                Modifier
                    .background(Color(218, 218, 218, 1))
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(),


                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 128),
                    unfocusedBorderColor = Color(218, 218, 218, 110)
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
                    contentColor = Color.White
                ), contentPadding = PaddingValues(15.dp),

                onClick = {

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
                    text = AnnotatedString("Log in"),
                    onClick = {

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
                        .width(60.dp)
                        .border(1.dp, Color(230,230,230,255), shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = viewModel.backgroundColor,
                        contentColor = Color.Black
                    ), contentPadding = PaddingValues(15.dp),

                    onClick = {
                        viewModel.changeButtonUi()
                    }, interactionSource = interactionSource

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
                        .width(60.dp)
                        .border(1.dp, Color(230,230,230,255), shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = viewModel.backgroundColor,
                        contentColor = Color.Black
                    ), contentPadding = PaddingValues(15.dp),

                    onClick = {
                        viewModel.changeButtonUi()
                    }, interactionSource = interactionSource1

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

@Composable
@Preview
fun LogInWithEmail() {
    LogInWithEmail(rememberNavController())
}