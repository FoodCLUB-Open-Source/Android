package live.foodclub.network.retrofit.responses.stories

import live.foodclub.network.retrofit.dtoModels.stories.StoryDetailDto
import androidx.annotation.Keep

@Keep
data class RetrieveUserViewedStoryResponse(
    val stories: List<StoryDetailDto>
)
