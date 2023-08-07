package android.kotlin.foodclub.views.home

import android.graphics.drawable.Icon
import android.kotlin.foodclub.R
import android.provider.CalendarContract
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults.ContainerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val montSerratFamily = FontFamily(Font(R.font.montserratregular, FontWeight.Normal))

//This is the main settings function where everything is being organised
@Composable
fun SettingsView(){
    Column {
        TopBar()
    }
}

//This is the back button and the Settings text
@Composable
fun TopBar(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top=80.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = { /*Goes to Page before this*/ },
//                modifier = Modifier.padding(end = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor=Color.Transparent, contentColor = Color.Black)
                ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Back",
                    
//                    colors = IconButtonDefaults.iconButtonColors(Color.Black)
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings",
                fontSize = 28.sp,
                color = Color.Black,
                fontFamily = montserratFamily

            )
        }
    }
}

