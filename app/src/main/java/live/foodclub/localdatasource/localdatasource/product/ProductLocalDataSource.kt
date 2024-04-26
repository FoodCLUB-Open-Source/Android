package live.foodclub.localdatasource.localdatasource.product

import live.foodclub.localdatasource.room.relationships.ProductWithUnits
import androidx.paging.LoadType
import androidx.paging.PagingSource

interface ProductLocalDataSource {
    suspend fun insertProductsWithUnits(productsWithUnits: List<ProductWithUnits>)

    fun pagingSource(): PagingSource<Int, ProductWithUnits>

    suspend fun insertPaginatedData(paginatedData: List<ProductWithUnits>, loadType: LoadType)

    suspend fun countRows(): Int?
}