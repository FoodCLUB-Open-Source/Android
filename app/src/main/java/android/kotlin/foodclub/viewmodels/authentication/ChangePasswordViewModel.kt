package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.ChangePasswordInformation
import android.kotlin.foodclub.api.authentication.VerificationCodeResendData
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ChangePasswordViewModel:ViewModel() {

    fun changePassword(username:String,verificationCode:String,password:String,navController: NavHostController){
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitInstance.retrofitApi.changePassword(
                    ChangePasswordInformation(username,verificationCode,password)
                )
                if(response.isSuccessful) {
                    navController.navigate("FORGOT_EMAIL_SENT")
                }

            } catch(e: IOException) {

            } catch (e: Exception) {

            }
        }
    }

}