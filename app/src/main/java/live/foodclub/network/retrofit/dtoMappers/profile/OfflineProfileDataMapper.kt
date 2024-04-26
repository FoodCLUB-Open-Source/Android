package live.foodclub.network.retrofit.dtoMappers.profile

import live.foodclub.domain.models.profile.UserProfile
import live.foodclub.localdatasource.room.entity.ProfileEntity
import live.foodclub.network.retrofit.utils.DomainMapper

class OfflineProfileDataMapper : DomainMapper<ProfileEntity, UserProfile> {

    override fun mapToDomainModel(entity: ProfileEntity): UserProfile {
        return UserProfile(
            username = entity.userName,
            profilePictureUrl = entity.profilePicture,
            totalUserLikes = entity.totalUserLikes,
            totalUserFollowers = entity.totalUserFollowers,
            totalUserFollowing = entity.totalUserFollowing,
        )
    }

    override fun mapFromDomainModel(domainModel: UserProfile): ProfileEntity {
        TODO("Not yet implemented")
    }
}