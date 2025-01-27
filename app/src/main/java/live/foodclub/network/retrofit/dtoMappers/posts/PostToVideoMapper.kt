package live.foodclub.network.retrofit.dtoMappers.posts

import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.home.VideoUserInteraction
import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import live.foodclub.network.retrofit.utils.DomainMapper

class PostToVideoMapper: DomainMapper<PostModelDto, VideoModel> {
    override fun mapToDomainModel(entity: PostModelDto): VideoModel {
        return VideoModel(
            videoId = entity.id,
            authorDetails = SimpleUserModel(entity.user.id, entity.user.username ?: "Marc", entity.user.profilePictureUrl ),
            title = entity.title,
            videoStats = VideoStats(
                entity.likes ?: 15,
                0L,
                0L,
                0L,
                entity.views ?: 100
            ),
            videoLink = entity.videoUrl,
            description = entity.description ?: "",
            thumbnailLink = entity.thumbnailUrl,
            currentViewerInteraction = VideoUserInteraction(
                isLiked = entity.isLiked ?: false,
                isBookmarked = entity.isBookmarked
            )
        )
    }

    override fun mapFromDomainModel(domainModel: VideoModel): PostModelDto {
        TODO("Not yet implemented")
    }
}