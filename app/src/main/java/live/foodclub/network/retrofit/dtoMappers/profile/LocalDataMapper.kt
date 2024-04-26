package live.foodclub.network.retrofit.dtoMappers.profile

import live.foodclub.localdatasource.room.entity.ProfileEntity
import live.foodclub.network.retrofit.dtoModels.profile.UserInfoDto
import live.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import live.foodclub.network.retrofit.utils.DomainMapper

class LocalDataMapper : DomainMapper<UserProfileDto, ProfileEntity> {
    override fun mapToDomainModel(entity: UserProfileDto): ProfileEntity {
        return ProfileEntity(
            userId = entity.userInfo.id,
            userName = entity.userInfo.username,
            fullName = entity.userInfo.fullName ?: "Undefined",
            profilePicture = entity.userInfo.profilePictureUrl,
            totalUserFollowers = entity.totalUserFollowers,
            totalUserFollowing = entity.totalUserFollowing,
            totalUserLikes = entity.totalUserLikes
        )
    }

    override fun mapFromDomainModel(domainModel: ProfileEntity): UserProfileDto {
        return UserProfileDto(
            userInfo = UserInfoDto(
                domainModel.userId,
                domainModel.userName,
                domainModel.profilePicture,
                domainModel.fullName
            ),
            totalUserLikes = domainModel.totalUserLikes!!,
            totalUserFollowers = domainModel.totalUserFollowers!!,
            totalUserFollowing = domainModel.totalUserFollowing!!,

            userPosts = emptyList(),
            topCreators = emptyList()
        )
    }
}