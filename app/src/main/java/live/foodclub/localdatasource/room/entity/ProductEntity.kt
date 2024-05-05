package live.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("products")
data class ProductEntity(
    @PrimaryKey
    val foodId: String,
    val label: String,
    val image: String?
)
