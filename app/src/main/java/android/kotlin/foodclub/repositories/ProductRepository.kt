package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.localdatasource.localdatasource.product.ProductLocalDataSource
import android.kotlin.foodclub.localdatasource.room.relationships.ProductWithUnits
import android.kotlin.foodclub.network.remotedatasource.product.ProductRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.product.ProductRemoteMediator
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig

class ProductRepository(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) {

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