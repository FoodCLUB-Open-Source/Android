package android.kotlin.foodclub.domain.models.profile

data class UserPosts(
    val id: Int,
    val title: String,
    val description: String,

    val dateCreated: String,
    val videoUrl: String,
    val thumbnailUrl: String,

    val totalLikes: Int,
    val totalViews: Int
)
