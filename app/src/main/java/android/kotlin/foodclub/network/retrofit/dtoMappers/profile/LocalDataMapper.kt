package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.localdatasource.room.entity.ProfileEntity
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class LocalDataMapper : DomainMapper<UserProfileDto, ProfileEntity> {
    override fun mapToDomainModel(entity: UserProfileDto): ProfileEntity {
        return ProfileEntity(
            userId = 0,
            userName = entity.username,
            profilePicture = entity.profilePictureUrl,
            totalUserFollowers = entity.totalUserFollowers,
            totalUserFollowing = entity.totalUserFollowing,
            totalUserLikes = entity.totalUserLikes
        )
    }

    override fun mapFromDomainModel(domainModel: ProfileEntity): UserProfileDto {
        return UserProfileDto(
            username = domainModel.userName,
            profilePictureUrl = domainModel.profilePicture,
            totalUserLikes = domainModel.totalUserLikes!!,
            totalUserFollowers = domainModel.totalUserFollowers!!,
            totalUserFollowing = domainModel.totalUserFollowing!!,

            userPosts = emptyList(),
            topCreators = emptyList()
        )
    }
}