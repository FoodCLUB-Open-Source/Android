package android.kotlin.foodclub.data.models

class ProductsData(val searchText: String, productsList: List<Ingredient>) {
    var productsList: List<Ingredient> = productsList
        private set

    init {
        removeRepeatedIds()
    }

    private fun removeRepeatedIds() {
        val productsMap: MutableMap<String, Ingredient> = mutableMapOf()
        productsList.forEach {
            if (!productsMap.containsKey(it.id)) productsMap.put(it.id, it)
        }
        productsList = productsMap.map { it.value }
    }
    
}