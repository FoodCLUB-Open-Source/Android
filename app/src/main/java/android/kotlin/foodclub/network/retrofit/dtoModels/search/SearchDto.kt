package android.kotlin.foodclub.network.retrofit.dtoModels.search

import com.google.gson.annotations.SerializedName

data class SearchDto(
    @SerializedName("search_text")
    val text: String?
)
