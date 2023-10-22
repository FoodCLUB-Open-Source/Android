package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.navigation.SettingsScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


//The main function of this PrivacyView file. This arranges all components to build the screen
@Composable
fun PrivacySetting(navController: NavController) {
    Box(modifier =Modifier.fillMaxWidth().background(Color.White),) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).padding(top = 80.dp)
                .background(Color.White),
        ) {
            SettingsTopBar(label = "Privacy", navController)
            Spacer(modifier = Modifier.height(50.dp))
            ChangePasswordButton(navController = navController)
            Spacer(modifier = Modifier.height(60.dp))
            SettingsText(
                text = "If you would like to get sent your data, make changes to it or delete it, send an email to tech@foodclub.live",
                size = 16,
                weight = FontWeight.W600,
                textAlign = TextAlign.Left
            )
        }
    }
}

// The Change Password button of this screen
@Composable
fun ChangePasswordButton(navController: NavController){
    Row(modifier = Modifier
            .clickable { navController.navigate(SettingsScreen.ChangePassword.route) }
            .fillMaxWidth().background(Color.Transparent)
    ){
        SettingsText(text = "Change Password", size = 16, weight = FontWeight.W600, textAlign = TextAlign.Left)
        Spacer(modifier = Modifier.weight(1f))
        SettingsIcons(size = 20, icon = R.drawable.forwardarrow)
    }

}
@Composable
@Preview
fun PrivacySetting() {
    PrivacySetting(rememberNavController())
}