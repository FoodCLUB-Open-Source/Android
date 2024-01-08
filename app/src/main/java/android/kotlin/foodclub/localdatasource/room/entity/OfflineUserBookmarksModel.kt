package android.kotlin.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_bookmarks")
data class OfflineUserBookmarksModel(
    @PrimaryKey
    val videoId: Long,
    val title: String? = null,
    val description: String?,
    val createdAt: String? = null,
    val videoLink: String? = null,
    val thumbnailLink: String? = null,
    val totalLikes: Long? = null,
    val totalViews: Long? = null
)
