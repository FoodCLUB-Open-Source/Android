package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.authentication.MainLogInAndSignUpViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.example.foodclub.viewmodels.onboarding.MenuViewModel



@Composable
fun MainLogInAndSignUp(navController: NavHostController) {

    val viewModel: MainLogInAndSignUpViewModel = viewModel()

    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),

        )

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {

        Image(
            painterResource(id = R.drawable.app_logo),
            contentDescription = "app_logo",
            modifier = Modifier
                .width(170.dp)
                .height(170.dp)
                .padding(top = 70.dp)
        )


        Image(
            painterResource(id = R.drawable.app_title),
            contentDescription = "app_title",
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)

        )




        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(15.dp), horizontalArrangement = Arrangement.SpaceAround


        ) {
            Image(
                painterResource(id = R.drawable.facebook_icon),
                contentDescription = "app_title",
                modifier = Modifier.size(17.dp)


            )

            Text(
                text = "Continue with Facebook",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(15.dp), horizontalArrangement = Arrangement.SpaceAround


        ) {
            Image(
                painterResource(id = R.drawable.instagram_icon),
                contentDescription = "app_title",
                modifier = Modifier.size(17.dp)


            )

            

            Text(
                text = "Continue with Instagram",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(Color(115, 180, 10))
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center,


            ) {


            Text(
                color = Color.White,
                text = "Sign Up",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween


        ) {

            Text(
                color = Color.Black,
                text = "Already have an account?",
                fontFamily = montserratFamily
            )


            Text(
                color = Color(152, 209, 60),
                text = "Log in",
                fontFamily = montserratFamily
            )


        }


        Column(
            Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom


        ) {

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
fun MainLogInAndSignUp(){
    MainLogInAndSignUp(rememberNavController())
}
