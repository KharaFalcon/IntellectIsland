package uk.ac.aber.dcs.cs31620.intellectisland.datasource1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData

@Dao
interface QuestionDao {
    @Insert
    suspend fun insertSingleQuestion(question: QuestionData)

    @Insert
    suspend fun insertMultipleQuestions(questions: List<QuestionData>)

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): LiveData<List<QuestionData>>

    @Query("SELECT * FROM questions WHERE id = :id")
    fun getQuestionById(id: Int): LiveData<QuestionData>

    @Update
    suspend fun updateQuestion(question: QuestionData)
}
