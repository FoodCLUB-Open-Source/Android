package live.foodclub.viewModels.authentication.mainLogin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class MainLogInAndSignUpViewModel : ViewModel(), MainLoginAndSignUpEvents {

    var backgroundColor by mutableStateOf(Color.White)
    fun changeButtonUi(){

        backgroundColor = Color(218, 218, 218, 80)
    }

    override fun reverseButtonUi(){
        backgroundColor = Color.White
    }

    fun continueWithFacebook(){

    }

    fun continueWithInstagram(){

    }

    override fun termsAndConditions(){
        // TODO : Navigate to Terms and Conditions Screen
    }





}