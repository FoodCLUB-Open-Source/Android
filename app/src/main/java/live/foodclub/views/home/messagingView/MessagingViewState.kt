package live.foodclub.views.home.messagingView

data class MessagingViewState(
    val userId: Long,
    val userMessagesHistory: List<MessagingViewData>,
    val messagingViewSearchText: String = "",
    val userSearchResult: List<MessagingViewData>
){

    companion object {
        fun default() = MessagingViewState(
            userId = 0,
            userMessagesHistory = emptyList(),
            messagingViewSearchText = "",
            emptyList()
        )
    }
}
