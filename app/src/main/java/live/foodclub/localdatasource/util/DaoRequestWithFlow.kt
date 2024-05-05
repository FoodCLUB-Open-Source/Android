package live.foodclub.localdatasource.util

import live.foodclub.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

suspend inline fun <reified T, reified E> daoRequestWithFlow(call: () -> Flow<T>): Resource<T, E> {
    return try {
        val result = call().first()
        Resource.Success(result)
    } catch (e: Exception) {
        Resource.Error("Room Error $e.")
    }
}
