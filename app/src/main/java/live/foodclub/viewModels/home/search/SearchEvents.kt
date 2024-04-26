package live.foodclub.viewModels.home.search

interface SearchEvents {
    suspend fun searchByText(searchText: String)
}