package live.foodclub.localdatasource.util

import live.foodclub.utils.helpers.Resource

inline fun <reified T, reified E> daoRequestFlow(call: () -> T): Resource<T, E> {
    return try {
        val result = call()
        Resource.Success(result)
    } catch (e: Exception) {
        Resource.Error("Room Error $e.")
    }
}

