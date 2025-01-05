
// IntellectIslandRoomDatabase.kt
package uk.ac.aber.dcs.cs31620.intellectisland.datasource1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData

@Database(entities = [QuestionData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class IntellectIslandRoomDatabase : RoomDatabase() {
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
