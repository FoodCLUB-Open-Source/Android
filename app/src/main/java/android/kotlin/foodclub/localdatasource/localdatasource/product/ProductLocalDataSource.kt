package android.kotlin.foodclub.localdatasource.localdatasource.product

import android.kotlin.foodclub.localdatasource.room.relationships.ProductWithUnits
import androidx.paging.PagingSource

interface ProductLocalDataSource {
    suspend fun insertProductsWithUnits(productsWithUnits: List<ProductWithUnits>)

    fun pagingSource(): PagingSource<Int, ProductWithUnits>

}