package android.kotlin.foodclub.utils.helpers

import android.content.SharedPreferences
import android.kotlin.foodclub.data.models.MyBasket
import com.google.gson.Gson

class MyBasketCache(private val sharedPreferences: SharedPreferences) {
    private val gson = Gson().getAdapter(MyBasket::class.java)

    fun saveBasket(basket: MyBasket) {
        sharedPreferences.edit().putString("basket", gson.toJson(basket)).apply()
    }

    fun getBasket(): MyBasket {
        val json = sharedPreferences.getString("basket", null) ?: return MyBasket()
        return gson.fromJson(json)
    }

    fun clearBasket() {
        sharedPreferences.edit().remove("basket").apply()
    }
}