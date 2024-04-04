package android.kotlin.foodclub.network.retrofit.dtoModels.edamam

import androidx.annotation.Keep

@Keep
data class EdamamFoodProductMeasuresDto(
    val uri: String,
    val label: String,
    val weight: Float
)
