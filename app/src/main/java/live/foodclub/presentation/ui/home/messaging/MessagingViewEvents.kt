package live.foodclub.presentation.ui.home.messaging

interface MessagingViewEvents {
    fun filterMessages(searchText: String)
    fun setSearchText(searchText: String)
}