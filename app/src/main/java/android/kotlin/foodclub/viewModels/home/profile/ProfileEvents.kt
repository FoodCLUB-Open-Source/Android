package android.kotlin.foodclub.viewModels.home.profile

import android.net.Uri
import java.io.File

interface ProfileEvents {
    fun isFollowedByUser(sessionUserId: Long, userId: Long)
    fun setUser(newUserId: Long)
    fun updateUserProfileImage(id: Long, file: File, uri: Uri)
    fun getPostData(postId: Long)
    fun updatePosts(postId: Long)
    fun deleteCurrentPost(postId: Long)
    fun unfollowUser(sessionUserId: Long, userId: Long)
    fun followUser(sessionUserId: Long, userId: Long)
    fun addIngredientsToBasket()
    fun onRefreshUI()
}