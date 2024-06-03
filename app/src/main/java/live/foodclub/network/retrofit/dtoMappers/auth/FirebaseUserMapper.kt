package live.foodclub.network.retrofit.dtoMappers.auth

import live.foodclub.domain.models.auth.FirebaseUserModel
import live.foodclub.domain.models.auth.SignUpUser
import live.foodclub.network.retrofit.utils.DomainMapper

class FirebaseUserMapper : DomainMapper<FirebaseUserModel, SignUpUser> {

    override fun mapToDomainModel(entity: FirebaseUserModel): SignUpUser {
        return SignUpUser(
            id = entity.userID,
            username = entity.username,
            profileImageUrl = entity.profileImageUrl,
            name = entity.fullName,
            accessToken = entity.accessToken,
            idToken = entity.idToken,
            refreshToken = entity.refreshToken,
            email = entity.email,
            password = ""
        )
    }

    override fun mapFromDomainModel(domainModel: SignUpUser): FirebaseUserModel {
        return FirebaseUserModel(
            userID = domainModel.id ?: -1,
            username = domainModel.username,
            fullName = domainModel.name,
            profileImageUrl = domainModel.profileImageUrl ?: "",
            accessToken = domainModel.accessToken ?: "",
            idToken = domainModel.idToken ?: "",
            refreshToken = domainModel.refreshToken ?: "",
            fcmToken = domainModel.fcmToken ?: "",
            isOnline = domainModel.isOnline ?: false,
        )
    }
}
