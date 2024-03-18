package android.kotlin.foodclub.views.home.profile

import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.utils.helpers.StoreData
import androidx.media3.exoplayer.ExoPlayer

data class ProfileState(
    val userProfile: UserProfile?,
    val sessionUserId: Long,
    val dataStore: StoreData?,
    val isFollowed: Boolean,
    val profileUserId : Long,
    val error : String,
    val recipe: Recipe?,
    val exoPlayer: ExoPlayer,
) {


    companion object {
        fun default(exoPlayer: ExoPlayer) = ProfileState(
            userProfile = null,
            sessionUserId = 0L,
            dataStore = null,
            isFollowed = false,
            profileUserId = 0,
            error = "",
            recipe = null,
            exoPlayer = exoPlayer,
        )
    }
}