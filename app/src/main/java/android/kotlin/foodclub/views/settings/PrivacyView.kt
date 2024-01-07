package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.R
import android.kotlin.foodclub.navigation.SettingsScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun PrivacySetting(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dim_16))
                .padding(top = dimensionResource(id = R.dimen.dim_80))
                .background(Color.White),
        ) {
            SettingsTopBar(
                label = stringResource(id = R.string.privacy),
                navController = navController
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_50)))

            ChangePasswordButton(navController = navController)

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_60)))

            SettingsText(
                text = stringResource(id = R.string.sent_your_data_request),
                size = 16,
                weight = FontWeight.W400,
                textAlign = TextAlign.Left,
                lineHeight = 19.5.sp
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
            weight = FontWeight.W400,
            textAlign = TextAlign.Left,
            lineHeight = 19.5.sp
        )

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
    rememberNavController()
}