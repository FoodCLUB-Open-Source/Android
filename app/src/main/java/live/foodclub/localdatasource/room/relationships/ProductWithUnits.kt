package live.foodclub.localdatasource.room.relationships

import live.foodclub.domain.enums.QuantityUnit
import live.foodclub.domain.models.products.Product
import live.foodclub.localdatasource.room.entity.ProductEntity
import live.foodclub.localdatasource.room.entity.ProductUnitEntity
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
        units = units.map { QuantityUnit.parseUnit(it.unit) }.toSet().toList()
    )
}
