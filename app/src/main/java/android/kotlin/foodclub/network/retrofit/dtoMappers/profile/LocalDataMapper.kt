package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileModel
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class LocalDataMapper : DomainMapper<UserProfileDto, OfflineProfileModel> {
    override fun mapToDomainModel(entity: UserProfileDto): OfflineProfileModel {
        return OfflineProfileModel(
            userId = null,
            userName = entity.username,
            profilePicture = entity.profilePictureUrl,
            totalUserFollowers = entity.totalUserFollowers,
            totalUserFollowing = entity.totalUserFollowing,
            totalUserLikes = entity.totalUserLikes
        )
    }

    override fun mapFromDomainModel(domainModel: OfflineProfileModel): UserProfileDto {
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