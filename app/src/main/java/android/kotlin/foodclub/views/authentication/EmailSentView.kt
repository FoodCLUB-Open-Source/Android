package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailSentView(navController: NavHostController) {

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
            text = "Email Sent", fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = montserratFamily,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        )
        Text(
            text = "We’ve sent an email with password reset link to PersonalEmail@xxx.com",
            fontSize = 15.sp,
            color = Color.Gray,
            fontFamily = montserratFamily,
            modifier = Modifier.padding(start = 10.dp)
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
    }

}

@Composable
@Preview
fun EmailSentView(){
    EmailSentView(rememberNavController())
}