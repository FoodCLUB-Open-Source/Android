package android.kotlin.foodclub.room.converters

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromListInt(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListInt(data: String): List<String> {
        return listOf(*data.split(",").map { it.ifEmpty { "" } }
            .toTypedArray())
    }
}
