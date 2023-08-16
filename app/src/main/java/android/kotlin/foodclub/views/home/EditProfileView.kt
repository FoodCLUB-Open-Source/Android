package android.kotlin.foodclub.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val colorGreen1= Color(android.graphics.Color.parseColor("#7EC60B"))
val colorLightGray1 = Color(android.graphics.Color.parseColor("#DADADA"))
@Composable
fun EditProfileSetting(){
    Column(
        verticalArrangement = Arrangement.Top,
        modifier= Modifier
            .padding(top = 80.dp)
            .fillMaxSize()
            .padding(16.dp)
    ){
        SettingsTopBar("Edit Profile")
        Spacer(modifier = Modifier.height(30.dp))
        EditProfileInputRow1("Username")
        Spacer(modifier = Modifier.height(15.dp))
        EditProfileInputRow1(boxType = "Email")
        Spacer(modifier = Modifier.height(30.dp))
        SaveButton()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileInputRow1(boxType: String) {
    var input by remember { mutableStateOf("") }
    TextField(
        value = input,
        onValueChange = { newText -> input = newText },
        label = { Text(boxType, color = Color.Gray) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (boxType == "Email") KeyboardType.Email else KeyboardType.Text
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorLightGray1,
            textColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorLightGray1, shape = RoundedCornerShape(8.dp))
            .height(60.dp)
            .padding(5.dp)
    )
}

@Composable
fun SaveButtonGreen(){
    Button(
        onClick = {/*Password changes*/},
        content = {
            Text(
                text = "Save",
                fontSize = 16.sp
            )
        },
        colors= ButtonDefaults.buttonColors(containerColor = colorGreen1, contentColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .border(
                width = 0.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
    )
}
