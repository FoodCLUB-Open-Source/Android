package android.kotlin.foodclub.viewModels.home.profile

import android.kotlin.foodclub.utils.composables.videoPager.VideoPagerEvents
import android.net.Uri
import java.io.File

interface ProfileEvents: VideoPagerEvents {
    fun isFollowedByUser(userId: Long)
    fun setUser(newUserId: Long)
    fun updateUserProfileImage(file: File, uri: Uri)
    fun unfollowUser(userId: Long)
    fun followUser(userId: Long)
}