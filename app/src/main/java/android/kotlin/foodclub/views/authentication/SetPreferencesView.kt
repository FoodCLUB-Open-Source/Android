package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.views.home.montserratFamily
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SetPreferencesView(navController: NavHostController) {
    Box(modifier = Modifier.background(Color.White))
    {
        AuthLayout(
            header = "Tell us what you like!",
            subHeading = "So we can bring the latest recipes!",
            onBackButtonClick = { navController.popBackStack() }) {
            SetPreferencesMainLayout()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SetPreferencesMainLayout() {
    val preferencesOptions = arrayListOf(
        "Italian",
        "Mexican",
        "Indian",
        "Chinese",
        "Vegan",
        "Vegetarian",
        "Paleo",
        "Japanese",
        "low-carb",
        "Vietnamese",
        "Thai",
        "Gluten-free"
    , "Llllllllllllllllllllooooooooonnnnnnnnggggggggggggg")
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

        ConfirmButton(enabled = selectedPreferences.isNotEmpty(), text = "Finish") {
            //OnClick
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
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(5.dp),
        colors = if (selectedOption) CardDefaults.cardColors(Color(0xFF7EC60B)) else CardDefaults.cardColors(
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
        border = BorderStroke(1.dp, if (selectedOption) Color.Transparent else Color(0xFFDADADA))
    ) {
        Text(
            text = text,
            fontFamily = montserratFamily,
            fontSize = 13.sp,
            color = if (selectedOption) Color.White else Color.Gray,
            modifier = Modifier.padding(5.dp),
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
            header = "Tell us what you like!",
            subHeading = "So we can bring the latest recipes!",
            onBackButtonClick = { /*TODO*/ }) {
            SetPreferencesMainLayout()
        }
    }


}
