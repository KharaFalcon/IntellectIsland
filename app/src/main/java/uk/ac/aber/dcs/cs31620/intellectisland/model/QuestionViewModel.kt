import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.IntellectIslandRoomDatabase
import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.QuestionDao
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
import uk.ac.aber.dcs.cs31620.intellectisland.model.UserAnswer
class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    // Use safe call and provide a fallback or throw exception if null
    private val questionDao: QuestionDao = IntellectIslandRoomDatabase.getDatabase(application)?.questionDao()
        ?: throw IllegalStateException("Database is not initialized")

    val allQuestions: LiveData<List<QuestionData>> = questionDao.getAllQuestions()

    // Shuffled questions
    val shuffledQuestions: LiveData<List<QuestionData>> = liveData(Dispatchers.IO) {
        emit(allQuestions.value?.shuffled() ?: emptyList())
    }

    // Save user answers in the ViewModel
    private val _userAnswers = mutableStateOf<List<UserAnswer>>(emptyList())
    val userAnswers: State<List<UserAnswer>> get() = _userAnswers

    // Insert a single question into the database
    fun insertSingleQuestion(question: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.insertSingleQuestion(question)
        }
    }

    // Save the user's answer
    fun saveUserAnswer(questionId: Int, selectedAnswerIndex: Int) {
        val userAnswer = UserAnswer(questionId, selectedAnswerIndex)
        _userAnswers.value = _userAnswers.value.toMutableList().apply {
            removeAll { it.questionId == questionId } // Remove any previous answer for this question
            add(userAnswer) // Add the new answer
        }
    }

    // Calculate score based on user's answers
    fun calculateScore(questions: List<QuestionData>, userAnswers: List<UserAnswer>): Int {
        return questions.count { question ->
            userAnswers.find { it.questionId == question.id }?.selectedAnswerIndex == question.correctAnswerIndex
        }
    }

    // Get a specific question by its ID
    fun getQuestionById(questionId: Int): LiveData<QuestionData> {
        return questionDao.getQuestionById(questionId)
    }

    // Update a question in the database
    fun updateQuestion(updatedQuestion: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.updateQuestion(updatedQuestion)
        }
    }
}
