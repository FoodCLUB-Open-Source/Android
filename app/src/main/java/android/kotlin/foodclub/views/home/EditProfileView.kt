package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.utils.composables.ConfirmButton
import android.kotlin.foodclub.utils.composables.CustomTextField
import android.kotlin.foodclub.utils.composables.SettingsLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

val colorLightGray = Color(0xFFDADADA)

//The main function of this EditProfile file. This arranges all components to build the screen
@Composable
fun EditProfileSetting(navController: NavController){
    
    SettingsLayout(label = "Edit Profile", onBackAction = { navController.navigateUp()}) {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        CustomTextField(placeholder = "Username", keyboardType = KeyboardType.Text, onValueChange ={ username= it })
        Spacer(modifier = Modifier.height(12.dp))
        CustomTextField(placeholder = "Email", keyboardType = KeyboardType.Text, onValueChange ={ email= it })
        Spacer(modifier = Modifier.height(16.dp))
        ConfirmButton(enabled = true, text = "Save"){

        }
    }
//    var username by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White),
//    ) {
//        Column(
//            verticalArrangement = Arrangement.Top,
//            modifier = Modifier
//                .padding(top = 80.dp)
//                .fillMaxSize()
//                .padding(16.dp)
//                .background(Color.White),
//        ) {
//            SettingsTopBar("Edit Profile", navController)
//            Spacer(modifier = Modifier.height(12.dp))
//            CustomTextField(placeholder = "Username", keyboardType = KeyboardType.Text, onValueChange ={ username= it })
//            Spacer(modifier = Modifier.height(12.dp))
//            CustomTextField(placeholder = "Email", keyboardType = KeyboardType.Text, onValueChange ={ email= it })
//            Spacer(modifier = Modifier.height(16.dp))
//            ConfirmButton(enabled = true, text = "Save") {
//
//            }
//        }
//    }
}

// The common component to generate the input boxes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileInputRow(boxType: String) {
    var input by remember { mutableStateOf("") }
    TextField(
        value = input,
        onValueChange = { newText -> input = newText },
        label = { Text(boxType, color = Color.Gray) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (boxType == "Email") KeyboardType.Email else KeyboardType.Text
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFF000000).copy(alpha = 0.04F),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .border(1.dp, Color(0xFFDADADA), RoundedCornerShape(percent = 20))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF000000).copy(alpha = 0.04F))
            .fillMaxWidth()
    )
}

@Composable
@Preview
fun EditProfileSetting() {
    EditProfileSetting(rememberNavController())
}