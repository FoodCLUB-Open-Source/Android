package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.authentication.MainLogInAndSignUpViewModel
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun MainLogInAndSignUp(navController: NavHostController) {

    val viewModel: MainLogInAndSignUpViewModel = viewModel()

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




        Button(

            shape = RectangleShape,
            modifier = Modifier
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = viewModel.backgroundColor,
                contentColor = Color.Black
            ), contentPadding = PaddingValues(15.dp),

            onClick = {
                viewModel.changeButtonUi()
                viewModel.continueWithFacebook()

            }, interactionSource = interactionSource

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
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 25.dp)
            )
        }


        Button(

            shape = RectangleShape,
            modifier = Modifier
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = viewModel.backgroundColor,
                contentColor = Color.Black
            ), contentPadding = PaddingValues(15.dp),

            onClick = {
                viewModel.changeButtonUi()
                viewModel.continueWithInstagram()
            }, interactionSource = interactionSource1


        ) {
            Image(
                painterResource(id = R.mipmap.instagram_icon),
                contentDescription = "app_title",
                modifier = Modifier.size(17.dp)


            )



            Text(
                text = "Continue with Instagram",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 25.dp)
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
                navController.popBackStack()
                navController.navigate("home_graph")
            }


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

            horizontalArrangement = Arrangement.SpaceEvenly


        ) {

            Text(
                color = Color.Black,
                text = "Already have an account?",
                fontFamily = montserratFamily
            )


            ClickableText(
                text = AnnotatedString("Log in >"),
                onClick = {
                    viewModel.logIn()
                },
                style = TextStyle(
                    color = Color(126, 198, 11, 255),
                    fontFamily = montserratFamily
                )


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

                ClickableText(
                    text = AnnotatedString("Terms & Conditions"),
                    onClick={
                        viewModel.termsAndConditions()
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
fun MainLogInAndSignUp() {
    MainLogInAndSignUp(rememberNavController())
}
