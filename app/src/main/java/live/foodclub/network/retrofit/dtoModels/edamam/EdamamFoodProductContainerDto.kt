package live.foodclub.network.retrofit.dtoModels.edamam

import live.foodclub.localdatasource.room.entity.ProductEntity
import live.foodclub.localdatasource.room.entity.ProductUnitEntity
import live.foodclub.localdatasource.room.relationships.ProductWithUnits
import androidx.annotation.Keep

@Keep
data class EdamamFoodProductContainerDto(
    val food: EdamamFoodProductFoodDto,
    val measures: List<EdamamFoodProductMeasuresDto>
)

fun EdamamFoodProductContainerDto.toProductWithUnits(): ProductWithUnits {
    return ProductWithUnits(
        productEntity = ProductEntity(
            foodId = food.foodId,
            label = food.label,
            image = food.image
        ),
        units = measures.filter { it.label != null  }.map {
            ProductUnitEntity(
                foodId = food.foodId,
                unit = it.label!!
            )
        }
    )
}