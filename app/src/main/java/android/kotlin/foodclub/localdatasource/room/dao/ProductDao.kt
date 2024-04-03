package android.kotlin.foodclub.localdatasource.room.dao

import android.kotlin.foodclub.localdatasource.room.entity.ProductEntity
import android.kotlin.foodclub.localdatasource.room.entity.ProductUnitEntity
import android.kotlin.foodclub.localdatasource.room.relationships.ProductWithUnits
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Upsert
    suspend fun insertProductsData(productEntities: List<ProductEntity>)

    @Upsert
    suspend fun insertProductUnits(productUnitEntities: List<ProductUnitEntity>)

    @Transaction
    suspend fun insertProductsWithUnit(productsWithUnits: List<ProductWithUnits>) {
        val allUnits = productsWithUnits.map { it.units }.flatten()

        insertProductsData(productsWithUnits.map { it.productEntity })
        insertProductUnits(allUnits)
    }

    @Transaction
    suspend fun insertPaginatedData(productsWithUnits: List<ProductWithUnits>, loadType: LoadType) {
        if(loadType == LoadType.REFRESH) {
            clearAll()
        }
        insertProductsWithUnit(productsWithUnits)
    }

    @Query("SELECT COUNT(*) FROM products")
    fun countRows(): Flow<Int>

    @Transaction
    @Query("SELECT * FROM products")
    fun getProductsWithUnits(): PagingSource<Int, ProductWithUnits>

    @Query("DELETE FROM products")
    fun clearAll()
}