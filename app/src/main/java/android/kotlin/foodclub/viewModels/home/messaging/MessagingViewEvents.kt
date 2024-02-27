package android.kotlin.foodclub.viewModels.home.messaging

interface MessagingViewEvents {
    fun filterMessages(searchText: String)
    fun setSearchText(searchText: String)
}