package live.foodclub.presentation.ui.home.follow

import live.foodclub.domain.models.profile.SimpleUserModel

data class FollowerFollowingState(
    val followersList: List<SimpleUserModel>,
    val followingList: List<SimpleUserModel>,
    val error: String,
    val title: String,
) {
    companion object {
        fun default() = FollowerFollowingState(
            followersList = emptyList(),
            followingList = emptyList(),
            error = "",
            title = "",
        )
    }
}
