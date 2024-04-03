package android.kotlin.foodclub.network.remotedatasource.product

import android.kotlin.foodclub.localdatasource.localdatasource.product.ProductLocalDataSource
import android.kotlin.foodclub.localdatasource.room.relationships.ProductWithUnits
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductContainerDto
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.toProductWithUnits
import android.kotlin.foodclub.utils.exceptions.RemoteDataRetrievalException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val searchString: String,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource
): RemoteMediator<Int, ProductWithUnits>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductWithUnits>
    ): MediatorResult {

        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val rowsCount = productLocalDataSource.countRows()
                    if(rowsCount == null) {
                        null
                    } else {
                        rowsCount * 2
                    }
                }
            }
            val response = productRemoteDataSource.retrieveProductsData(
                session = loadKey,
                searchString = searchString,
            )

            if(!response.isSuccessful) throw RemoteDataRetrievalException("Could not retrieve products")
            val products: List<EdamamFoodProductContainerDto> = response.body()!!.hints

            val productsWithUnits = products.map { it.toProductWithUnits() }
            productLocalDataSource.insertPaginatedData(productsWithUnits, loadType)
//            foodClubDb.withTransaction {
//                if(loadType == LoadType.REFRESH) {
//                    dao.clearAll()
//                }
//                val productsWithUnits = products.map { it.toProductWithUnits() }
//                dao.insertProductsWithUnit(productsWithUnits)
//            }


            MediatorResult.Success(
                endOfPaginationReached = products.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: RemoteDataRetrievalException) {
            MediatorResult.Error(e)
        }
    }
}