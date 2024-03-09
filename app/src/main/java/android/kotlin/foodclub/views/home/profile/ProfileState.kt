package android.kotlin.foodclub.views.home.profile

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileModel
import android.kotlin.foodclub.utils.helpers.StoreData

data class ProfileState(
    val userProfile: UserProfile?,
    val offlineProfileModel: OfflineProfileModel?,
    val bookmarkedPosts: List<VideoModel>,
    val userPosts: List<VideoModel>,
    val sessionUserId: Long,
    val dataStore: StoreData?,
    val isFollowed: Boolean,
    val myUserId : Long,
    val error : String,
    val postData: VideoModel?,
    val recipe: Recipe?,
    var isRefreshingUI: Boolean,
    var isLoading: Boolean
) {


    companion object {
        fun default() = ProfileState(
            userProfile = null,
            offlineProfileModel = null,
            bookmarkedPosts = emptyList(),
            userPosts = emptyList(),
            sessionUserId = 0L,
            dataStore = null,
            isFollowed = false,
            myUserId = 0,
            error = "",
            postData = null,
            recipe = null,
            isRefreshingUI = false,
            isLoading = false
        )
    }
}