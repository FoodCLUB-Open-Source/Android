package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.FollowingUserDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

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