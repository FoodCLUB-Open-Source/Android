package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.ErrorResponse
import android.kotlin.foodclub.api.authentication.VerificationCodeForPasswordData
import android.kotlin.foodclub.api.authentication.VerificationCodeResendData
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.data.models.VideoModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.graphs.AuthScreen
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ForgotPasswordViewModel : ViewModel() {

    private val gson = Gson()


    private val _errorStatus = MutableLiveData<String?>()
    val errorStatus: LiveData<String?> get() = _errorStatus

    fun sendCode(username:String,navController: NavHostController){



        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitInstance.retrofitApi.sendVerificationCodePassword(
                    VerificationCodeResendData(username)
                )

                if(response.isSuccessful) {
                    navController.navigate(route = AuthScreen.ChangePassword.route + "/" + username)
                }
                else{
                    val errorString = response.errorBody()?.string()
                    _errorStatus.postValue(errorString)
                }

            } catch(e: IOException) {


            } catch (e: Exception) {

            }
        }
    }


}
