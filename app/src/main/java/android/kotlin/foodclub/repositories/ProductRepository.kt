package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.network.retrofit.apiInterfaces.ProductsService
import android.kotlin.foodclub.network.retrofit.dtoMappers.edamam.EdamamFoodProductsMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductsDto
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

class ProductRepository(
    private val api: ProductsService,
    private val mapper: EdamamFoodProductsMapper
) {
    private val APP_ID = "2c8da0a4"
    private val APP_KEY = "eae6a559369343cff81b31a2cda90caf"
    suspend fun getProductsList(
        searchText: String, session: Int? = null
    ): Resource<ProductsData, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<EdamamFoodProductsDto, DefaultErrorResponse> {
                api.getFoodProducts(session, APP_ID, APP_KEY, searchText)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    mapper.mapToDomainModel(resource.data!!.body()!!)
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}