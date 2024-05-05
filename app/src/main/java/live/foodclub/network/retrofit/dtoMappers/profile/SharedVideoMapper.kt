package live.foodclub.network.retrofit.dtoMappers.profile

import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.home.VideoUserInteraction
import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.localdatasource.room.entity.ProfileBookmarksEntity
import live.foodclub.localdatasource.room.entity.ProfilePostsEntity
import live.foodclub.network.retrofit.utils.DomainMapper

class SharedVideoMapper : DomainMapper<Any, VideoModel> {

    override fun mapToDomainModel(entity: Any): VideoModel {
        return when (entity) {
            is ProfileBookmarksEntity -> mapBookmarksToVideoModel(entity)
            is ProfilePostsEntity -> mapPostsToVideoModel(entity)
            else -> throw IllegalArgumentException("Unknown type")
        }
    }
    private fun mapBookmarksToVideoModel(model: ProfileBookmarksEntity): VideoModel {
        val videoStats = VideoStats(
            like = model.totalLikes ?: 0L,
            views = model.totalViews ?: (model.totalLikes ?: 500L).plus((500..100000).random())
        )

        val authorDetails = SimpleUserModel(userId = 0,username = "", profilePictureUrl = null)

        return VideoModel(
            videoId = model.videoId,
            authorDetails = authorDetails,
            videoStats = videoStats,
            videoLink = model.videoLink ?: "",
            description = model.description ?: "",
            createdAt = model.createdAt ?: "${(1..24).random()}h",
            thumbnailLink = model.thumbnailLink ?: "",
            currentViewerInteraction = VideoUserInteraction(
                isBookmarked = model.isBookmarked ?: false,
                isLiked = model.isLiked ?: false
            )
        )
    }

    private fun mapPostsToVideoModel(model: ProfilePostsEntity): VideoModel {
        val videoStats = VideoStats(
            like = model.totalLikes ?: 0L,
            views = model.totalViews ?: (model.totalLikes ?: 500L).plus((500..100000).random())
        )

        val authorDetails = SimpleUserModel(userId = 0,username = "", profilePictureUrl = null)

        return VideoModel(
            videoId = model.videoId,
            authorDetails = authorDetails,
            videoStats = videoStats,
            videoLink = model.videoLink ?: "",
            description = model.description ?: "",
            createdAt = model.createdAt ?: "${(1..24).random()}h",
            thumbnailLink = model.thumbnailLink ?: "",
            currentViewerInteraction = VideoUserInteraction(
                isBookmarked = model.isBookmarked ?: false,
                isLiked = model.isLiked ?: false
            )
        )
    }


    override fun mapFromDomainModel(domainModel: VideoModel): Any {
        TODO("Not yet implemented")
    }
}