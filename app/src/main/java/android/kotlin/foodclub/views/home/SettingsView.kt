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

val colorGray= Color(android.graphics.Color.parseColor("#D0D0D0"))
val colorRed= Color(android.graphics.Color.parseColor("#C64E0B"))

@Composable
fun SettingsIcons(size: Int, icon: Int){
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "Back",
        modifier = Modifier
            .size(size.dp)
    )
}


@Composable
fun SettingsText(text:String, size: Int, weight:FontWeight, fontC: Color=Color.Black){
    Text(
        text = text,
        fontSize = size.sp,
        color = fontC,
        fontFamily = montserratFamily,
        fontWeight = weight,
        textAlign = TextAlign.Center
    )
}


@Composable
fun SettingsTopBar() {
   Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
    ) {
        Column(
            modifier = Modifier
                .background( colorGray, RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                onClick = { /*Goes to Page before this*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorGray,
                    contentColor = Color.Black
                ),
                modifier= Modifier
                    .background(color=Color.Transparent, RoundedCornerShape(8.dp))
            ) {
                SettingsIcons(size = 20, icon =  R.drawable.back_icon)
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(

        ) {
            SettingsText(text = "Settings", size = 28, weight = FontWeight.ExtraBold)
        }

    }
}

@Composable
fun SettingsProfile(userName: String, userImage: Painter){
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
            SettingsText(text = userName, size = 24, weight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun SettingRow(text: String, iconId: Int, fontC:  Color=Color.Black, bordersize: Int=1, bordercolor: Color= colorGray) {
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
            SettingsIcons(size = 24, icon = iconId)
            Spacer(modifier = Modifier.width(16.dp))
            SettingsText(text = text, size = 14, weight = FontWeight.W700, fontC=fontC)
            Spacer(modifier = Modifier.weight(1f))
            SettingsIcons(size = 24, icon = R.drawable.forwardarrow)
        }
    }
}

@Composable
fun SettingsView(){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
        .background(color = Color.White)

    ){

        SettingsTopBar()
        SettingsProfile(userName = "Jake Rayner", userImage = painterResource(id=R.drawable.story_user))

        Spacer(modifier = Modifier.height(60.dp))

        SettingRow(text = "Edit profile information", iconId = R.drawable.editprofile)
        SettingRow(text = "Privacy settings", iconId = R.drawable.privacysettings)

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier=Modifier
                .border(width = 1.dp,color =colorGray, shape= RoundedCornerShape(8.dp))
        ) {
            SettingRow(text = "Help & Support", iconId = R.drawable.helpandsupport, bordersize = 0, bordercolor = Color.Transparent)
            SettingRow(text = "Contact Us", iconId = R.drawable.contactus, bordersize = 0, bordercolor = Color.Transparent)
            SettingRow(text = "Privacy Policy", iconId = R.drawable.privacypolicy, bordersize = 0, bordercolor = Color.Transparent)
        }

        Spacer(modifier = Modifier.height(15.dp))

        SettingRow(text = "Log Out", iconId = R.drawable.logout, fontC=colorRed)

    }
}










