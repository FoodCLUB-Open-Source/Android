package android.kotlin.foodclub.domain.models.recipes

// DATA CLASS FOR "CREATE RECIPE VIEW" SECTION

data class Recipe(
    val title: String,
    val description: String,
    val preparationTime: Int,
    val servingSize: Int,
    val category: String,
)
