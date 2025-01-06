
package uk.ac.aber.dcs.cs31620.intellectisland.datasource1

import androidx.lifecycle.LiveData
import androidx.room.*
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
@Dao
interface QuestionDao {

    @Insert
    suspend fun insertSingleQuestion(question: QuestionData)

    @Insert
    suspend fun insertMultipleQuestions(questions: List<QuestionData>)

    @Insert
    suspend fun insertQuestion(question: QuestionData): Long

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): LiveData<List<QuestionData>>

    // Fetch question as LiveData
    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun getQuestionById(questionId: Int): LiveData<QuestionData?>

    // Synchronously fetch question
    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun getQuestionByIdSync(questionId: Int): QuestionData?
    @Update
    suspend fun updateQuestion(question: QuestionData)

    @Delete
    suspend fun deleteQuestion(question: QuestionData)

    @Query("DELETE FROM questions WHERE id = :id")
    suspend fun deleteQuestionById(id: Int)


    @Query("SELECT * FROM questions WHERE userName = :userName")
    fun getUserAnswersByName(userName: String): LiveData<List<QuestionData>>

    @Query("DELETE FROM questions WHERE userName = :userName")
    suspend fun clearUserAnswersByName(userName: String)

    @Query("UPDATE questions SET selectedAnswerIndex = :selectedAnswerIndex WHERE id = :questionId")
    suspend fun updateSelectedAnswer(questionId: Int, selectedAnswerIndex: Int)
    @Query("SELECT * FROM questions")
    suspend fun getAllQuestionsSync(): List<QuestionData>

}
