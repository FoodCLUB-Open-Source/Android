package android.kotlin.foodclub.views.home

import android.graphics.drawable.shapes.Shape
import android.kotlin.foodclub.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.DatePickerDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

val montSerratFamily = FontFamily(Font(R.font.montserratregular, FontWeight.Normal))

//Common icon code to be reused
@Composable
fun ArrowIcon(size: Int, icon: Painter){
    Icon(
        painter = icon,
        contentDescription = "Back",
        modifier = Modifier
            .size(size.dp)
    )
}

//Reusing Title Text Component

@Composable
fun CommonText(text:String, size: Int, weight:FontWeight){
    Text(
        text = text,
        fontSize = size.sp,
        color = Color.Black,
        fontFamily = montserratFamily,
        fontWeight = weight,
        textAlign = TextAlign.Center
    )
}

//This is the back button and the Settings text
@Composable
fun TopBar() {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
    ) {
        Column(
            modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = { /*Goes to Page before this*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                ArrowIcon(size = 25, icon = painterResource(id = R.drawable.back_icon))
            }
        }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                CommonText(text = "Settings", size = 28, weight = FontWeight.W900)
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
            CommonText(text = userName, size = 24, weight = FontWeight.W900)
        }
    }
}

//Common Button
@Composable
fun SettingsButtons(item: String, icon: Painter){
    Button(
        onClick = {/* Goes to the corresponding item page*/},

    ){
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
        ArrowIcon(size = 25, icon = icon)
        CommonText(text = item, size = 16, weight = FontWeight.W500)
        ArrowIcon(size = 25, icon = painterResource(id=R.drawable.baseline_arrow_right_24))
        }
    }
}

/*
@Composable
fun SettingsButtons(item: String, icon: Painter){
    Button(
        onClick = {/* Goes to the corresponding item page*/},

    ){
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
        ArrowIcon(size = 25, icon = icon)
        CommonText(text = item, size = 16, weight = FontWeight.W500)
        ArrowIcon(size = 25, icon = painterResource(id=R.drawable.baseline_arrow_right_24))
        }
    }
}
 */
//different bars
@Composable
fun LogoutBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.logout),
            contentDescription = "Log out",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Log out",
            color = Color.Red,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PrivacyPolicyRow(){
    SettingRow(
        text = "Privacy Policy",
        iconId = R.drawable.logout,
        endIcon = Icons.Default.ArrowForward
    )
}
@Composable
fun ContactUsRow() {
    SettingRow(
        text = "Contact us",
        iconId = R.drawable.email,
        endIcon = Icons.Default.ArrowForward
    )
}
@Composable
fun EditProfileBar() {
    SettingBar(
        text = "Edit profile information",
        iconId = R.drawable.email,  //need to replace
        endIcon = Icons.Default.ArrowForward
    )
}
@Composable
fun PrivacySettingsBar() {
    SettingBar(
        text = "Privacy settings",
        iconId = R.drawable.email, // Replace with your icon resource
        endIcon = Icons.Default.ArrowForward
    )
}
@Composable
fun HelpAndSupportRow() {
    SettingRow(
        text = "Help & Support",
        iconId = R.drawable.email, // Replace with your icon resource
        endIcon = Icons.Default.ArrowForward
    )
}
@Composable
fun SettingBar(text: String, iconId: Int, endIcon: ImageVector){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = endIcon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SettingRow(text: String, iconId: Int, endIcon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null, // No content description for decorative icons
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = endIcon,
            contentDescription = null, // No content description for decorative icons
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
    }
}
@Composable
fun SettingPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        BoxWithBorder(
            borderColor = Color.Black,
            borderWidth = 2.dp,
            cornerRadius = 16.dp
        ) {
            Column {
                HelpAndSupportRow()
                ContactUsRow()
                PrivacyPolicyRow()
            }
        }
    }
}
/*
Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally)
 */
@Composable
fun BoxWithBorder(
    borderColor: Color,
    borderWidth: Dp,
    cornerRadius: Dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(16.dp)
    ) {
        content()
    }
}
@Composable
fun TopBar1() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, RoundedCornerShape(4.dp))
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_icon), // Replace with your arrow icon
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        CommonText(text = "Settings", size = 28, weight = FontWeight.Bold)
    }
}
    //This is the main settings function where everything is being organised
    @Composable
    fun SettingsView(){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ){

            TopBar1()
            Spacer(modifier = Modifier.height(30.dp))
            UserContent(userName = "Jake Rayner", userImage = painterResource(id=R.drawable.story_user))
          //  Spacer(modifier = Modifier.height(10.dp))
          //  SettingsButtons(item = "Edit profile information", icon = painterResource(id=R.drawable.story_user))
            Spacer(modifier = Modifier.height(8.dp))

            SettingBar(text = "Edit profile information", iconId = R.drawable.edit, endIcon = Icons.Default.ArrowForward)
            SettingBar(text = "Privacy settings", iconId = R.drawable.privacy, endIcon = Icons.Default.ArrowForward)
           // SettingBar(text = "Help & Support", iconId = R.drawable.support, endIcon = Icons.Default.ArrowForward)
            Spacer(modifier = Modifier.weight(1f))
            SettingPage()
            LogoutBar() //somehow disappeared??
        }
    }

/*
    @Composable
    fun SettingsView(){
        Column {

            TopBar()
            UserContent(userName = "Jake Rayner", userImage = painterResource(id=R.drawable.story_user))
            SettingsButtons(item = "Edit profile information", icon = painterResource(id=R.drawable.story_user))
            ButtonWithBorder()
         //   ButtonWithBorder2()
         //   ButtonWithBorder3()
            SettingBar(text = "Privacy Policy", iconId = R.drawable.insurance, endIcon = Icons.Default.ArrowForward)
            BoxWithBorder(borderColor = Color.Black, borderWidth = 2.dp, cornerRadius =16.dp ) {
                PrivacyPolicyRow()
                ContactUsRow()
                HelpAndSupportRow()
                EditProfileBar()
            }
            LogoutBar()

        }
    }
 */








