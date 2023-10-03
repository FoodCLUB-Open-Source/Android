package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.utils.helpers.SessionCache
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val sessionCache: SessionCache
) : ViewModel() {
    fun changePassword() {

    }
}