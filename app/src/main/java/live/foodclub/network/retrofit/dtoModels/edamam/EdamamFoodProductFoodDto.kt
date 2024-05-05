package live.foodclub.network.retrofit.dtoModels.edamam

import androidx.annotation.Keep

@Keep
data class EdamamFoodProductFoodDto(
    val foodId: String,
    val label: String,
    val knownAs: String,
    val category: String,
    val categoryLabel: String,
    val image: String? = null
)
