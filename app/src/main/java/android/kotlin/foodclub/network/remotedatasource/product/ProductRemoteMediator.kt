package android.kotlin.foodclub.network.remotedatasource.product

import android.kotlin.foodclub.localdatasource.room.database.FoodCLUBDatabase
import android.kotlin.foodclub.localdatasource.room.relationships.ProductWithUnits
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.EdamamFoodProductContainerDto
import android.kotlin.foodclub.network.retrofit.dtoModels.edamam.toProductWithUnits
import android.kotlin.foodclub.utils.exceptions.RemoteDataRetrievalException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import kotlin.math.ceil

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val searchString: String,
    private val foodClubDb: FoodCLUBDatabase,
    private val productRemoteDataSource: ProductRemoteDataSource,
): RemoteMediator<Int, ProductWithUnits>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductWithUnits>
    ): MediatorResult {
        val dao = foodClubDb.getProductDao()

        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val rowsCount = dao.countRows().firstOrNull()
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

            foodClubDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    dao.clearAll()
                }
                val productsWithUnits = products.map { it.toProductWithUnits() }
                dao.insertProductsWithUnit(productsWithUnits)
            }

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