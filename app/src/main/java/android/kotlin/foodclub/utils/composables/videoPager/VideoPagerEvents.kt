package android.kotlin.foodclub.utils.composables.videoPager

interface VideoPagerEvents {
    suspend fun userViewsPost(postId: Long)
    suspend fun updatePostLikeStatus(postId: Long, isLiked: Boolean)
    fun getRecipe(postId: Long)
    fun updatePostBookmarkStatus(postId: Long, isBookmarked: Boolean)
    fun deletePost(postId: Long)
    fun addIngredientsToBasket()
}