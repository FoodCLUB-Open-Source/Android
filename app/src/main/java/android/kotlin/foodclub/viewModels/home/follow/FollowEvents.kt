package android.kotlin.foodclub.viewModels.home.follow

interface FollowEvents {
    fun getFollowersList(userId: Long)
    fun getFollowingList(userId: Long)
}