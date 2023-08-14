package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val colorGreen= Color(android.graphics.Color.parseColor("#7EC60B"))
val colorLightGray = Color(android.graphics.Color.parseColor("#DADADA"))


        @Composable
fun ChangePasswordView(){
    Column(
        verticalArrangement = Arrangement.Top,
        modifier= Modifier
            .padding(top = 80.dp)
            .fillMaxSize()
            .padding(16.dp)


    ){

            SettingsTopBar("Password")

            Spacer(modifier = Modifier.height(30.dp))

            inputBoxes("Password")
            Spacer(modifier = Modifier.height(15.dp))
            inputBoxes(boxType = "New Password")

            Spacer(modifier = Modifier.height(30.dp))
            SaveButton()


    }
}

@Composable
fun inputBoxes(boxType:String) {

    Row (
        verticalAlignment=Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorLightGray, RoundedCornerShape(8.dp))
            .height(60.dp)
            .padding(5.dp)
    ){
        Column{
            InputBox(boxType)
        }

        Spacer(modifier = Modifier.weight(1f))

        Column{
            eyeIcon()
        }
    }
}

@Composable
fun eyeIcon() {

    var switch by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = {
                 switch = !switch
            },

            content = {
                SettingsIcons(size = 20, icon = R.drawable.unhide)
            },
        )

    if(switch){
          SettingsIcons(size = 20, icon = R.drawable.crossline)
          passwordHidden(hidden = true)
      }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBox(boxType:String){

    var input by remember { mutableStateOf("") }

    TextField(
        value = input,
        onValueChange = { newText -> input = newText },
        label = { Text(boxType, color = Color.Gray) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
//        visualTransformation= passwordHidden(hidden = hidePass),
        visualTransformation = PasswordVisualTransformation(mask='*'),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorLightGray,
            textColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .border(0.dp,Color.Transparent, RoundedCornerShape(8.dp)),
    )
}

@Composable
fun passwordHidden(hidden:Boolean):VisualTransformation{
    val passType:VisualTransformation
    if(hidden) {
        passType = PasswordVisualTransformation(mask = '*')
    }else{
        passType =VisualTransformation.None
    }
    return passType
}
@Composable
fun SaveButton(){
    Button(
        onClick = {/*Password changes*/},
        content = {
            Text(
                text = "Save",
                fontSize = 16.sp
            )
        },
        colors= ButtonDefaults.buttonColors(containerColor = colorGreen, contentColor = Color.White),
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
