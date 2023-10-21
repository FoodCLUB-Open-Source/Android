package android.kotlin.foodclub.network.retrofit.dtoModels.edamam

data class EdamamFoodProductContainerDto(
    val food: EdamamFoodProductFoodDto,
    val measures: List<EdamamFoodProductMeasuresDto>
)