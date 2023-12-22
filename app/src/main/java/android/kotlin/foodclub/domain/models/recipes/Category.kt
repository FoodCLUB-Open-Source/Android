package android.kotlin.foodclub.domain.models.recipes

data class Category(
    val id: Int,
    val name: String
)

val allCategories = listOf(
    Category(1, "Meat"),
    Category(2, "Keto"),
    Category(3, "High-protein"),
    Category(4, "Vegan"),
    Category(5, "Low-fat"),
    Category(6, "Fat-reduction"),
    Category(7, "Italian"),
    Category(8, "Chinese"),
    Category(9, "Vegetarian")
)