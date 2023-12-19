package android.kotlin.foodclub.domain.models.products

class MyBasket {
    var ingredients: MutableList<Ingredient> = mutableListOf()
        private set

    var selectedIngredients: MutableList<String> = mutableListOf()
        private set

    private var removedIds = ArrayList<String>()

    fun getIngredientCount(): Int {
        return ingredients.size
    }

    fun addIngredient(ingredient: Ingredient) {
        val repeatedIngredients = ingredients.filter { element -> element.id == ingredient.id }
        if(repeatedIngredients.isNotEmpty()) {
            repeatedIngredients.get(0).incrementQuantity(ingredient.quantity)
            return
        }
        ingredients.add(ingredient)
    }

    fun removeIngredient(id: String) {
        val removeList = ingredients.filter { it.id == id }
        ingredients.remove(removeList[0])
        removedIds.add(id)
    }

    fun selectIngredient(id: String) {
        selectedIngredients.add(id)
    }

    fun unselectIngredient(id: String) {
        selectedIngredients.remove(id)
    }

    fun deleteIngredients() {
        val removeList = ingredients.filter { selectedIngredients.contains(it.id) }
        ingredients.removeAll(removeList)
        removedIds.addAll(removeList.map { it.id })
        selectedIngredients = mutableListOf()
    }

    fun clearSelectedIngredients() {
        selectedIngredients = mutableListOf()
    }

    fun copy(
        ingredients: MutableList<Ingredient> = this.ingredients.toMutableList(),
        selectedIngredients: MutableList<String> = this.selectedIngredients.toMutableList(),
        removedIds: ArrayList<String> = ArrayList(this.removedIds)
    ): MyBasket {
        val newBasket = MyBasket()
        newBasket.ingredients = ingredients
        newBasket.selectedIngredients = selectedIngredients
        newBasket.removedIds = removedIds
        return newBasket
    }
}