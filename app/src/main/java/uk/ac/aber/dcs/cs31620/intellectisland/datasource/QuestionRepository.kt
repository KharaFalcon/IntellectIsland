package uk.ac.aber.dcs.cs31620.intellectisland.datasource


import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.intellectisland.datasource.model.QuestionData
class QuestionRepository(private val questionDao: QuestionDao) {

    fun getAllQuestions(): LiveData<List<QuestionData>> = questionDao.getAllQuestions()


    suspend fun insertSingleQuestion(question: QuestionData) {
        questionDao.insertSingleQuestion(question)
    }

    suspend fun updateQuestion(updatedQuestion: QuestionData) {
        questionDao.updateQuestion(updatedQuestion)
    }

    suspend fun deleteQuestion(question: QuestionData) {
        questionDao.deleteQuestion(question)
    }
    fun getUserAnswersByName(userName: String): LiveData<List<QuestionData>> =
        questionDao.getUserAnswersByName(userName)

    suspend fun clearUserAnswersByName(userName: String) {
        questionDao.clearUserAnswersByName(userName)
    }

    suspend fun updateQuestionUserName(questionId: Int, userName: String) {
        questionDao.updateQuestionUserName(questionId, userName)
    }


    // Fetch question as LiveData
    fun getQuestionById(questionId: Int): LiveData<QuestionData?> {
        return questionDao.getQuestionById(questionId)
    }

    suspend fun saveUserAnswer(questionId: Int, selectedAnswerIndex: Int) {
        questionDao.updateSelectedAnswer(questionId, selectedAnswerIndex)
    }
    suspend fun getAllQuestionsSync(): List<QuestionData> {
        return questionDao.getAllQuestionsSync()
    }





}
