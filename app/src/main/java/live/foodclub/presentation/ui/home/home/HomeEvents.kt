package live.foodclub.presentation.ui.home.home

import live.foodclub.domain.enums.Reactions
import live.foodclub.utils.composables.videoPager.VideoPagerEvents
import java.io.File

interface HomeEvents: VideoPagerEvents {
    fun postSnap(file: File)
    suspend fun userViewsStory(storyId: Long)
    fun toggleShowMemories(show: Boolean)
    fun toggleShowMemoriesReel(show: Boolean)
    fun selectReaction(reaction: Reactions)

}