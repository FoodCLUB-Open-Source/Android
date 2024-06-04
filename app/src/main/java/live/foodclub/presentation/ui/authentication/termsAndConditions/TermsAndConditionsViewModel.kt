package live.foodclub.presentation.ui.authentication.termsAndConditions

import live.foodclub.presentation.navigation.auth.AuthScreen
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TermsAndConditionsViewModel : ViewModel(), TermsAndConditionsEvents {


    private val _onBoxChecked = MutableStateFlow(true)
    val onBoxChecked: StateFlow<Boolean> get() = _onBoxChecked


    override fun onChecked(
        checked: Boolean,
        navController: NavController
    ) {
        if (checked) {
            navController.navigate(AuthScreen.SignUp.route)
        }
    }

}