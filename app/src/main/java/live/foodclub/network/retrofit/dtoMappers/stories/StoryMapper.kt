package live.foodclub.network.retrofit.dtoMappers.stories

import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.network.retrofit.dtoModels.stories.FriendStoryDto
import live.foodclub.network.retrofit.utils.DomainMapper
import kotlin.random.Random

class StoryMapper: DomainMapper<FriendStoryDto, List<VideoModel>> {
    override fun mapToDomainModel(entity: FriendStoryDto): List<VideoModel> {
        return entity.stories.map {
            VideoModel(
                videoId = Random.nextLong(),// use random for now because json data is not long causing NumberFormatException
                authorDetails = entity.username,
                videoStats = VideoStats(
                    15,
                    281L,
                    0L,
                    0L,
                    10
                ),
                videoLink = it.videoUrl, // use 0 index for now, later pass model list and loop it
                description = "Friends' Story",
                thumbnailLink = it.thumbnailUrl
            )
        }

    }

    override fun mapFromDomainModel(domainModel: List<VideoModel>): FriendStoryDto {
        TODO("Not yet implemented")
    }
}