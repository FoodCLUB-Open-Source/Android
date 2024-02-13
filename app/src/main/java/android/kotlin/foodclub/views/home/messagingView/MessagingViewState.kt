package android.kotlin.foodclub.views.home.messagingView

data class MessagingViewState(
    val userId: Long,
    val userMessages: List<MessagingSingleUser>
){
    companion object {
        fun default() = MessagingViewState(
            userId = 0,
            userMessages = emptyList()
        )
    }
}
