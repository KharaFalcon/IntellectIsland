package uk.ac.aber.dcs.cs31620.intellectisland.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.ac.aber.dcs.cs31620.intellectisland.datasource.model.QuestionData

/**
 * Room database where I store all information for the quiz and user.
 * This is also where I use my converters to allow for the data to be handled and stored correctly.
 */
@Database(entities = [QuestionData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class IntellectIslandRoomDatabase : RoomDatabase() {
    // Accesses the dao, that holds the database operation
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var INSTANCE: IntellectIslandRoomDatabase? = null

        fun getDatabase(context: Context): IntellectIslandRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IntellectIslandRoomDatabase::class.java,
                    "intellect_island_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
