package uk.ac.aber.dcs.cs31620.intellectisland.datasource1

    import android.content.Context
    import androidx.room.Database
    import androidx.room.Room
    import androidx.room.RoomDatabase
    import androidx.room.TypeConverters
    import androidx.sqlite.db.SupportSQLiteDatabase
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.util.OptionsConverter
    import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionDao
    import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData

@Database(entities = [QuestionData::class], version = 1)
@TypeConverters(OptionsConverter::class)
    abstract class IntellectIslandRoomDatabase : RoomDatabase() {
        abstract fun questionDao(): QuestionDao

        companion object {
            private var instance: IntellectIslandRoomDatabase? = null
            private val coroutineScope = CoroutineScope(Dispatchers.IO)

            @Synchronized
            fun getDatabase(context: Context): IntellectIslandRoomDatabase? {
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            IntellectIslandRoomDatabase::class.java,
                            "question_database"
                        )
                            .allowMainThreadQueries()
                            .addCallback(roomDatabaseCallback(context))
                            //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build()
                }
                return instance
            }

            private fun roomDatabaseCallback(context: Context): Callback {
                return object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        coroutineScope.launch {
                            populateDatabase(getDatabase(context)!!)
                        }
                    }
                }
            }

            private suspend fun populateDatabase(instance: IntellectIslandRoomDatabase) {
                val dao = instance.questionDao()

                // Example questions to populate the database
                val question1 = QuestionData(
                    id = 0,
                    questionText = "What is the capital of France?",
                    options = listOf("Berlin", "Madrid", "Paris", "Rome"),
                    correctAnswerIndex = 2
                )

                val question2 = QuestionData(
                    id = 0,
                    questionText = "What is 2 + 2?",
                    options = listOf("3", "4", "5", "6"),
                    correctAnswerIndex = 1
                )
}}}
