package android.kotlin.foodclub.network.retrofit.dtoMappers.auth

import android.kotlin.foodclub.domain.models.auth.SignInUser
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignInUserDto
import android.kotlin.foodclub.network.retrofit.responses.auth.LoginResponse
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class SignInUserMapper: DomainMapper<LoginResponse, SignInUser> {
    override fun mapToDomainModel(entity: LoginResponse): SignInUser {
        return SignInUser(
            id = entity.user.id,
            username = entity.user.username,
            profileImageUrl = entity.user.profileImageUrl ?: "",
            accessToken = entity.accessToken,
            idToken = entity.idToken,
            refreshToken = entity.refreshToken
        )
    }

    override fun mapFromDomainModel(domainModel: SignInUser): LoginResponse {
        return LoginResponse(
            user = SignInUserDto(
                id = domainModel.id,
                username = domainModel.username,
                profileImageUrl = domainModel.profileImageUrl,
            ),
            accessToken = domainModel.accessToken,
            idToken = domainModel.idToken,
            refreshToken = domainModel.refreshToken
        )
    }
}