package live.foodclub.network.retrofit.dtoMappers.profile

import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.network.retrofit.dtoModels.profile.FollowerUserDto
import live.foodclub.network.retrofit.utils.DomainMapper

class FollowerUserMapper: DomainMapper<FollowerUserDto, SimpleUserModel> {
    override fun mapToDomainModel(entity: FollowerUserDto): SimpleUserModel {
        return SimpleUserModel(
            userId = entity.userId,
            username = entity.username,
            profilePictureUrl = entity.profilePictureUrl
        )
    }

    override fun mapFromDomainModel(domainModel: SimpleUserModel): FollowerUserDto {
        return FollowerUserDto(
            userId = domainModel.userId,
            username = domainModel.username,
            profilePictureUrl = domainModel.profilePictureUrl
        )
    }
}