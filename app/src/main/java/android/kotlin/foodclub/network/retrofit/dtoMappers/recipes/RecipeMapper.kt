package android.kotlin.foodclub.network.retrofit.dtoMappers.recipes

import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.network.retrofit.dtoModels.recipes.RecipeDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class RecipeMapper: DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(entity: RecipeDto): Recipe {
        return Recipe(
            title = entity.title,
            description = entity.description,
            preparationTime = entity.preparationTime,
            servingSize = entity.servingSize,
            category = entity.category
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            title = domainModel.title,
            description = domainModel.description,
            preparationTime = domainModel.preparationTime,
            servingSize = domainModel.servingSize,
            category = domainModel.category
        )
    }
}