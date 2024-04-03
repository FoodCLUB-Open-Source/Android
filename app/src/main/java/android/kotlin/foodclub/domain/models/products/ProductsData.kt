package android.kotlin.foodclub.domain.models.products

import android.kotlin.foodclub.utils.helpers.ValueParser

class ProductsData(val searchText: String, val nextUrl: String, productsList: List<Ingredient>) {
    var productsList: List<Ingredient> = productsList
        private set

    init {
        removeRepeatedIds()
    }

    private fun removeRepeatedIds() {
        val productsMap: MutableMap<String, Ingredient> = mutableMapOf()
        productsList.forEach {
            if (!productsMap.containsKey(it.product.foodId)) productsMap.put(it.product.foodId, it)
        }
        productsList = productsMap.map { it.value }
    }

    fun getSessionIdFromUrl(): Int? {
        val sessionIdString = ValueParser.extractQueriesFromUri(nextUrl)
            .getOrDefault("session", null) ?: return null
        return Integer.valueOf(sessionIdString)
    }
    
}