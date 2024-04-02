package android.kotlin.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity("product_units", foreignKeys = [
    ForeignKey(
        entity = ProductEntity::class,
        parentColumns = ["foodId"],
        childColumns = ["foodId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ProductUnitEntity(
    val foodId: String,
    val unit: String
)
