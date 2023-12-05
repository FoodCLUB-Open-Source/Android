package android.kotlin.foodclub.domain.models.products

import android.content.SharedPreferences
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import com.google.gson.Gson

class MyBasketCache(
    private val sharedPreferences: SharedPreferences,
    private val sessionCache: SessionCache
) {
    private val gson = Gson().getAdapter(MyBasket::class.java)
    private var userId = sessionCache.getActiveSession()?.sessionUser?.userId?.toInt() ?: 0
    private var basket: MyBasket? = null

    fun saveBasket(newBasket: MyBasket) {
        getUserId()
        basket = newBasket
        sharedPreferences.edit().putString("basket$userId", gson.toJson(basket)).apply()
    }

    fun getBasket(): MyBasket {
        getUserId()
        if (basket == null) {
            val json = sharedPreferences.getString("basket$userId", null)
            basket = if (json != null) gson.fromJson(json) else MyBasket()
        }
        return basket!!
    }

    fun clearBasket() {
        getUserId()
        sharedPreferences.edit().remove("basket$userId").apply()
    }

    private fun getUserId() {
        userId = sessionCache.getActiveSession()?.sessionUser?.userId?.toInt() ?: 0
    }
}