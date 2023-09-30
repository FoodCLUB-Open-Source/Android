package android.kotlin.foodclub.viewmodels.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmailSentViewModel : ViewModel() {


    fun goToLogin(navController: NavHostController){
        viewModelScope.launch(Dispatchers.Main) {

            try{
                navController.navigate("LOGIN")
            }catch (e:Exception){

            }


        }

    }
}