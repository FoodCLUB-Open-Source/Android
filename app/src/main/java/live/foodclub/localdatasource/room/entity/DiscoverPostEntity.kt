package live.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "discover_posts",
    foreignKeys = [
        ForeignKey(
            entity = PostEntity::class,
            parentColumns = ["postId"],
            childColumns = ["postId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["postId"], unique = true)
    ]
)
data class DiscoverPostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val postId: Long
)