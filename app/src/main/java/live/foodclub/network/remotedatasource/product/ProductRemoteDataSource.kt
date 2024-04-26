package live.foodclub.network.remotedatasource.product

import live.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductsDto
import retrofit2.Response

interface ProductRemoteDataSource {
    suspend fun retrieveProductsData(
        session: Int?,
        appId: String = "2c8da0a4",
        appKey: String = "eae6a559369343cff81b31a2cda90caf",
        searchString: String,
    ): Response<EdamamFoodProductsDto>
}