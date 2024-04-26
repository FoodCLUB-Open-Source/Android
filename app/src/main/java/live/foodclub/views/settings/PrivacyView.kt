package live.foodclub.views.settings

import live.foodclub.R
import live.foodclub.navigation.SettingsScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

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
    SettingRow(text = stringResource(id = R.string.change_password), size = 16 , destination = SettingsScreen.ChangePassword.route , navController = navController)
}
@Composable
@Preview
fun PrivacySetting() {
    rememberNavController()
}