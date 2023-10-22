package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserPostsDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class UserPostsMapper: DomainMapper<UserPostsDto, UserPosts> {
    override fun mapToDomainModel(entity: UserPostsDto): UserPosts {
        return UserPosts(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            dateCreated = entity.dateCreated,
            videoUrl = entity.videoUrl,
            thumbnailUrl = entity.thumbnailUrl,
            totalLikes = entity.totalLikes,
            totalViews = entity.totalViews
        )
    }

    override fun mapFromDomainModel(domainModel: UserPosts): UserPostsDto {
        return UserPostsDto(
            id = domainModel.id,
            title = domainModel.title,
            description = domainModel.description,
            dateCreated = domainModel.dateCreated,
            videoUrl = domainModel.videoUrl,
            thumbnailUrl = domainModel.thumbnailUrl,
            totalLikes = domainModel.totalLikes,
            totalViews = domainModel.totalViews
        )
    }
}