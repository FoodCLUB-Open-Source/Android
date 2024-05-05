package live.foodclub.network.retrofit.dtoMappers.auth

import live.foodclub.domain.models.auth.ForgotChangePassword
import live.foodclub.network.retrofit.dtoModels.auth.ForgotChangePasswordDto
import live.foodclub.network.retrofit.utils.DomainMapper

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