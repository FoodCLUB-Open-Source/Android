package android.kotlin.foodclub.utils.helpers

import android.content.SharedPreferences
import android.kotlin.foodclub.data.models.Session
import com.google.gson.Gson

class SessionCache(private val sharedPreferences: SharedPreferences) {
    private val gson = Gson().getAdapter(Session::class.java)

    fun saveSession(session: Session) {
        sharedPreferences.edit().putString("session", gson.toJson(session)).apply()
    }

    fun getActiveSession(): Session? {
        val json = sharedPreferences.getString("session", null) ?: return null
        return gson.fromJson(json)
    }

    fun clearSession() {
        sharedPreferences.edit().remove("session").apply()
    }
}