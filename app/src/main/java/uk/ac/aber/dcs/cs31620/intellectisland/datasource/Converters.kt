package uk.ac.aber.dcs.cs31620.intellectisland.datasource


import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return list?.joinToString(",") // Convert list to comma-separated string
    }

    @TypeConverter
    fun fromStringToList(data: String?): List<String>? {
        return data?.split(",") // Convert string back to list
    }
}
