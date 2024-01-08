package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.home.VideoStats
import android.kotlin.foodclub.domain.models.home.VideoUserInteraction
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserBookmarksModel
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class SharedVideoMapper : DomainMapper<Any, VideoModel> {

    override fun mapToDomainModel(entity: Any): VideoModel {
        return when (entity) {
            is OfflineUserBookmarksModel -> mapBookmarksToVideoModel(entity)
            is OfflineUserPostsModel -> mapPostsToVideoModel(entity)
            else -> throw IllegalArgumentException("Unknown type")
        }
    }
    private fun mapBookmarksToVideoModel(model: OfflineUserBookmarksModel): VideoModel {
        val videoStats = VideoStats(
            like = model.totalLikes ?: 0L,
            views = model.totalViews ?: (model.totalLikes ?: 500L).plus((500..100000).random())
        )

        val authorDetails = "Author Name"

        return VideoModel(
            videoId = model.videoId,
            authorDetails = authorDetails,
            videoStats = videoStats,
            videoLink = model.videoLink ?: "",
            description = model.description ?: "",
            createdAt = model.createdAt ?: "${(1..24).random()}h",
            thumbnailLink = model.thumbnailLink ?: "",
            currentViewerInteraction = VideoUserInteraction(isBookmarked = true)
        )
    }

    private fun mapPostsToVideoModel(model: OfflineUserPostsModel): VideoModel {
        val videoStats = VideoStats(
            like = model.totalLikes ?: 0L,
            views = model.totalViews ?: (model.totalLikes ?: 500L).plus((500..100000).random())
        )

        val authorDetails = "Author Name"

        return VideoModel(
            videoId = model.videoId,
            authorDetails = authorDetails,
            videoStats = videoStats,
            videoLink = model.videoLink ?: "",
            description = model.description ?: "",
            createdAt = model.createdAt ?: "${(1..24).random()}h",
            thumbnailLink = model.thumbnailLink ?: "",
            currentViewerInteraction = VideoUserInteraction(isBookmarked = true)
        )
    }


    override fun mapFromDomainModel(domainModel: VideoModel): Any {
        TODO("Not yet implemented")
    }
}