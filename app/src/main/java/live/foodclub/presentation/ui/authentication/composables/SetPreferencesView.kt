package live.foodclub.presentation.ui.authentication.composables

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.utils.composables.AuthLayout
import live.foodclub.utils.composables.customComponents.ConfirmButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SetPreferencesView(navController: NavHostController) {
    Box(modifier = Modifier.background(Color.White))
    {
        AuthLayout(
            header = stringResource(id = R.string.preferences_title),
            subHeading = stringResource(id = R.string.preferences_subheading),
            onBackButtonClick = { navController.popBackStack() }) {
            SetPreferencesMainLayout()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SetPreferencesMainLayout() {
    val preferencesOptions = stringArrayResource(id = R.array.preferences)

    val selectedPreferences = remember { mutableStateListOf<String>() }

    Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight())
    {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1F)
        ) {
            FlowRow {
                preferencesOptions.forEach {
                    PreferenceItem(text = it, selectedPreferences = selectedPreferences)
                }
            }
        }

        ConfirmButton(
            enabled = selectedPreferences.isNotEmpty(),
            text = stringResource(id = R.string.finish)
        ) {
            // TODO add OnClick
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceItem(text: String, selectedPreferences: MutableList<String>) {
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_3)),
        colors = if (selectedOption) CardDefaults.cardColors(foodClubGreen) else CardDefaults.cardColors(
            Color(0xFFEEEEEE)
        ),
        onClick = {
            onOptionSelected(!selectedOption)
            if (selectedPreferences.contains(text)) {
                selectedPreferences.remove(text)
            } else {
                selectedPreferences.add(text)
            }
        },
        border = BorderStroke(dimensionResource(id = R.dimen.dim_1), if (selectedOption) Color.Transparent else Color(0xFFDADADA))
    ) {
        Text(
            text = text,
            fontFamily = Montserrat,
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            color = if (selectedOption) Color.White else Color.Gray,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_8)),
            maxLines = 1
        )
    }
}

@Composable
@Preview
fun SetPreferencesPreview() {

    Box(modifier = Modifier.background(Color.White))
    {
        AuthLayout(
            header = stringResource(id = R.string.preferences_title),
            subHeading = stringResource(id = R.string.preferences_subheading),
            onBackButtonClick = { /*TODO*/ }) {
            SetPreferencesMainLayout()
        }
    }


}
