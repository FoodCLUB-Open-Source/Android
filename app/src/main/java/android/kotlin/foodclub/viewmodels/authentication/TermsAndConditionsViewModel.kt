package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.data.models.VideoModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TermsAndConditionsViewModel: ViewModel() {


    private val _onBoxChecked = MutableStateFlow<Boolean>(true)
    val onBoxChecked: StateFlow<Boolean> get() = _onBoxChecked


    fun onChecked(checked:Boolean,navController: NavHostController){
        if(checked){
            navController.navigate("SIGN_UP")
        }
    }

}