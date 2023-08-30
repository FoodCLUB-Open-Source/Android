package android.kotlin.foodclub.viewmodels.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodclub.viewmodels.authentication.ConfirmIdentityViewModel

class MainLogInAndSignUpViewModel : ViewModel() {

    var backgroundColor by mutableStateOf(Color.White)
    fun changeButtonUi(){

        backgroundColor = Color(218, 218, 218, 80)
    }

    fun reverseButtonUi(){
        backgroundColor = Color.White
    }

    fun continueWithFacebook(){

    }

    fun continueWithInstagram(){

    }

    fun termsAndConditions(){

    }





}