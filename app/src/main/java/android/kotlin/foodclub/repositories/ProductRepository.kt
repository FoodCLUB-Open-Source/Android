package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.network.retrofit.apiInterfaces.ProductsService
import android.kotlin.foodclub.network.retrofit.dtoMappers.EdamamFoodProductsMapper
import android.kotlin.foodclub.utils.helpers.Resource
import java.io.IOException

class ProductRepository(
    private val api: ProductsService,
    private val mapper: EdamamFoodProductsMapper
) {
    private val APP_ID = "2c8da0a4"
    private val APP_KEY = "eae6a559369343cff81b31a2cda90caf"
    suspend fun getProductsList(
        searchText: String, session: Int? = null
    ): Resource<ProductsData, DefaultErrorResponse> {
        val response = try {
            api.getFoodProducts(session, APP_ID, APP_KEY, searchText)
        } catch (e: IOException) {
            return Resource.Error(
                "Cannot retrieve data. Check your internet connection and try again."
            )
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null && response.body()?.hints != null
            && response.body()?.hints?.isEmpty() == false){
            return Resource.Success(mapper.mapToDomainModel(response.body()!!))
        }
        return Resource.Error("Unknown error occurred.")

    }
}