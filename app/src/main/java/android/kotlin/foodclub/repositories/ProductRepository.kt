package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.localdatasource.localdatasource.product.ProductLocalDataSource
import android.kotlin.foodclub.localdatasource.room.relationships.ProductWithUnits
import android.kotlin.foodclub.network.remotedatasource.product.ProductRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.product.ProductRemoteMediator
import android.kotlin.foodclub.network.retrofit.services.ProductsService
import android.kotlin.foodclub.network.retrofit.dtoMappers.edamam.EdamamFoodProductsMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductsDto
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig

class ProductRepository(
    private val api: ProductsService,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource,
    private val mapper: EdamamFoodProductsMapper
) {
    private val APP_ID = "2c8da0a4"
    private val APP_KEY = "eae6a559369343cff81b31a2cda90caf"
    suspend fun getProductsList(
        searchText: String, session: Int? = null
    ): Resource<ProductsData, DefaultErrorResponse> {
        Log.d("ProductRepository", "Testing API request: $searchText")
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

    @OptIn(ExperimentalPagingApi::class)
    fun getProducts(searchText: String): Pager<Int, ProductWithUnits> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ProductRemoteMediator(
                searchString = searchText,
                productRemoteDataSource = productRemoteDataSource,
                productLocalDataSource = productLocalDataSource
            ),
            pagingSourceFactory = { productLocalDataSource.pagingSource() }
        )
    }
}