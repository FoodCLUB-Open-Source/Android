package live.foodclub.presentation.ui.home.profile

import live.foodclub.utils.composables.videoPager.VideoPagerEvents
import android.net.Uri
import java.io.File

interface ProfileEvents: VideoPagerEvents {
    fun isFollowedByUser(userId: Long)
    fun setUser(newUserId: Long)
    fun updateUserProfileImage(file: File, uri: Uri)
    fun unfollowUser(userId: Long)
    fun followUser(userId: Long)
}