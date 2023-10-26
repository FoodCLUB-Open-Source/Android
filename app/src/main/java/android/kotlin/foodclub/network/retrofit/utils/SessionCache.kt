package android.kotlin.foodclub.network.retrofit.utils

import android.content.SharedPreferences
import android.kotlin.foodclub.domain.models.session.Session
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionCache(private val sharedPreferences: SharedPreferences) {
    private val gson = Gson().getAdapter(Session::class.java)

    private val _session = MutableStateFlow(getActiveSession())
    val session: StateFlow<Session?> get() = _session

    fun saveSession(session: Session) {
        _session.value = session
        sharedPreferences.edit().putString("session", gson.toJson(session)).apply()
    }

    fun getActiveSession(): Session? {
        val json = sharedPreferences.getString("session", null) ?: return null
        return gson.fromJson(json)
    }

    fun clearSession() {
        _session.value = null
        sharedPreferences.edit().remove("session").apply()
    }
}