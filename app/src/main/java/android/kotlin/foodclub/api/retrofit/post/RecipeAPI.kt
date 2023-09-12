package android.kotlin.foodclub.api.retrofit.post

import android.kotlin.foodclub.data.models.Recipe
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeAPI {
    @POST("posts/{user_id}")
    suspend fun createRecipe(
        @Path("user_id") userId: String,
        @Body recipe: Recipe
    ): Response<Unit>
}