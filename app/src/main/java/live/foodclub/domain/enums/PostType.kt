package live.foodclub.domain.enums

enum class PostType(val value: Int) {
    PROFILE(0),
    BOOKMARK(1),
    HOME(2),
    DISCOVER(3);

    companion object {
        fun parsePostType(value: Int): PostType {
            return entries.firstOrNull { it.value == value } ?: HOME
        }
    }

}