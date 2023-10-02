package android.kotlin.foodclub.utils.helpers

import android.content.SharedPreferences
import android.kotlin.foodclub.data.models.MyBasket
import com.google.gson.Gson

class MyBasketCache(
    private val sharedPreferences: SharedPreferences,
    private val sessionCache: SessionCache
) {
    private val gson = Gson().getAdapter(MyBasket::class.java)
    private var userId = sessionCache.getActiveSession()?.userId ?: 0

    fun saveBasket(basket: MyBasket) {
        getUserId()
        sharedPreferences.edit().putString("basket$userId", gson.toJson(basket)).apply()
    }

    fun getBasket(): MyBasket {
        getUserId()
        val json = sharedPreferences.getString("basket$userId", null) ?: return MyBasket()
        return gson.fromJson(json)
    }

    fun clearBasket() {
        getUserId()
        sharedPreferences.edit().remove("basket$userId").apply()
    }

    private fun getUserId() {
        userId = sessionCache.getActiveSession()?.userId ?: 0
    }
}