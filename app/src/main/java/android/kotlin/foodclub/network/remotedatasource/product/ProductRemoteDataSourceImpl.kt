package android.kotlin.foodclub.network.remotedatasource.product

import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductsDto
import android.kotlin.foodclub.network.retrofit.services.ProductsService
import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val productsService: ProductsService
): ProductRemoteDataSource {
    override suspend fun retrieveProductsData(
        session: Int?,
        appId: String,
        appKey: String,
        searchString: String
    ): Response<EdamamFoodProductsDto> {
        return productsService.getFoodProducts(session, appId, appKey, searchString)
    }
}