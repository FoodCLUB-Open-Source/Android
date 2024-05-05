package live.foodclub.network.retrofit.dtoMappers.profile

import live.foodclub.localdatasource.room.entity.UserDetailsModel
import live.foodclub.network.retrofit.dtoModels.profile.UserDetailsDto
import live.foodclub.network.retrofit.utils.DomainMapper

class UserDetailsMapper: DomainMapper<UserDetailsDto, UserDetailsModel> {
    override fun mapToDomainModel(entity: UserDetailsDto): UserDetailsModel {
        return UserDetailsModel(
            id = entity.id,
            userName = entity.userName,
            email = entity.email,
            phoneNumber = entity.phoneNumber,
            profilePicture = entity.profilePicture,
            userBio = entity.userBio,
            gender = entity.gender,
            createdAt = entity.createdAt,
            dateOfBirth = entity.dateOfBirth,
            dietaryPrefs = entity.dietaryPrefs,
            country = null,
            shippingAddress = null,
            fullName = null
        )
    }

    override fun mapFromDomainModel(domainModel: UserDetailsModel): UserDetailsDto {

        return UserDetailsDto(
            id = domainModel.id,
            userName = domainModel.userName,
            email = domainModel.email,
            phoneNumber = domainModel.phoneNumber,
            profilePicture = domainModel.profilePicture ?: "default.jpg",
            userBio = domainModel.userBio,
            gender = domainModel.gender,
            createdAt = domainModel.createdAt,
            dateOfBirth = domainModel.dateOfBirth,
            dietaryPrefs = domainModel.dietaryPrefs,
            country = null,
            shippingAddress = null,
            fullName = null
        )
    }
}