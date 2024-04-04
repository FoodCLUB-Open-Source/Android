package android.kotlin.foodclub.network.retrofit.responses.stories

import android.kotlin.foodclub.network.retrofit.dtoModels.stories.StoryDetailDto
import androidx.annotation.Keep

@Keep
data class RetrieveUserViewedStoryResponse(
    val stories: List<StoryDetailDto>
)
