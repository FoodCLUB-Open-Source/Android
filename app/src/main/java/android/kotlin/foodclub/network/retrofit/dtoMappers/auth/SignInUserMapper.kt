package android.kotlin.foodclub.network.retrofit.dtoMappers.auth

import android.kotlin.foodclub.domain.models.auth.SignInUser
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignInUserDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class SignInUserMapper: DomainMapper<SignInUserDto, SignInUser> {
    override fun mapToDomainModel(entity: SignInUserDto): SignInUser {
        return SignInUser(
            id = entity.id,
            username = entity.username,
            profileImageUrl = entity.profileImageUrl ?: ""
        )
    }

    override fun mapFromDomainModel(domainModel: SignInUser): SignInUserDto {
        return SignInUserDto(
            id = domainModel.id,
            username = domainModel.username,
            profileImageUrl = domainModel.profileImageUrl
        )
    }
}