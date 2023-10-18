package android.kotlin.foodclub.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun BlackArrow() {
    //need to fix the arrow thing
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(4.dp))
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack, // Arrow pointing to the left
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
fun SettingNotifications(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BlackArrow()
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        NotificationRow("General notifications")
        Text(
            text = "System & Service update",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        NotificationRow("App updates")
        NotificationRow("Payment request")
    }
}




@Composable
fun NotificationRow(title: String){
    var isNotificationEnabled by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
        )
        //change the color of the switch button to light green

        Switch(
            checked = isNotificationEnabled,
            onCheckedChange = {
                isNotificationEnabled = it
            },
            //the color should be more lighter green, when it is turned on and the thumb should be white
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
               // checkedTrackColor = Color(0xFF4CAF50),
                checkedTrackColor = Color(0xFF4CAF50),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFBDBDBD),
            ),
            )
    }
}

@Composable
@Preview
fun SettingNotificationsPreview(){
    SettingNotifications(navController = rememberNavController())
}