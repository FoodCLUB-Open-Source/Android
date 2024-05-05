package live.foodclub.network.retrofit.utils

interface DomainMapper <T, DomainModel> {
    fun mapToDomainModel(entity: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T
}