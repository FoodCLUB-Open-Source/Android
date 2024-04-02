package android.kotlin.foodclub.network.retrofit.dtoMappers.edamam

import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductMeasuresDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper
import android.kotlin.foodclub.domain.enums.QuantityUnit

class EdamamFoodProductsMapper: DomainMapper<EdamamFoodProductsDto, ProductsData> {
    override fun mapToDomainModel(entity: EdamamFoodProductsDto): ProductsData {
        return ProductsData(
            searchText = entity.text,
            productsList = entity.hints.map { dtoEntity ->
                Ingredient(
                    id = dtoEntity.food.foodId,
                    type = dtoEntity.food.label,
                    quantity = 0,
                    unit = determineDefaultUnit(dtoEntity.measures),
                    imageUrl = dtoEntity.food.image ?: "",
                    expirationDate = ""
                )
            },
            nextUrl = entity.links["next"]?.href ?: ""
        )
    }

    override fun mapFromDomainModel(domainModel: ProductsData): EdamamFoodProductsDto {
        TODO("Not yet implemented")
    }

    private fun determineDefaultUnit(measures: List<EdamamFoodProductMeasuresDto>): QuantityUnit {
        if(measures.isEmpty()) return QuantityUnit.GRAM
        val units = measures.map { measure -> QuantityUnit.parseUnit(measure.label ?: "Grams") }
        return if(units.any { it == QuantityUnit.MILLILITER }) QuantityUnit.MILLILITER
            else QuantityUnit.GRAM
    }
}