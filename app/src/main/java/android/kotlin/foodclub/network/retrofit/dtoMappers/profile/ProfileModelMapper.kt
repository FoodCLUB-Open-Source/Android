package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.room.entity.ProfileModel
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

// for room database profile entity
class ProfileModelMapper: DomainMapper<ProfileModel, UserDetailsModel > {
    override fun mapToDomainModel(entity: ProfileModel): UserDetailsModel {
        return UserDetailsModel(
            id = entity.userId,
            userName = entity.userName,
            email = entity.email,
            profilePicture = entity.profilePicture!!,
            userBio = null,
            gender = null,
            createdAt = entity.createdAt!!,
            dateOfBirth = null,
            dietaryPrefs = null,
            country = null,
            shippingAddress = null,
            fullName = null
        )
    }

    override fun mapFromDomainModel(domainModel: UserDetailsModel): ProfileModel {
        return ProfileModel(
            userId = domainModel.id,
            userName = domainModel.userName,
            email = domainModel.email,
            profilePicture = domainModel.profilePicture,
            createdAt = domainModel.createdAt
        )
    }
}