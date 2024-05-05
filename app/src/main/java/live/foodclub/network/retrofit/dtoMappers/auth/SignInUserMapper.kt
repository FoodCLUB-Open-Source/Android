package live.foodclub.network.retrofit.dtoMappers.auth

import live.foodclub.domain.models.auth.SignInUser
import live.foodclub.network.retrofit.dtoModels.auth.SignInUserDto
import live.foodclub.network.retrofit.responses.auth.LoginResponse
import live.foodclub.network.retrofit.utils.DomainMapper

class SignInUserMapper: DomainMapper<LoginResponse, SignInUser> {
    override fun mapToDomainModel(entity: LoginResponse): SignInUser {
        return SignInUser(
            id = entity.user.id,
            username = entity.user.username,
            profileImageUrl = entity.user.profileImageUrl,
            fullName = entity.user.fullName,
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
                fullName = domainModel.fullName
            ),
            accessToken = domainModel.accessToken,
            idToken = domainModel.idToken,
            refreshToken = domainModel.refreshToken
        )
    }
}