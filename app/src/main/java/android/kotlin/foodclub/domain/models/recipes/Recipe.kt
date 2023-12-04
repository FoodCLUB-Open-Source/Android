package android.kotlin.foodclub.domain.models.recipes

data class Recipe(
    val title: String,
    val description: String,
    val preparationTime: Int,
    val servingSize: Int,
    val category: String,
)
