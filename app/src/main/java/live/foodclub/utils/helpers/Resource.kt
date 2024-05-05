package live.foodclub.utils.helpers

sealed class Resource<T, Error>(
    val data: T? = null, val errorData: Error? = null, val message: String? = null
) {
    class Success<T, Error>(data: T) : Resource<T, Error>(data)
    class Error<T, Error>(
        message: String, data: Error? = null
    ) : Resource<T, Error>(null, data, message)
//    class Loading<T> : Resource<T>()
}
