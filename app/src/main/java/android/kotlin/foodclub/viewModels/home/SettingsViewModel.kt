package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val sessionCache: SessionCache
) : ViewModel() {
    fun logout() {
        sessionCache.clearSession()
    }
    fun changePassword() {

    }
}