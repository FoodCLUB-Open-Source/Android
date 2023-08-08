package android.kotlin.foodclub.views.home

import android.graphics.drawable.shapes.Shape
import android.kotlin.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.DatePickerDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    //This is the main settings function where everything is being organised
    @Composable
    fun SettingsView(){
        Column {

            TopBar()
            UserContent(userName = "Jake Rayner", userImage = painterResource(id=R.drawable.story_user))
            SettingsButtons(item = "Se", icon = painterResource(id=R.drawable.story_user))
        }
    }




