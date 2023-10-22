package android.kotlin.foodclub.viewModels.authentication

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TermsAndConditionsViewModel: ViewModel() {


    private val _onBoxChecked = MutableStateFlow<Boolean>(true)
    val onBoxChecked: StateFlow<Boolean> get() = _onBoxChecked


    fun onChecked(checked:Boolean,navController: NavHostController){
        if(checked){
            navController.navigate("SIGN_UP")
        }
    }

}