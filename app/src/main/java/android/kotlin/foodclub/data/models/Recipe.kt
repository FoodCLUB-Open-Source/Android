package android.kotlin.foodclub.data.models

// DATA CLASS FOR "CREATE RECIPE VIEW" SECTION

data class Recipe(
    val title: String,
    val description: String,
    val preparationTime: Int,
    val servingSize: Int,
    val category: String,
)
