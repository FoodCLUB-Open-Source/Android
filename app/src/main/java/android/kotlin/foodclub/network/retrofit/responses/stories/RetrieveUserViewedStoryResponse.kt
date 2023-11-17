package android.kotlin.foodclub.network.retrofit.responses.stories

import android.kotlin.foodclub.network.retrofit.dtoModels.stories.StoryDetailDto

data class RetrieveUserViewedStoryResponse(
    val stories: List<StoryDetailDto>
)
