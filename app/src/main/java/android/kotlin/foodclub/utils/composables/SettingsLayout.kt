package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.ui.theme.Montserrat
import android.kotlin.foodclub.views.home.colorGray
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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


//The main function of this PrivacyView file. This arranges all components to build the screen
@Composable
fun SettingsLayout(label: String, onBackAction: () -> Unit, content: @Composable() (() -> Unit)) {
    Box(
        modifier =Modifier.fillMaxWidth()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).padding(top = 80.dp)
                .background(Color.White),
        ) {
            SettingsTopBar(label = label, onBackAction = onBackAction)
            Spacer(modifier = Modifier.height(50.dp))
            content()
        }
    }
}

// The top bar composable - Back button and the "Settings" text
@Composable
fun SettingsTopBar(label: String, onBackAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column{
            IconButton(
                onClick = { onBackAction() },
                modifier = Modifier
                    .background(color = colorGray, RoundedCornerShape(8.dp))
                    .size(35.dp),
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

// Common icon composable to enter the parameters to create icons in this screen
@Composable
fun SettingsIcons(size: Int, icon: Int){
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "Back",
        modifier = Modifier
            .size(size.dp)
    )
}

// Common text composable to create text according to the parameters entered in this screen
@Composable
fun SettingsText(text:String, size: Int, weight:FontWeight, fontC: Color = Color.Black, textAlign: TextAlign = TextAlign.Center){
    Text(
        text = text,
        fontSize = size.sp,
        color = fontC,
        fontFamily = Montserrat,
        fontWeight = weight,
        textAlign = textAlign
    )
}