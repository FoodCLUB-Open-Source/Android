package android.kotlin.foodclub.data.models

class MyBasket {
    var ingredients: MutableList<Ingredient> = mutableListOf()
        private set

    var selectedIngredients: MutableList<Int> = mutableListOf()
        private set

    private var idIndex = 1
    private var removedIds = ArrayList<Int>()

    fun addIngredient(ingredient: Ingredient) {
        if(removedIds.isNotEmpty()) {
            idIndex = removedIds[0]
            removedIds.remove(0)
        }
        ingredient.id = idIndex
        ingredients.add(ingredient)
        idIndex = ingredients.size + 1
    }

    fun removeIngredient(id: Int) {
        val removeList = ingredients.filter { it.id == id }
        ingredients.remove(removeList[0])
        removedIds.add(id)
    }

    fun selectIngredient(id: Int) {
        selectedIngredients.add(id)
    }

    fun unselectIngredient(id: Int) {
        selectedIngredients.remove(id)
    }

    fun deleteIngredients() {
        val removeList = ingredients.filter { selectedIngredients.contains(it.id) }
        ingredients.removeAll(removeList)
        removedIds.addAll(removeList.map { it.id })
        selectedIngredients = mutableListOf()
    }
}