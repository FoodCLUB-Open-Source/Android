package android.kotlin.foodclub.localdatasource.room.converters

import androidx.room.TypeConverter

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
}
