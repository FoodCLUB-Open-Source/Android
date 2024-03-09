package android.kotlin.foodclub.viewModels

import android.kotlin.foodclub.domain.models.session.Session
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    val sessionCache: SessionCache
) : ViewModel() {
    val currentSession: StateFlow<Session?> get() = sessionCache.session

    fun checkSession(navController: NavController) {
        if(currentSession.value?.sessionUser == null
            || currentSession.value!!.sessionUser.username == "") {
            navController.navigate(Graph.AUTHENTICATION) { popUpTo(Graph.HOME) {
                inclusive = true
            }}
        }
    }
}