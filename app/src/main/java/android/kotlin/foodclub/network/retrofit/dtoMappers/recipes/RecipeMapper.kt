package android.kotlin.foodclub.network.retrofit.dtoMappers.recipes

import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.network.retrofit.dtoModels.recipes.RecipeDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class RecipeMapper: DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(entity: RecipeDto): Recipe {
        return Recipe(
            id = entity.id,
            postId = entity.postId,
            description = entity.description,
            ingredients = createIngredients(entity.ingredients),
            servingSize = entity.servingSize,
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            id = domainModel.id,
            postId = domainModel.postId,
            description = domainModel.description,
            ingredients = domainModel.ingredients.map { it.type },
            servingSize = domainModel.servingSize,
        )
    }

    private fun createIngredients(ingredientsDto: List<String>): List<Ingredient> {
        var i = 1
        val list = ArrayList<Ingredient>()
        for(ingredient in ingredientsDto) {
            list.add(Ingredient(i.toString(), ingredient, 1, QuantityUnit.GRAM))
            i++
        }
        return list
    }
}