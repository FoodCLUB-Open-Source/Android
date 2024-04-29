package android.kotlin.foodclub.viewModels.home.home

import android.kotlin.foodclub.domain.enums.Reactions
import java.io.File

interface HomeEvents {
    suspend fun userViewsPost(postId: Long)
    suspend fun updatePostLikeStatus(postId: Long, isLiked: Boolean)
    fun getRecipe(recipeId: Long)
    fun updatePostBookmarkStatus(postId: Long, isBookmarked: Boolean)
    fun postSnap(file: File)
    suspend fun userViewsStory(storyId: Long)
    fun addIngredientsToBasket()
    fun toggleShowMemories(show: Boolean)
    fun toggleShowMemoriesReel(show: Boolean)
    fun selectReaction(reaction: Reactions)

}