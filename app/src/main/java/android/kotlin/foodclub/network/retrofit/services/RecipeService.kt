package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.dtoModels.recipes.RecipeDto
import android.kotlin.foodclub.network.retrofit.responses.recipes.RecipeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @POST("posts")
    suspend fun createRecipe(
        @Body recipe: RecipeDto
    ): Response<Unit>

    @GET("recipe/{post_id}")
    suspend fun getRecipe(
        @Path("post_id") postId: Long
    ): Response<RecipeResponse>
}