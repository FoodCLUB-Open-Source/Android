package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.network.retrofit.apiInterfaces.RecipeService
import android.kotlin.foodclub.network.retrofit.dtoMappers.recipes.RecipeMapper
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

class RecipeRepository(
    private val api: RecipeService,
    private val recipeMapper: RecipeMapper
) {

    suspend fun createRecipe(recipe: Recipe, userId: String): Boolean {
        return when(
            val resource = apiRequestFlow<Unit, DefaultErrorResponse> {
                api.createRecipe(userId, recipeMapper.mapFromDomainModel(recipe))
            }
        ) {
            is Resource.Success -> {
                true
            }

            is Resource.Error -> {
                false
            }
        }
    }
}

