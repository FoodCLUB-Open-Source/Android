package android.kotlin.foodclub.domain.models.recipes

import android.kotlin.foodclub.domain.models.products.Ingredient

data class Recipe(
    val id: Long,
    val postId: Long,
    val description: String,
    val ingredients: List<Ingredient>,
    val servingSize: Int
)
