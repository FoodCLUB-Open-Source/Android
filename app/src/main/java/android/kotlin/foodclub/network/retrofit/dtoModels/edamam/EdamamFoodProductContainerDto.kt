package android.kotlin.foodclub.network.retrofit.dtoModels.edamam

import androidx.annotation.Keep

@Keep
data class EdamamFoodProductContainerDto(
    val food: EdamamFoodProductFoodDto,
    val measures: List<EdamamFoodProductMeasuresDto>
)