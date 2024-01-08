package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileModel
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class OfflineProfileDataMapper : DomainMapper<OfflineProfileModel, UserProfile> {

    override fun mapToDomainModel(entity: OfflineProfileModel): UserProfile {
        return UserProfile(
            username = entity.userName,
            profilePictureUrl = entity.profilePicture,
            totalUserLikes = entity.totalUserLikes!!,
            totalUserFollowers = entity.totalUserFollowers!!,
            totalUserFollowing = entity.totalUserFollowing!!,

            userPosts = emptyList(),
            topCreators = emptyList()
        )
    }

    override fun mapFromDomainModel(domainModel: UserProfile): OfflineProfileModel {
        TODO("Not yet implemented")
    }
}