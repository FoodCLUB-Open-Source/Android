package android.kotlin.foodclub.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun ChangePasswordView(){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier=Modifier
            .padding(16.dp)
            .fillMaxSize()

    ){
        SettingsTopBar("Password")
        inputBoxes("Password")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inputBoxes(boxType : String){

    var input by remember { mutableStateOf("") }


    TextField(
        value = input,
        onValueChange = { newText -> input = newText},
        label={ Text(boxType, color=Color.Gray) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        colors= TextFieldDefaults.textFieldColors(containerColor = Color(218, 218, 218), textColor = Color.White ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(top = 100.dp)
            .height(50.dp)
            .fillMaxWidth()
    )
}