package live.foodclub.views.home.profile

import live.foodclub.domain.models.profile.UserProfile
import live.foodclub.utils.composables.videoPager.VideoPagerState
import live.foodclub.utils.helpers.StoreData
import androidx.media3.exoplayer.ExoPlayer

data class ProfileState(
    val userProfile: UserProfile?,
    val sessionUserId: Long,
    val dataStore: StoreData?,
    val isFollowed: Boolean,
    val profileUserId : Long,
    val videoPagerState: VideoPagerState,
    val error : String,
    val exoPlayer: ExoPlayer,
) {


    companion object {
        fun default(exoPlayer: ExoPlayer) = ProfileState(
            userProfile = null,
            sessionUserId = 0L,
            dataStore = null,
            isFollowed = false,
            profileUserId = 0,
            videoPagerState = VideoPagerState.default(),
            error = "",
            exoPlayer = exoPlayer,
        )
    }
}