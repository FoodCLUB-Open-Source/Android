package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


//The main function of this PrivacyView file. This arranges all components to build the screen
@Composable
fun PrivacySetting(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 80.dp)
    ) {

        SettingsTopBar(label = "Privacy", navController)
        Spacer(modifier = Modifier.height(50.dp))
        ChangePasswordButton()
        Spacer(modifier = Modifier.height(60.dp))
        SettingsText(
            text = "If you would like to get sent your data, make changes to it or delete it, send an email to tech@foodclub.live",
            size = 16,
            weight = FontWeight.W600,
            textAlign = TextAlign.Left
        )
    }
}

// The Change Password button of this screen
@Composable
fun ChangePasswordButton(){
    Row(
        modifier = Modifier
            .clickable { /* TO DO */ }
            .fillMaxWidth()
            .background(Color.Transparent)
    ){
        SettingsText(text = "Change Password", size = 16, weight = FontWeight.W600, textAlign = TextAlign.Left)
        Spacer(modifier = Modifier.weight(1f))
        SettingsIcons(size = 20, icon = R.drawable.forwardarrow)
    }

}
