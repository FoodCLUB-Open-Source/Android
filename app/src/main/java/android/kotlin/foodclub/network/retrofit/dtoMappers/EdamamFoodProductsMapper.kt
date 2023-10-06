package android.kotlin.foodclub.network.retrofit.dtoMappers

import android.kotlin.foodclub.data.models.Ingredient
import android.kotlin.foodclub.data.models.ProductsData
import android.kotlin.foodclub.network.retrofit.dtoModels.EdamamFoodProductsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.subModels.EdamamFoodProductMeasuresDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper
import android.kotlin.foodclub.utils.enums.QuantityUnit

class EdamamFoodProductsMapper: DomainMapper<EdamamFoodProductsDto, ProductsData> {
    override fun mapToDomainModel(entity: EdamamFoodProductsDto): ProductsData {
        return ProductsData(
            searchText = entity.text,
            productsList = entity.hints.map { dtoEntity ->
                Ingredient(
                    id = dtoEntity.food.foodId,
                    type = dtoEntity.food.label,
                    quantity = 10,
                    unit = determineDefaultUnit(dtoEntity.measures),
                    imageUrl = dtoEntity.food.image
                )
            }
        )
    }

    override fun mapFromDomainModel(domainModel: ProductsData): EdamamFoodProductsDto {
        TODO("Not yet implemented")
    }

    private fun determineDefaultUnit(measures: List<EdamamFoodProductMeasuresDto>):QuantityUnit {
        val units = measures.map { measure -> QuantityUnit.parseUnit(measure.label) }
        return if(units.any { it == QuantityUnit.MILLILITERS }) QuantityUnit.MILLILITERS
            else QuantityUnit.GRAMS
    }
}