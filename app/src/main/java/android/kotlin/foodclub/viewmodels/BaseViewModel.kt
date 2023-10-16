package android.kotlin.foodclub.viewmodels

import android.kotlin.foodclub.data.models.Session
import android.kotlin.foodclub.navigation.graphs.Graph
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    val sessionCache: SessionCache
): ViewModel(){
    val currentSession: StateFlow<Session?> get() = sessionCache.session

    fun checkSession(navController: NavController) {
        if(currentSession.value == null) {
            navController.navigate(Graph.AUTHENTICATION) { popUpTo(Graph.HOME) {
                inclusive = true
            }}
        }
    }
}