package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ChangePasswordView(){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier= Modifier
            .padding(16.dp)
            .fillMaxSize()

    ){
        SettingsTopBar("Password")
        Spacer(modifier = Modifier.height(30.dp))
        inputBoxes("Password")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inputBoxes() {

    var input by remember { mutableStateOf("") }

    Row (
        modifier = Modifier.fillMaxWidth()
    ){
        Column (
            modifier=Modifier.background(Color(218, 218, 218, 1), RoundedCornerShape(8.dp))
        ){
            InputBox()
        }

        Spacer(modifier = Modifier.weight(1f))

        Column (
            modifier=Modifier.background(Color.Transparent)
        ){
         hideButton(switch = false)
        }
    }
}

@Composable
fun Button (switch:Boolean){

    var iconChoice: Int = if(switch){
        R.drawable.unhide
    }else{
        R.drawable.hide
    }

    IconButton(
        onClick = {/* TO DO */},
        modifier = Modifier.background(Color(218, 218, 218, 1)),
        content = {
            SettingsIcons(size = 20, icon = iconChoice)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBox(input:String, boxType:String){



    TextField(
        value = input,
        onValueChange = { newText -> input = newText },
        label = { Text(boxType, color = Color.Gray) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            textColor = Color.Black
        ),
        modifier = Modifier
            .height(60.dp)
            .border(1.dp, Color.Transparent, shape = MaterialTheme.shapes.small),
    )
}
