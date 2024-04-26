package live.foodclub.network.retrofit.dtoModels.edamam

import androidx.annotation.Keep

@Keep
data class EdamamFoodProductLinkDto(
    val title: String,
    val href: String
)
