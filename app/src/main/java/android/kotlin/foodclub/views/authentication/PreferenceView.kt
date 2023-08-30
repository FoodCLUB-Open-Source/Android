package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.kotlin.foodclub.views.authentication.ui.theme.FoodClubTheme
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PreferenceView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodClubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Preference()
                }
            }
        }
    }
}



@Composable
fun Preference(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()          // MAKING IT SO IT FILLS THE MAX SCREEN SIZE
            .padding(top = 100.dp)  // ADDING PADDING TO SHIFT IT UP/DOWN
    )  {

        // BACK BUTTON
        Image(
            painterResource(id = R.drawable.back_icon),
            contentDescription = "back_icon",
            modifier = Modifier
                .width(35.dp)
                .height(35.dp)
                .offset(y = (-45).dp)  // TO SHIFT THE BACK BUTTON UP/DOWN
                .offset(x = (+25).dp)  // TO SHIFT THE BACK BUTTON INWARDS
        )

        // USERNAME TEXTVIEW
        Text(
            text = "Tell us what you like!",
            fontSize = 28.sp,
            fontFamily = FontFamily.SansSerif,  // AS ITS A CLEAN CUT FONT
            fontWeight = FontWeight.Bold,       // TO MAKE IT STAND OUT
            modifier = Modifier
                .padding(start = 30.dp, end = 4.dp)
                .offset(y = (+5).dp)
        )

        // SUB TEXT VIEW
        Text(
            text = "So we can bring the latest recipes!",
            fontSize = 15.sp,
            fontFamily = FontFamily.SansSerif,  // AS ITS A CLEAN CUT FONT
            modifier = Modifier                 // TO MAKE IT STAND OUT
                .padding(bottom = 16.dp, start = 32.dp, end = 16.dp)
        )

        // **PREFERENCE FOOD BOXES HERE**

        // LIST TO ADD INTO MINI BOXES
        val foodPreferences = listOf("Italian", "Mexican", "Chinese", "Thai", "Indian", "Japanese", "Viewnamese", "Korean", "Vegan", "Low-Carb", "Vegetarian", "Paleo", "Gluten-Free")
        val chunkedFoodPreferences = foodPreferences.chunked(4)

        for (row in chunkedFoodPreferences) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (preference in row) {
                    FoodPreferenceBox(preference)
                }
            }
        }


        // BUTTON
        Button(
            onClick = {

                // ADD THE ON CLICK EVENT HERE

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 250.dp, start = 30.dp, end = 30.dp),

            shape = MaterialTheme.shapes.extraSmall,  // CHOOSE FROM XS (SHARP) TO XL (CURVED) EDGES

            // LIME GREEN BUTTON COLOR
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(126, 198, 11, 255),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Finish",
                color = Color.White // COLOR OF TEXT
            )
        }

    }
}



// BUTTON AND CONTENTS
@Composable
fun FoodPreferenceBox(text: String) = Box(
    modifier = Modifier
        .height(30.dp)
        .size(80.dp)
        .background(Color.LightGray)    // SETS THE BUTTON TO LIGHT GREY
        .clickable(onClick = {

            /* ON CLICK LISTENER STUFF GOES HERE */

        }),
    contentAlignment = Alignment.Center
) {
    Text(
        text = text,
        color = Color.DarkGray,             // MAKES THE TEXT DARK GREY SO IT'S VISIBLE
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(4.dp)

    )
}

@Preview(showBackground = true)
@Composable
fun PreferencePreview() {
    FoodClubTheme {
        Preference()
    }
}


