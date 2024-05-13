package live.foodclub.network.retrofit.dtoMappers.auth

import live.foodclub.domain.models.auth.FirebaseUserModel
import live.foodclub.domain.models.auth.SignInUser
import live.foodclub.network.retrofit.utils.DomainMapper

class FirebaseUserMapper : DomainMapper<FirebaseUserModel, SignInUser> {

    override fun mapToDomainModel(entity: FirebaseUserModel): SignInUser {
        return SignInUser(
            id = entity.userID,
            username = entity.username,
            profileImageUrl = entity.profileImageUrl,
            fullName = entity.fullName,
            accessToken = entity.accessToken,
            idToken = entity.idToken,
            refreshToken = entity.refreshToken,
        )
    }

    override fun mapFromDomainModel(domainModel: SignInUser): FirebaseUserModel {
        return FirebaseUserModel(
            userID = domainModel.id,
            username = domainModel.username,
            fullName = domainModel.fullName ?: "",
            profileImageUrl = domainModel.profileImageUrl ?: "",
            accessToken = domainModel.accessToken,
            idToken = domainModel.idToken,
            refreshToken = domainModel.refreshToken,
            fcmToken = domainModel.fcmToken ?: "",
            isOnline = domainModel.isOnline ?: false,
        )
    }
}
