package android.kotlin.foodclub.domain.models.profile

import android.kotlin.foodclub.domain.models.home.VideoModel

data class UserProfile(
    val username: String,
    val profilePictureUrl: String? = null,

    val totalUserLikes: Int,
    val totalUserFollowers: Int,
    val totalUserFollowing: Int,

    val userPosts: List<VideoModel> = listOf(),
    val topCreators: List<SimpleUserModel> = listOf()
)
