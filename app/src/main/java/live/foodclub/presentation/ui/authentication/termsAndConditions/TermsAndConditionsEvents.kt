package live.foodclub.presentation.ui.authentication.termsAndConditions

import androidx.navigation.NavController

interface TermsAndConditionsEvents {
    fun onChecked(checked:Boolean, navController: NavController)
}