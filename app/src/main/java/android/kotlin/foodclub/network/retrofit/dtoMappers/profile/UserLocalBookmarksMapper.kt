package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserBookmarksModel
import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class UserLocalBookmarksMapper : DomainMapper<List<PostModelDto>?, List<OfflineUserBookmarksModel>> {

    override fun mapToDomainModel(entity: List<PostModelDto>?): List<OfflineUserBookmarksModel> {
        return entity?.map { dto ->
            OfflineUserBookmarksModel(
                videoId = dto.id,
                title = dto.title,
                description = dto.description,
                createdAt = dto.createdAt,
                videoLink = dto.videoUrl,
                thumbnailLink = dto.thumbnailUrl,
                totalLikes = dto.likes,
                totalViews = dto.views,
            )
        } ?: emptyList()
    }

    override fun mapFromDomainModel(domainModel: List<OfflineUserBookmarksModel>): List<PostModelDto>? {
        TODO("Not yet implemented")
    }
}