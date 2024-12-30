package uk.ac.aber.dcs.cs31620.intellectisland.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleQuestion(question: QuestionData)

    @Insert
    suspend fun insertMultipleQuestions(questionsList: List<QuestionData>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateQuestion(question: QuestionData)

    @Delete
    suspend fun deleteQuestion(question: QuestionData)

    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): LiveData<List<QuestionData>>

    @Query("SELECT * FROM questions WHERE id = :id")
    fun getQuestionById(id: Int): LiveData<QuestionData>

    @Query("""SELECT * FROM questions WHERE questionText LIKE :keyword""")
    fun searchQuestions(keyword: String): LiveData<List<QuestionData>>

    @Query("SELECT * FROM questions WHERE correctAnswerIndex = :answerIndex")
    fun getQuestionsByCorrectAnswerIndex(answerIndex: Int): LiveData<List<QuestionData>>
}
