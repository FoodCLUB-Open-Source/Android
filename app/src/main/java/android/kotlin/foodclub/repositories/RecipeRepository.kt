package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.network.retrofit.services.RecipeService
import android.kotlin.foodclub.network.retrofit.dtoMappers.recipes.RecipeMapper
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.recipes.RecipeResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

class RecipeRepository(
    private val api: RecipeService,
    private val recipeMapper: RecipeMapper
) {

    suspend fun createRecipe(recipe: Recipe): Boolean {
        return when(
            apiRequestFlow<Unit, DefaultErrorResponse> {
                api.createRecipe(recipeMapper.mapFromDomainModel(recipe))
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

    suspend fun getRecipe(postId: Long): Resource<Recipe, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RecipeResponse, DefaultErrorResponse> {
                api.getRecipe(postId)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    recipeMapper.mapToDomainModel(resource.data!!.body()!!.recipe)
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}

