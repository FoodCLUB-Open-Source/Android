package live.foodclub.domain.models.products

import android.content.SharedPreferences
import live.foodclub.network.retrofit.utils.SessionCache
import com.google.gson.Gson

class MyBasketCache(
    private val sharedPreferences: SharedPreferences,
    private val sessionCache: SessionCache
) {
    private val gson = Gson().getAdapter(MyBasket::class.java)
    private var username = sessionCache.getActiveSession()?.sessionUser?.username ?: ""
    private var basket: MyBasket? = null

    fun saveBasket(newBasket: MyBasket) {
        getUserId()
        basket = newBasket
        sharedPreferences.edit().putString("basket$username", gson.toJson(basket)).apply()
    }

    fun getBasket(): MyBasket {
        getUserId()
        if (basket == null) {
            val json = sharedPreferences.getString("basket$username", null)
            basket = if (json != null) gson.fromJson(json) else MyBasket()
        }
        return basket!!
    }

    fun clearBasket() {
        getUserId()
        sharedPreferences.edit().remove("basket$username").apply()
    }

    private fun getUserId() {
        username = sessionCache.getActiveSession()?.sessionUser?.username ?: ""
    }
}