package live.foodclub.localdatasource.room.converters

import androidx.room.TypeConverter
import live.foodclub.domain.enums.PostType

class Converters {

    @TypeConverter
    fun fromListString(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return listOf(*data.split(",").map { it.ifEmpty { "" } }
            .toTypedArray())
    }

    @TypeConverter
    fun fromPostType(value: PostType): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toPostType(value: Int): PostType {
        return PostType.parsePostType(value)
    }
}
