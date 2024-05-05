package android.kotlin.foodclub.localdatasource.localdatasource.product

import android.kotlin.foodclub.localdatasource.room.dao.ProductDao
import android.kotlin.foodclub.localdatasource.room.relationships.ProductWithUnits
import androidx.paging.LoadType
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
): ProductLocalDataSource {
    override suspend fun insertProductsWithUnits(productsWithUnits: List<ProductWithUnits>) {
        return productDao.insertProductsWithUnit(productsWithUnits)
    }

    override fun pagingSource(): PagingSource<Int, ProductWithUnits> {
        return productDao.getProductsWithUnits()
    }

    override suspend fun insertPaginatedData(
        paginatedData: List<ProductWithUnits>, loadType: LoadType
    ) {
        productDao.insertPaginatedData(paginatedData, loadType)
    }

    override suspend fun countRows(): Int? {
        return productDao.countRows().firstOrNull()
    }
}