package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignUpWithEmailView(navController: NavHostController){


    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),
    )




}

@Composable
@Preview
fun SignUpWithEmailView(){
    SignUpWithEmailView(rememberNavController())
}