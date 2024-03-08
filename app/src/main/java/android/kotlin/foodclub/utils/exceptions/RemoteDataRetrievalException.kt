package android.kotlin.foodclub.utils.exceptions

class RemoteDataRetrievalException(
    override val message: String? = "Cannot retrieve data from remote datasource"
): Exception() {
}