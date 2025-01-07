package uk.ac.aber.dcs.cs31620.intellectisland.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import uk.ac.aber.dcs.cs31620.intellectisland.datasource.model.QuestionData

/**
 * Data Access Object(DAO)
 * Managing database operations on questions table in the room database.
 */
@Dao
interface QuestionDao {

    //Inserts a single question
    @Insert
    suspend fun insertSingleQuestion(question: QuestionData)

    //Inserts multiple questions
    @Insert
    suspend fun insertMultipleQuestions(questions: List<QuestionData>)

    //Inserts the question and automates ID in db
    @Insert
    suspend fun insertQuestion(question: QuestionData): Long

    //Retrieves all questions stored as LiveData
    @Query("SELECT * FROM questions")
    fun getAllQuestions(): LiveData<List<QuestionData>>

    // Fetches question as LiveData
    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun getQuestionById(questionId: Int): LiveData<QuestionData?>

    // Synchronously fetches a question using question ID
    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun getQuestionByIdSync(questionId: Int): QuestionData?

    // Updates question that already exists in the db
    @Update
    suspend fun updateQuestion(question: QuestionData)

    //Deletes one specific question in db
    @Delete
    suspend fun deleteQuestion(question: QuestionData)

    //Delete a question using the id in the db
    @Query("DELETE FROM questions WHERE id = :id")
    suspend fun deleteQuestionById(id: Int)

    //Updates the selected answer index for a specific question when a user clicks a question option
    @Query("UPDATE questions SET selectedAnswerIndex = :selectedAnswerIndex WHERE id = :questionId")
    suspend fun updateSelectedAnswer(questionId: Int, selectedAnswerIndex: Int)

    //Retrieves all questions synchronously
    @Query("SELECT * FROM questions")
    suspend fun getAllQuestionsSync(): List<QuestionData>

    //FOR FURTHER DEVELOPMENT
    //Gets all questions for a specific user as LiveData
    @Query("SELECT * FROM questions WHERE userName = :userName")
    fun getUserAnswersByName(userName: String): LiveData<List<QuestionData>>

    //Deletes all questions based on user
    @Query("DELETE FROM questions WHERE userName = :userName")
    suspend fun clearUserAnswersByName(userName: String)

    //Updates the username
    @Query("UPDATE questions SET userName = :userName WHERE id = :questionId")
    suspend fun updateQuestionUserName(questionId: Int, userName: String)

}
