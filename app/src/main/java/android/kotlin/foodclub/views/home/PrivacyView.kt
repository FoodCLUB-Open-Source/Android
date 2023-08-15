package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PrivacySetting() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Privacy",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 80.dp, bottom = 30.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .clickable(onClick = { /* Handle change password click */ })
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Change password", fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.back_icon), // Replace with your image resource ID
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "If you would like to get sent your data, make changes to it or delete it, send an email to tech@foodclub.live",
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}


//@Composable
//fun PrivacyOptionRow(title: String, onClick: () -> Unit) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(onClick = onClick)
//            .padding(16.dp)
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        Text(text = title, fontSize = 16.sp)
//        Icon(
//            painter = painterResource(id =  R.drawable.back_icon),
//            contentDescription = null,
//            tint = Color.Black,
//            modifier = Modifier.size(24.dp)
//        )
//    }
//}
//
//@Composable
//fun PrivacyOptionRow1(title: String, onClick: () -> Unit) {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(onClick = onClick)
//            .padding(16.dp),
//        shape = MaterialTheme.shapes.medium,
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(text = title, fontSize = 16.sp)
//            Icon(
//                painter = painterResource(id = R.drawable.back_icon), // Replace with your image resource ID
//                contentDescription = null,
//                tint = Color.Black,
//                modifier = Modifier.size(24.dp)
//            )
//        }
//    }
//}