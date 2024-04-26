package live.foodclub.viewModels.home.myBasket

import live.foodclub.utils.composables.products.ProductsEvents

interface MyBasketEvents: ProductsEvents {
    fun selectIngredient(ingredientId: String)
    fun unselectIngredient(ingredientId: String)
    fun saveBasket()
    fun deleteSelectedIngredients()
}