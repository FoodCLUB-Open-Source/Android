package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("api/food-database/v2/parser")
    suspend fun getFoodProducts(
        @Query("session") session: Int?,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("ingr") searchString: String,
        @Query("category") category: String = "generic-foods"
    ): Response<EdamamFoodProductsDto>

}