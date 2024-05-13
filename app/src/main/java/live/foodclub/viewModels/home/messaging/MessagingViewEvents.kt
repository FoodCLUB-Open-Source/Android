package live.foodclub.viewModels.home.messaging


interface MessagingViewEvents {
    fun filterMessages(searchText: String)
    fun setSearchText(searchText: String)
    fun getConversation(documentId: String)
    fun sendMessage(content: String, conversationId: String)
    fun deleteConversation(documentId: String)
}