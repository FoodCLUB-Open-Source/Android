package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class UserLocalPostsMapper : DomainMapper<List<PostModelDto>?, List<OfflineUserPostsModel>> {

    override fun mapToDomainModel(entity: List<PostModelDto>?): List<OfflineUserPostsModel> {
        return entity?.map { dto ->
            OfflineUserPostsModel(
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

    override fun mapFromDomainModel(domainModel: List<OfflineUserPostsModel>): List<PostModelDto>? {
        // Implement the reverse mapping if needed
        TODO("Not yet implemented")
    }
}