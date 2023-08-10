package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Common icon code to be reused
@Composable
fun AppIcons(size: Int, icon: Int){
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "Back",
        modifier = Modifier
            .size(size.dp)
    )
}


//Reusing Title Text Component
@Composable
fun CommonText(text:String, size: Int, weight:FontWeight, fontC: Color=Color.Black){
    Text(
        text = text,
        fontSize = size.sp,
        color = fontC,
        fontFamily = montserratFamily,
        fontWeight = weight,
        textAlign = TextAlign.Center
    )
}

// Top Bar - Icon inside Button component so it is clickable
@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                onClick = { /*Goes to Page before this*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                modifier= Modifier
                    .background(color=Color.Transparent, RoundedCornerShape(8.dp))
            ) {
                AppIcons(size = 20, icon =  R.drawable.back_icon)
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(

        ) {
            CommonText(text = "Settings", size = 28, weight = FontWeight.ExtraBold)
        }

    }
}

@Composable
fun UserContent(userName: String, userImage: Painter){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(top = 30.dp, start = 125.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
                Image(
                    contentDescription = "User Images",
                    painter = userImage,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(100.dp))
                )
        }
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            CommonText(text = userName, size = 24, weight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun SettingRow(text: String, iconId: Int, fontC:  Color=Color.Black, bordersize: Int=1, bordercolor: Color=Color.LightGray) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            onClick = {/*Goes to the corresponding screen*/},
            colors= ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Black),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(55.dp)
                .border(width = bordersize.dp,color =bordercolor, shape= RoundedCornerShape(8.dp))

        ) {
            AppIcons(size = 24, icon = iconId)
            Spacer(modifier = Modifier.width(16.dp))
            CommonText(text = text, size = 14, weight = FontWeight.W700, fontC=fontC)
            Spacer(modifier = Modifier.weight(1f))
            AppIcons(size = 24, icon = R.drawable.forwardarrow)
        }
    }
}

//This is the main settings function where everything is being organised
@Composable
fun SettingsView(){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
        .background(color = Color.White)

    ){

        TopBar()
        UserContent(userName = "Jake Rayner", userImage = painterResource(id=R.drawable.story_user))

        Spacer(modifier = Modifier.height(60.dp))

        SettingRow(text = "Edit profile information", iconId = R.drawable.editprofile)
        SettingRow(text = "Privacy settings", iconId = R.drawable.privacysettings)

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier=Modifier
                .border(width = 1.dp,color =Color.LightGray, shape= RoundedCornerShape(8.dp))
        ) {
            SettingRow(text = "Help & Support", iconId = R.drawable.helpandsupport, bordersize = 0, bordercolor = Color.Transparent)
            SettingRow(text = "Contact Us", iconId = R.drawable.contactus, bordersize = 0, bordercolor = Color.Transparent)
            SettingRow(text = "Privacy Policy", iconId = R.drawable.privacypolicy, bordersize = 0, bordercolor = Color.Transparent)
        }
        Spacer(modifier = Modifier.height(15.dp))

        SettingRow(text = "Log Out", iconId = R.drawable.logout, fontC=Color.Red)

    }
}

////different bars
//@Composable
//fun LogoutBar(){
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(56.dp)
//            .background(MaterialTheme.colorScheme.primary)
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(painter = painterResource(id = R.drawable.logout),
//            contentDescription = "Log out",
//            tint = Color.White,
//            modifier = Modifier.size(24.dp)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text = "Log out",
//            color = Color.Red,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//@Composable
//fun PrivacyPolicyRow(){
//    SettingRow(
//        text = "Privacy Policy",
//        iconId = R.drawable.logout,
//    )
//}
//@Composable
//fun ContactUsRow() {
//    SettingRow(
//        text = "Contact us",
//        iconId = R.drawable.email,
//    )
//}
//@Composable
//fun EditProfileBar() {
//    SettingBar(
//        text = "Edit profile information",
//        iconId = R.drawable.email,  //need to replace
//        endIcon = Icons.Default.ArrowForward
//    )
//}
//@Composable
//fun PrivacySettingsBar() {
//    SettingBar(
//        text = "Privacy settings",
//        iconId = R.drawable.email, // Replace with your icon resource
//        endIcon = Icons.Default.ArrowForward
//    )
//}
//@Composable
//fun HelpAndSupportRow() {
//    SettingRow(
//        text = "Help & Support",
//        iconId = R.drawable.email, // Replace with your icon resource
//    )
//}
////CHECK IF NEEDED
//@Composable
//fun SettingBar(text: String, iconId: Int, endIcon: ImageVector){
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(56.dp)
//            .padding(horizontal = 16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            painter = painterResource(id = iconId),
//            contentDescription = null,
//            tint = Color.Black,
//            modifier = Modifier.size(24.dp)
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(text = text,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Normal
//        )
//        Spacer(modifier = Modifier.weight(1f))
//        Icon(
//            imageVector = endIcon,
//            contentDescription = null,
//            tint = Color.Black,
//            modifier = Modifier.size(24.dp)
//        )
//    }
//}

////CHECK IF NEEDED
//@Composable
//fun BoxWithBorder(
//    borderColor: Color,
//    borderWidth: Dp,
//    cornerRadius: Dp,
//    content: @Composable BoxScope.() -> Unit
//) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .border(
//                width = borderWidth,
//                color = borderColor,
//                shape = RoundedCornerShape(cornerRadius)
//            )
//            .padding(16.dp)
//    ) {
//        content()
//    }
//}










