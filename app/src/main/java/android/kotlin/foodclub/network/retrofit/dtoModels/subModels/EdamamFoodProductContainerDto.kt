package android.kotlin.foodclub.network.retrofit.dtoModels.subModels

data class EdamamFoodProductContainerDto(
    val food: EdamamFoodProductFoodDto,
    val measures: List<EdamamFoodProductMeasuresDto>
)