package android.kotlin.foodclub.domain.enums

import android.kotlin.foodclub.network.retrofit.dtoModels.recipes.RecipeCategoryDto

enum class Category(val displayName: String, val categoryType: CategoryType) {
    MEAT("Meat", CategoryType.DIET),
    KETO("Keto", CategoryType.DIET),
    HIGH_PROTEIN("High-protein", CategoryType.DIET),
    VEGAN("Vegan", CategoryType.DIET),
    LOW_FAT("Low-fat", CategoryType.DIET),
    FAT_REDUCTION("Fat-reduction", CategoryType.DIET),
    ITALIAN("Italian", CategoryType.CUISINE),
    CHINESE("Chinese", CategoryType.CUISINE),
    VEGETARIAN("Vegetarian", CategoryType.DIET),
    JAPANESE("Japanese", CategoryType.CUISINE),
    MEXICAN("Mexican", CategoryType.CUISINE),
    SPANISH("Spanish", CategoryType.CUISINE);

    companion object {
        fun deriveFromName(name: String): Category? {
            val derivedCategories = entries.filter { it.displayName == name }
            if(derivedCategories.isNotEmpty()) return derivedCategories[0]
            return null
        }

        fun deriveFromType(categoryType: CategoryType): List<Category> {
            val categoryList = entries.filter { it.categoryType == categoryType }

            return categoryList.ifEmpty {
                emptyList()
            }
        }
    }
}

fun Category.toRecipeCategoryDto(): RecipeCategoryDto {
    return RecipeCategoryDto(category = displayName)
}
