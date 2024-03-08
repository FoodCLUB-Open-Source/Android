package android.kotlin.foodclub.views.home.profile

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.views.settings.SettingsIcons
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpAndSupportTopBar(
    navController: NavController
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.help_and_support),
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                SettingsIcons(size = dimensionResource(id = R.dimen.dim_30).value.toInt(), icon = R.drawable.back_icon)
            }
        },
    )
}