package live.foodclub.presentation.ui.home.search

interface SearchEvents {
    suspend fun searchByText(searchText: String)
}