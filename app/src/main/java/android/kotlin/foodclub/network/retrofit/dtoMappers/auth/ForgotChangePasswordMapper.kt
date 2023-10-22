package android.kotlin.foodclub.network.retrofit.dtoMappers.auth

import android.kotlin.foodclub.domain.models.auth.ForgotChangePassword
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.ForgotChangePasswordDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class ForgotChangePasswordMapper: DomainMapper<ForgotChangePasswordDto, ForgotChangePassword> {
    override fun mapToDomainModel(entity: ForgotChangePasswordDto): ForgotChangePassword {
        return ForgotChangePassword(
            username = entity.username,
            code = entity.code,
            newPassword = entity.newPassword
        )
    }

    override fun mapFromDomainModel(domainModel: ForgotChangePassword): ForgotChangePasswordDto {
        return ForgotChangePasswordDto(
            username = domainModel.username,
            code = domainModel.code,
            newPassword = domainModel.newPassword
        )
    }

}