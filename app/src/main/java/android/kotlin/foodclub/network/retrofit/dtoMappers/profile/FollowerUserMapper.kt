package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.FollowerUserDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

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