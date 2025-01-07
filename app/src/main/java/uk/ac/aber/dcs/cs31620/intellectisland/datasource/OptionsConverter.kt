package uk.ac.aber.dcs.cs31620.intellectisland.datasource

import androidx.room.TypeConverter

object OptionsConverter {
    @TypeConverter
    @JvmStatic
    fun fromOptionsList(options: List<String>): String {
        return options.joinToString(separator = ",")
    }

    @TypeConverter
    @JvmStatic
    fun toOptionsList(data: String): List<String> {
        return data.split(",")
    }
}
