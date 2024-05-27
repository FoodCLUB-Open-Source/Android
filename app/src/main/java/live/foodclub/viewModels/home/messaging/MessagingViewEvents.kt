package live.foodclub.viewModels.home.messaging

import live.foodclub.domain.models.profile.SimpleUserModel


interface MessagingViewEvents {
    fun filterContacts(searchText: String)
    fun setSearchText(searchText: String)
    fun getConversation(documentId: String)
    fun sendMessage(content: String, conversationId: String)
    fun deleteConversation(documentId: String)
    fun setFollowingsSearchText(searchText: String)
    fun filterFollowings(searchText: String)
    fun createConversation(recipient: SimpleUserModel)
    fun chatViewClear()
}