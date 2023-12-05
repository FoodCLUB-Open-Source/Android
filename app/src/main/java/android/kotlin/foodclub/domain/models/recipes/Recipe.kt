package android.kotlin.foodclub.domain.models.recipes

import android.kotlin.foodclub.domain.models.products.Ingredient

data class Recipe(
    val title: String,
    val description: String,
    val preparationTime: Int,
    val servingSize: Int,
    val category: String,
    val ingredients: List<Ingredient>
)
