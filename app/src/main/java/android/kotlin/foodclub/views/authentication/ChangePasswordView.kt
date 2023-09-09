package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.authentication.ChangePasswordViewModel
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foodclub.viewmodels.authentication.ForgotPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordView(navController:NavHostController, username: String?){

    val viewModel: ChangePasswordViewModel = viewModel()

    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),

        )

    val plusjakartasansFamily = FontFamily(

        Font(R.font.plusjakartasanssemibold, FontWeight.Bold),

        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {
        Button(
            shape = RectangleShape,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .width(40.dp)
                .padding(top = 30.dp).
                height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.White
            ), contentPadding = PaddingValues(5.dp),
            onClick = {
                navController.navigateUp()
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "Back",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
            )
        }

        Text(
            text = "Change Password", fontSize = 30.sp,
            fontFamily = plusjakartasansFamily,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        )


        var verificationCode by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        TextField(
            value = verificationCode,
            onValueChange = {
                verificationCode = it;
            },
            modifier = Modifier
                .background(Color(218, 218, 218, 1))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(),

            placeholder = {
                Text(text = "Code", color = Color(218, 218, 218, 228))
            },

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(218, 218, 218, 128),
                unfocusedBorderColor = Color(218, 218, 218, 110)
            )

        )

        TextField(
            value = password,
            onValueChange = {
                password = it;
            },
            modifier = Modifier
                .background(Color(218, 218, 218, 1))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(),

            placeholder = {
                Text(text = "New Password", color = Color(218, 218, 218, 228))
            },

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
                viewModel.changePassword(username!!,verificationCode,password,navController);
            }

        ) {
            Text(
                color = Color.White,
                text = "Change Password",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold
            )
        }
    }

}