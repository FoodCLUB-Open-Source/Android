package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.viewModels.home.SettingsViewModel
import android.kotlin.foodclub.utils.composables.SettingsLayout
import android.kotlin.foodclub.views.home.montserratFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

val colorGray= Color(android.graphics.Color.parseColor("#D0D0D0"))
val colorRed= Color(android.graphics.Color.parseColor("#C64E0B"))

@Composable
fun SettingsView(navController: NavHostController, viewModel: SettingsViewModel){
    SettingsLayout(label = "Settings", onBackAction = { navController.navigateUp()}) {
        val screenSizeHeight =
            LocalConfiguration.current.screenHeightDp.dp //added screenSizeHeight so page is adaptable to all screen size

        SettingsProfile(
            userName = "Jake Rayner",
            userImage = painterResource(id = R.drawable.story_user)
        )
        Spacer(modifier = Modifier.height(screenSizeHeight * 0.1f))
        SettingRow(
            text = "Edit profile information",
            iconId = R.drawable.editprofile,
            fontC = Color.Black,
            borderSize = 0,
            borderColor = Color.Gray,
            destination = "SETTINGS_EDIT_PROFILE", navController
        )
        SettingRow(
            text = "Privacy settings",
            iconId = R.drawable.privacysettings,
            fontC = Color.Black,
            borderSize = 0,
            borderColor = Color.Gray,
            destination = "SETTINGS_PRIVACY", navController
        )
        Spacer(modifier = Modifier.height(screenSizeHeight * 0.03f))
        Column(modifier = Modifier.border(width = 1.dp, colorGray, RoundedCornerShape(8.dp))) {
            SettingRow(
                text = "Help & Support",
                iconId = R.drawable.helpandsupport,
                Color.Black,
                borderSize = 0,
                borderColor = Color.Transparent,
                destination = "SETTINGS_PRIVACY",
                navController = navController
            )
            SettingRow(
                text = "Contact Us",
                iconId = R.drawable.contactus,
                fontC = Color.Black,
                borderSize = 0,
                borderColor = Color.Transparent,
                destination = "SETTINGS_PRIVACY",
                navController = navController
            )
            SettingRow(
                text = "Privacy Policy",
                iconId = R.drawable.privacypolicy,
                fontC = Color.Black,
                borderSize = 0,
                borderColor = Color.Transparent,
                destination = "SETTINGS_PRIVACY",
                navController = navController
            )
        }
        Spacer(modifier = Modifier.height(screenSizeHeight * 0.03f))
        SettingRow(
            text = "Log Out",
            iconId = R.drawable.logout,
            fontC = colorRed,
            borderSize = 0,
            borderColor = Color.Black,
            destination = Graph.AUTHENTICATION,
            navController = navController,
            onClick = { viewModel.logout() }
        )
    }
}

// Common icon composable to enter the parameters to create icons in this screen
@Composable
fun SettingsIcons(size: Int, icon: Int){
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "Back",
        modifier = Modifier.size(size.dp)
    )
}

// Common text composable to create text according to the parameters entered in this screen
@Composable
fun SettingsText(
    text: String, size: Int, weight: FontWeight, fontC: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Center
){
    Text(
        text = text,
        fontSize = size.sp,
        color = fontC,
        fontFamily = montserratFamily,
        fontWeight = weight,
        textAlign = textAlign
    )
}

// The top bar composable - Back button and the "Settings" text
@Composable
fun SettingsTopBar(label:String, navController: NavController) {
   Row(
       modifier = Modifier.fillMaxWidth(),
       verticalAlignment = Alignment.CenterVertically
    ) {
        Column{
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.background(colorGray, RoundedCornerShape(8.dp)).size(35.dp),
                content = {
                    SettingsIcons(size = 20, icon =  R.drawable.back_icon)
                }
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            SettingsText(text = label, size = 28, weight = FontWeight.ExtraBold)
        }
    }
}

// The middle user profile group - the profile picture and the name. Parameters are used so this can be used to vary according to the user that is logged in
@Composable
fun SettingsProfile(userName: String, userImage: Painter){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                contentDescription = "User Images",
                painter = userImage,
                modifier = Modifier.size(120.dp).clip(RoundedCornerShape(100.dp))
            )
        }

        Spacer(modifier = Modifier.height(15.dp)) // Added spacer instead to give space between image and name

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SettingsText(text = userName, size = 24, weight = FontWeight.ExtraBold)
        }
    }
}

// A reused composable to create each setting button row
@Composable
fun SettingRow(text: String, iconId: Int, fontC:  Color = Color.Black,
               borderSize: Int = 1, borderColor: Color = colorGray, destination: String,
               navController: NavController, onClick: () -> Unit = {}) {
   val rowSize=65.dp
    Row(
        modifier = Modifier.fillMaxWidth().height(rowSize),
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            onClick = {
                onClick()
                navController.navigate(destination)
            },
            colors= ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(rowSize)
                .border(
                    width = borderSize.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                )

        ) {
            SettingsIcons(size = 24, icon = iconId)
            Spacer(modifier = Modifier.width(16.dp))
            SettingsText(text = text, size = 14, weight = FontWeight.Normal, fontC=fontC)
            Spacer(modifier = Modifier.weight(1f))
            SettingsIcons(size = 24, icon = R.drawable.forwardarrow)
        }
    }
}
@Composable
@Preview
fun SettingsView() {
    SettingsView(rememberNavController(), hiltViewModel())
}










