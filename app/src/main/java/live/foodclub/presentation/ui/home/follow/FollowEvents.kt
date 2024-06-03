package live.foodclub.presentation.ui.home.follow

interface FollowEvents {
    fun getFollowersList(userId: Long)
    fun getFollowingList(userId: Long)
}