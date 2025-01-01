import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.IntellectIslandRoomDatabase
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionDao

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private val questionDao: QuestionDao
    val allQuestions: LiveData<List<QuestionData>>  // Observed by the UI for all questions
    var searchedQuestions: LiveData<List<QuestionData>> // Observed by the UI for search results

    init {
        // Get the DAO instance from the Room Database
        val database = IntellectIslandRoomDatabase.getDatabase(application)
        questionDao = database?.questionDao()!!

        // Initialize the LiveData for all questions
        allQuestions = questionDao.getAllQuestions()

        // Initialize searchedQuestions as an empty list initially
        searchedQuestions = questionDao.getAllQuestions()

        // Populate the database with hardcoded data only if the database is empty
        checkAndHardcodeQuestions()
    }

    // Check if the database is empty, and then hardcode data
    private fun checkAndHardcodeQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            if (questionDao.getAllQuestions().value.isNullOrEmpty()) {
                val hardcodedQuestions = listOf(
                    QuestionData(
                        questionText = "What is the capital of France?",
                        options = listOf("Berlin", "Madrid", "Paris", "Rome"),
                        correctAnswerIndex = 2
                    ),
                    QuestionData(
                        questionText = "What is 2 + 2?",
                        options = listOf("3", "4", "5", "6"),
                        correctAnswerIndex = 1
                    ),
                    QuestionData(
                        questionText = "Which planet is closest to the Sun?",
                        options = listOf("Earth", "Venus", "Mercury", "Mars"),
                        correctAnswerIndex = 2
                    )
                )
                questionDao.insertMultipleQuestions(hardcodedQuestions)
            }
        }
    }

    // Insert a single question into the database
    fun insertSingleQuestion(question: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.insertSingleQuestion(question)
        }
    }

    // Insert multiple questions into the database
    fun insertMultipleQuestions(questionsList: List<QuestionData>) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.insertMultipleQuestions(questionsList)
        }
    }

    // Update an existing question in the database
    fun updateQuestion(question: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.updateQuestion(question)
        }
    }

    // Delete a single question from the database
    fun deleteQuestion(question: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.deleteQuestion(question)
        }
    }

    // Delete all questions from the database
    fun deleteAllQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.deleteAllQuestions()
        }
    }

    // Get a single question by ID
    fun getQuestionById(id: Int): LiveData<QuestionData> {
        return questionDao.getQuestionById(id)
    }

    // Search questions by a keyword
    fun searchQuestions(keyword: String) {
        searchedQuestions = questionDao.searchQuestions("%$keyword%")
    }

    // Get questions by the correct answer index
    fun getQuestionsByCorrectAnswerIndex(answerIndex: Int): LiveData<List<QuestionData>> {
        return questionDao.getQuestionsByCorrectAnswerIndex(answerIndex)
    }
}
