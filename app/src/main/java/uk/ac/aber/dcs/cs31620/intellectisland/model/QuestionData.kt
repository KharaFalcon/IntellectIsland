package uk.ac.aber.dcs.cs31620.intellectisland.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionData(
    @PrimaryKey(autoGenerate = true)  val id: Int = 0,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    var selectedAnswerIndex: Int,
    val userName: String // The user's name
)
