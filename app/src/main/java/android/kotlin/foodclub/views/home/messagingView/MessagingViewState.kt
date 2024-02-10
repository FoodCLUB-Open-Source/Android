package android.kotlin.foodclub.views.home.messagingView

data class MessagingViewState(
    val userId: Long
){
    companion object {
        fun default() = MessagingViewState(
            userId = 0
        )
    }
}
