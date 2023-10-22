package android.kotlin.foodclub.network.retrofit.dtoModels.recipes

data class RecipeDto(
    val title: String,
    val description: String,
    val preparationTime: Int,
    val servingSize: Int,
    val category: String,
)
