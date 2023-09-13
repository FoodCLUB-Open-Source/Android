package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.VerificationCodeResendData
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.graphs.AuthScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class ForgotPasswordViewModel : ViewModel() {


    fun sendCode(username:String,navController: NavHostController){
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitInstance.retrofitApi.sendVerificationCodePassword(
                    VerificationCodeResendData(username)
                )

                if(response.isSuccessful) {

                    navController.navigate(route = AuthScreen.ChangePassword.route + "/" + username)

                }

            } catch(e: IOException) {


            } catch (e: Exception) {

            }
        }
    }

}
