package live.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "home_posts",
    foreignKeys = [
        ForeignKey(
            entity = PostEntity::class,
            parentColumns = ["postId"],
            childColumns = ["postId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HomePostEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val postId: Long
)
