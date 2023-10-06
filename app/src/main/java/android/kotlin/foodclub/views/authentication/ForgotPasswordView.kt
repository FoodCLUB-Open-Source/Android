package android.kotlin.foodclub.views.authentication

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
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
import android.kotlin.foodclub.viewmodels.authentication.ForgotPasswordViewModel
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordView(navController: NavHostController) {
    val viewModel: ForgotPasswordViewModel = viewModel()

    val errorStatus by viewModel.errorStatus.observeAsState(null)

    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),

        )

    val plusjakartasansFamily = FontFamily(

        Font(R.font.plusjakartasanssemibold, FontWeight.Bold),

        )

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 30.dp, end = 30.dp, top = 120.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(35.dp)

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
            text = "Forgot Password", fontSize = 30.sp,
            fontFamily = plusjakartasansFamily,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        )
        Text(
            text = "Don’t worry! It happens. Please enter the email associated with your account.",
            fontSize = 15.sp,
            color = Color.Gray,
            fontFamily = montserratFamily,
            modifier = Modifier.padding(start = 10.dp)
        )

        var userName by remember { mutableStateOf("") }

        TextField(
            value = userName,
            isError = errorStatus!=null && userName!="",
            onValueChange = {
                userName = it

            },
            modifier = Modifier
                .background(Color(218, 218, 218, 1))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(),

            placeholder = {
                Text(text = "Username", color = Color(218, 218, 218, 228))
                },

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(218, 218, 218, 128),
                unfocusedBorderColor = Color(218, 218, 218, 110)
            )

        )
        if(errorStatus!=null && userName!=""){
              Text(
                     text = errorStatus ?: "",
                     fontSize = 11.sp,
                     color = Color.Red
                 )
        }



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
                    viewModel.sendCode(userName,navController);
            }


        ) {


            Text(
                color = Color.White,
                text = "Send Code",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold
            )
        }
    }


}

@Composable
@Preview
fun ForgotPasswordView() {
    ForgotPasswordView(rememberNavController())
}

