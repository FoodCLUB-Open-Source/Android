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
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CreateUsernameView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            FoodClubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UsernameView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // FOR SEARCH BAR
@Composable
fun UsernameView(modifier: Modifier = Modifier.fillMaxSize()) {
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
            text = "Create a Username!",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,  // AS ITS A CLEAN CUT FONT
            fontWeight = FontWeight.Bold,       // TO MAKE IT STAND OUT
            modifier = Modifier
                .padding(start = 30.dp, end = 4.dp)
                .offset(y = (+5).dp)
        )

        // SUB TEXT VIEW
        Text(
            text = "So everyone can find you!",
            fontSize = 15.sp,
            fontFamily = FontFamily.SansSerif,  // AS ITS A CLEAN CUT FONT
            modifier = Modifier                 // TO MAKE IT STAND OUT
                .padding(bottom = 16.dp, start = 32.dp, end = 16.dp)
        )

        // TEXT FIELD STUFF
        OutlinedTextField(
            value = "",
            onValueChange = {},                             // NEEDED OR ELSE IT'LL SHOW ERROR
            placeholder = { Text("Username...") },     // TEXT HINT
            singleLine = true,                              // MAKING IT A SINGLE LINE
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)        // PADDING TO SHIFT THE TEXT FIELD INWARDS
        )

        // BUTTON
        Button(
            onClick = {

                // ADD THE ON CLICK EVENT HERE

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 30.dp, end = 30.dp),

            shape = MaterialTheme.shapes.extraSmall,  // CHOOSE FROM XS (SHARP) TO XL (CURVED) EDGES

            // LIME GREEN BUTTON COLOR
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(126, 198, 11, 255),
                contentColor = Color.White
            ) 
        ) {
            Text(
                text = "Create",
                color = Color.White // COLOR OF TEXT
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun UsernameViewPreview() {
    FoodClubTheme {
        UsernameView()
    }
}