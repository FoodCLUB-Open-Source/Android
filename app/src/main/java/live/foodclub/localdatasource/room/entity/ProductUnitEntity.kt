package live.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("product_units", foreignKeys = [
    ForeignKey(
        entity = ProductEntity::class,
        parentColumns = ["foodId"],
        childColumns = ["foodId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ProductUnitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val foodId: String,
    val unit: String
)
