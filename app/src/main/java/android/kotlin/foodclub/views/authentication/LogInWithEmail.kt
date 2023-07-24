package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.authentication.LogInWithEmailViewModel
import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInWithEmail(navController: NavHostController) {

    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),

        )


    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp),
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
            Image(
                painterResource(id = R.drawable.back_icon),
                contentDescription = "back_icon",
                modifier = Modifier
                    .width(35.dp)
                    .height(35.dp)

            )

            Text(
                text = "Welcome Back!",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )


            Text(
                text = "Cooking just got social!",
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 10.dp)
            )


        }

        Column(

            Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {

            OutlinedTextField(

                value = "",
                onValueChange = {},

                Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()

                    .background(Color(218, 218, 218, 50)),


                placeholder = {
                    Text(text = "Email", color = Color(218, 218, 218, 228))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 128),
                    unfocusedBorderColor = Color(218, 218, 218, 110)
                )

            )

            OutlinedTextField(
                value = "",
                onValueChange = {},

                placeholder = {
                    Text(text = "Password", color = Color(218, 218, 218, 228))
                },


                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(218, 218, 218, 80)),


                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 128),
                    unfocusedBorderColor = Color(218, 218, 218, 110)
                )


            )

//            TextButton(onClick = {  },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(Color(126, 198, 11, 255))
//
//
//                    ) {
//                Text(text = "Log in", color = Color.White)
//            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(126, 198, 11, 255))
                    .padding(15.dp), horizontalArrangement = Arrangement.Center


            ) {


                Text(
                    text = "Log in",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.Center


            ) {

                Text(
                    color = Color.Black,
                    text = "Forgot Password?",
                    fontFamily = montserratFamily,
                    fontSize = 13.sp

                )


                Text(

                    color = Color(152, 209, 60),
                    text = "Reset here",
                    fontFamily = montserratFamily,
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline
                )
            }

            Image(
                painterResource(id = R.drawable.login_with),
                contentDescription = "app_title",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)


            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color(218, 218, 218, 110), RoundedCornerShape(10.dp))
                        .padding(top = 15.dp, start = 35.dp, end = 35.dp, bottom = 15.dp)
                ) {
                    Image(painterResource(id = R.mipmap.facebook_icon), contentDescription = "",Modifier.size(25.dp))
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color(218, 218, 218, 110), RoundedCornerShape(10.dp))
                        .padding(top = 15.dp, start = 35.dp, end = 35.dp, bottom = 15.dp)

                ) {
                    Image(painterResource(id = R.mipmap.instagram_icon), contentDescription = "",Modifier.size(25.dp))
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

                Text(
                    color = Color.Gray,
                    text = "Terms & Conditions",
                    fontFamily = montserratFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            }



        }

    }

}
@Composable
@Preview
fun LogInWithEmail(){
    LogInWithEmail(rememberNavController())
}