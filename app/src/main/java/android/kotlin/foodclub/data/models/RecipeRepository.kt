package android.kotlin.foodclub.data.models

import android.kotlin.foodclub.api.retrofit.post.RecipeAPI

class RecipeRepository(private val api: RecipeAPI) {

    suspend fun createRecipe(recipe: Recipe, userId: String): Boolean {
        try {
            // MAKE API CALL TO CREATE RECIPE
            api.createRecipe(userId, recipe)
            return true // RETURN TRUE FOR SUCCESS
        } catch (e: Exception) {
            e.printStackTrace()
            return false // RETURN FALSE FOR FAILURE
        }
    }
}

