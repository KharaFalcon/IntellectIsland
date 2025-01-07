package uk.ac.aber.dcs.cs31620.intellectisland.datasource

import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.intellectisland.datasource.model.QuestionData

/**
 * Repository
 * Managing all quiz operations mainly relating to questions
 */
class QuestionRepository(private val questionDao: QuestionDao) {

    // Retrieves all questions as Livedata list
    fun getAllQuestions(): LiveData<List<QuestionData>> = questionDao.getAllQuestions()

/*
    suspend fun insertSingleQuestion(question: QuestionData) {
        questionDao.insertSingleQuestion(question)
    }
*/
    // Updates an existing question in the db
    suspend fun updateQuestion(updatedQuestion: QuestionData) {
        questionDao.updateQuestion(updatedQuestion)
    }

    //Deletes a question from the db
    suspend fun deleteQuestion(question: QuestionData) {
        questionDao.deleteQuestion(question)
    }

    // Fetch question as LiveData
    fun getQuestionById(questionId: Int): LiveData<QuestionData?> {
        return questionDao.getQuestionById(questionId)
    }
/*
    suspend fun saveUserAnswer(questionId: Int, selectedAnswerIndex: Int) {
        questionDao.updateSelectedAnswer(questionId, selectedAnswerIndex)
    }*/
    // Gets all data synchronously
    suspend fun getAllQuestionsSync(): List<QuestionData> {
        return questionDao.getAllQuestionsSync()
    }

    //Further Development
    //Deletes all questions associated with user
    fun getUserAnswersByName(userName: String): LiveData<List<QuestionData>> =
        questionDao.getUserAnswersByName(userName)

    //Deletes all questions for that user
    suspend fun clearUserAnswersByName(userName: String) {
        questionDao.clearUserAnswersByName(userName)
    }
    //Updates the username
    suspend fun updateQuestionUserName(questionId: Int, userName: String) {
        questionDao.updateQuestionUserName(questionId, userName)
    }

}
