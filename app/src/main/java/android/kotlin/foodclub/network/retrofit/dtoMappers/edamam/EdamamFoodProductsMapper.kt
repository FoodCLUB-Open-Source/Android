package android.kotlin.foodclub.network.retrofit.dtoMappers.edamam

import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductMeasuresDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Product

class EdamamFoodProductsMapper: DomainMapper<EdamamFoodProductsDto, ProductsData> {
    override fun mapToDomainModel(entity: EdamamFoodProductsDto): ProductsData {
        return ProductsData(
            searchText = entity.text,
            productsList = entity.hints.map { dtoEntity ->
                Ingredient(
                    product = Product(
                        foodId = dtoEntity.food.foodId,
                        label = dtoEntity.food.label,
                        image = dtoEntity.food.image,
                        units = dtoEntity.measures.filter { it.label != null }
                            .map { QuantityUnit.parseUnit(it.label!!) }.toSet().toList()
                    ),
                    quantity = 0,
                    unit = determineDefaultUnit(dtoEntity.measures),
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