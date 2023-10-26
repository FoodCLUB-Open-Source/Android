package android.kotlin.foodclub.network.retrofit.utils.auth

import android.content.SharedPreferences
import android.kotlin.foodclub.domain.models.session.RefreshToken
import com.google.gson.Gson

class RefreshTokenManager(private val sharedPreferences: SharedPreferences) {
    private val gson = Gson().getAdapter(RefreshToken::class.java)

    fun saveToken(session: RefreshToken) {
        sharedPreferences.edit().putString("refresh_token", gson.toJson(session)).apply()
    }

    fun getActiveToken(): RefreshToken? {
        val json = sharedPreferences.getString("refresh_token", null) ?: return null
        return gson.fromJson(json)
    }

    fun clearToken() {
        sharedPreferences.edit().remove("refresh_token").apply()
    }
}