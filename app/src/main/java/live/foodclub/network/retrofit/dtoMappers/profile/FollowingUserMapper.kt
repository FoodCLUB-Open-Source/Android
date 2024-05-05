package live.foodclub.network.retrofit.dtoMappers.profile

import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.network.retrofit.dtoModels.profile.FollowingUserDto
import live.foodclub.network.retrofit.utils.DomainMapper

class FollowingUserMapper: DomainMapper<FollowingUserDto, SimpleUserModel> {
    override fun mapToDomainModel(entity: FollowingUserDto): SimpleUserModel {
        return SimpleUserModel(
            userId = entity.userId,
            username = entity.username,
            profilePictureUrl = entity.profilePictureUrl
        )
    }

    override fun mapFromDomainModel(domainModel: SimpleUserModel): FollowingUserDto {
        return FollowingUserDto(
            userId = domainModel.userId,
            username = domainModel.username,
            profilePictureUrl = domainModel.profilePictureUrl
        )
    }
}