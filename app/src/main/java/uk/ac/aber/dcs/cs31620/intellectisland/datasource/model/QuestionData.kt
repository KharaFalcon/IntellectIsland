package uk.ac.aber.dcs.cs31620.intellectisland.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Question Entity tables that holds all quiz data in the "questions" table in the room database.
 * This allows the app to store and get information such as questions for the quiz.
 */
@Entity(tableName = "questions")
data class QuestionData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    var selectedAnswerIndex: Int,
    val userName: String// The user's name
)
