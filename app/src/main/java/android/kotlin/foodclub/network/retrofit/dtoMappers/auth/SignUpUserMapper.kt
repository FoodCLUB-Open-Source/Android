package android.kotlin.foodclub.network.retrofit.dtoMappers.auth

import android.kotlin.foodclub.domain.models.auth.SignUpUser
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignUpUserDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class SignUpUserMapper: DomainMapper<SignUpUserDto, SignUpUser> {
    override fun mapToDomainModel(entity: SignUpUserDto): SignUpUser {
        return SignUpUser(
            username = entity.username,
            email = entity.email,
            password = entity.password,
            name = entity.name
        )
    }

    override fun mapFromDomainModel(domainModel: SignUpUser): SignUpUserDto {
        return SignUpUserDto(
            username = domainModel.username,
            email = domainModel.email,
            password = domainModel.password,
            name = domainModel.name
        )
    }

}