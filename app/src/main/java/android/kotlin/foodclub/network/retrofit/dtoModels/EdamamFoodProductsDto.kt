package android.kotlin.foodclub.network.retrofit.dtoModels

import android.kotlin.foodclub.network.retrofit.dtoModels.subModels.EdamamFoodProductContainerDto


data class EdamamFoodProductsDto(
    val text: String = "",
    val parsed: List<EdamamFoodProductContainerDto> = listOf(),
    val hints: List<EdamamFoodProductContainerDto> = listOf()
)
