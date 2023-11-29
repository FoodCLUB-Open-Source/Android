package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.navigation.SettingsScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun PrivacySetting(navController: NavController) {
    Box(modifier = Modifier.fillMaxWidth().background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 80.dp)
                .background(Color.White),
        ) {
            SettingsTopBar(
                label = stringResource(id = R.string.privacy),
                navController = navController
            )

            Spacer(modifier = Modifier.height(50.dp))

            ChangePasswordButton(navController = navController)

            Spacer(modifier = Modifier.height(60.dp))

            SettingsText(
                text = stringResource(id = R.string.sent_your_data_request),
                size = 16,
                weight = FontWeight.W600,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
fun ChangePasswordButton(navController: NavController){
    Row(modifier = Modifier
            .clickable { navController.navigate(SettingsScreen.ChangePassword.route) }
            .fillMaxWidth()
            .background(Color.Transparent)
    ){
        SettingsText(
            text = stringResource(id = R.string.change_password),
            size = 16,
            weight = FontWeight.W600,
            textAlign = TextAlign.Left)

        Spacer(modifier = Modifier.weight(1f))

        SettingsIcons(
            size = 20,
            icon = R.drawable.forwardarrow
        )
    }

}
@Composable
@Preview
fun PrivacySetting() {
    PrivacySetting(rememberNavController())
}