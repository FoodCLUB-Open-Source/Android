package android.kotlin.foodclub.localdatasource.room.relationships

import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.Product
import android.kotlin.foodclub.localdatasource.room.entity.ProductEntity
import android.kotlin.foodclub.localdatasource.room.entity.ProductUnitEntity
import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithUnits(
    @Embedded val productEntity: ProductEntity,
    @Relation(
        parentColumn = "foodId",
        entityColumn = "foodId"
    )
    val units: List<ProductUnitEntity>
)

fun ProductWithUnits.toProductModel(): Product {
    return Product(
        foodId = productEntity.foodId,
        label = productEntity.label,
        image = productEntity.image,
        units = units.map { QuantityUnit.parseUnit(it.unit) }
    )
}
