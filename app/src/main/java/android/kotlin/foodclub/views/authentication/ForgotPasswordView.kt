package com.example.foodclub.views.authentication

import android.kotlin.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodclub.viewmodels.authentication.ForgotPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordView(navController: NavHostController) {
    val viewModel: ForgotPasswordViewModel = viewModel()

    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),

        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {
        Image(
            painterResource(id = R.drawable.back_icon),
            contentDescription = "back_icon",
            modifier = Modifier
                .width(35.dp)
                .height(35.dp)

        )

        Text(
            text = "Forgot Password", fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = montserratFamily,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        )
        Text(
            text = "Don’t worry! It happens. Please enter the email associated with your account.",
            fontSize = 15.sp,
            color = Color.Gray,
            fontFamily = montserratFamily,
            modifier = Modifier.padding(start = 10.dp)
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},

            placeholder = {
                Text(text = "Email", color = Color(218, 218, 218, 228))
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(126, 198, 11, 255))
                .padding(15.dp), horizontalArrangement = Arrangement.Center


        ) {


            Text(
                text = "Send Codex",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }


}

@Composable
@Preview
fun ForgotPasswordView() {
    ForgotPasswordView(rememberNavController())
}