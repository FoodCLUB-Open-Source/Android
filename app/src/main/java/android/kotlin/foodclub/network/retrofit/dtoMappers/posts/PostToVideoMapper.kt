package android.kotlin.foodclub.network.retrofit.dtoMappers.posts

import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.home.VideoStats
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class PostToVideoMapper: DomainMapper<PostModelDto, VideoModel> {
    override fun mapToDomainModel(entity: PostModelDto): VideoModel {
        return VideoModel(
            videoId = entity.id,
            authorDetails = entity.username ?: "Marc",
            videoStats = VideoStats(
                entity.likes ?: 15,
                0L,
                0L,
                0L,
                entity.views ?: 100
            ),
            videoLink = entity.videoUrl,
            description = entity.description,
            thumbnailLink = entity.thumbnailUrl,
            isLiked = entity.isLiked ?: false,
            isBookmarked = entity.isBookmarked ?: false
        )
    }

    override fun mapFromDomainModel(domainModel: VideoModel): PostModelDto {
        TODO("Not yet implemented")
    }
}