package android.kotlin.foodclub.viewModels.home.profile

import android.kotlin.foodclub.utils.composables.videoPager.VideoPagerEvents
import android.net.Uri
import java.io.File

interface ProfileEvents: VideoPagerEvents {
    fun isFollowedByUser(sessionUserId: Long, userId: Long)
    fun setUser(newUserId: Long)
    fun updateUserProfileImage(file: File, uri: Uri)
    fun getPostData(postId: Long)
    fun updatePosts(postId: Long)
    fun unfollowUser(userId: Long)
    fun followUser(userId: Long)
    fun onRefreshUI()
}