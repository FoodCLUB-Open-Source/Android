package android.kotlin.foodclub.domain.enums

enum class Category(val displayName: String) {
    MEAT("Meat"),
    KETO("Keto"),
    HIGH_PROTEIN("High-protein"),
    VEGAN("Vegan"),
    LOW_FAT("Low-fat"),
    FAT_REDUCTION("Fat-reduction"),
    ITALIAN("Italian"),
    CHINESE("Chinese"),
    VEGETARIAN("Vegetarian");

    companion object {
        fun deriveFromName(name: String): Category? {
            val derivedCategories = entries.filter { it.displayName == name }
            if(derivedCategories.isNotEmpty()) return derivedCategories[0]
            return null
        }
    }
}