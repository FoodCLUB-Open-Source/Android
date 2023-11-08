package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserDetailsDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class UserDetailsMapper: DomainMapper<UserDetailsDto, UserDetailsModel> {
    override fun mapToDomainModel(entity: UserDetailsDto): UserDetailsModel {
        return UserDetailsModel(
            id = entity.id,
            userName = entity.userName,
            email = entity.email,
            profilePicture = entity.profilePicture,
            createdAt = entity.createdAt
        )
    }

    override fun mapFromDomainModel(domainModel: UserDetailsModel): UserDetailsDto {
        return UserDetailsDto(
            id = domainModel.id,
            userName = domainModel.userName,
            email = domainModel.email,
            phoneNumber = null,
            profilePicture = domainModel.profilePicture,
            userBio = null,
            createdAt = domainModel.createdAt,
            dateOfBirth = null,
            dietaryPrefs = null
        )
    }
}