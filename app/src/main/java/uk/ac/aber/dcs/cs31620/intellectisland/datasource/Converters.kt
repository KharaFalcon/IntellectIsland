package uk.ac.aber.dcs.cs31620.intellectisland.datasource


import androidx.room.TypeConverter

/**
 * I needed to create converters to be able to handle the datatype I selected for the quiz in the room database.
 * I need this to be able to access and use the lists I have created.
 */
class Converters {
    @TypeConverter
    // Converts list to comma-separated string
    fun fromListToString(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    // Converts string back to list
    fun fromStringToList(data: String?): List<String>? {
        return data?.split(",")
    }
}
