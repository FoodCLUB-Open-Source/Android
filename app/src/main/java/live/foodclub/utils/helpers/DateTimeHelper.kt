package live.foodclub.utils.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeHelper {
    companion object {
        fun formatMessageTime(messageTime: String?): String {
            if (messageTime.isNullOrBlank()) return ""

            val lastMessageTime = LocalDateTime.parse(messageTime)
            val currentTime = LocalDateTime.now()
            val lastMessageDay = lastMessageTime.dayOfMonth

            val today = currentTime.dayOfMonth
            val yesterday = currentTime.minusDays(1).dayOfMonth

            return when (lastMessageDay) {
                today -> lastMessageTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                yesterday -> "Yesterday"
                else -> lastMessageTime.format(DateTimeFormatter.ofPattern("MM/dd"))
            }
        }
    }
}