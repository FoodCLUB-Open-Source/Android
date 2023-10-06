package android.kotlin.foodclub.network.retrofit.dtoModels

import android.kotlin.foodclub.network.retrofit.dtoModels.subModels.EdamamFoodProductContainerDto
import android.kotlin.foodclub.network.retrofit.dtoModels.subModels.EdamamFoodProductLinkDto
import com.google.gson.annotations.SerializedName


data class EdamamFoodProductsDto(
    val text: String = "",
    val parsed: List<EdamamFoodProductContainerDto> = listOf(),
    val hints: List<EdamamFoodProductContainerDto> = listOf(),

    @SerializedName("_links")
    val links: Map<String, EdamamFoodProductLinkDto> = mapOf()
)
