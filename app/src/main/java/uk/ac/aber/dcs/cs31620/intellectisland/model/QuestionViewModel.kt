// QuestionViewModel.kt
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.IntellectIslandRoomDatabase
import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.QuestionDao
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
import uk.ac.aber.dcs.cs31620.intellectisland.model.UserAnswer
class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private val questionDao: QuestionDao = IntellectIslandRoomDatabase.getDatabase(application)?.questionDao()
        ?: throw IllegalStateException("Database is not initialized")

    val allQuestions: LiveData<List<QuestionData>> = questionDao.getAllQuestions()


    private val _userAnswers = mutableStateOf<List<UserAnswer>>(emptyList())
    val userAnswers: State<List<UserAnswer>> get() = _userAnswers

    fun insertSingleQuestion(question: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.insertSingleQuestion(question)
        }
    }

    fun saveUserAnswer(questionId: Int, selectedAnswerIndex: Int) {
        val userAnswer = UserAnswer(questionId, selectedAnswerIndex)
        _userAnswers.value = _userAnswers.value.toMutableList().apply {
            removeAll { it.questionId == questionId }
            add(userAnswer)
        }
    }

    fun calculateScore(questions: List<QuestionData>, userAnswers: List<UserAnswer>): Int {
        return questions.count { question ->
            userAnswers.find { it.questionId == question.id }?.selectedAnswerIndex == question.correctAnswerIndex
        }
    }

    fun getQuestionById(questionId: Int): LiveData<QuestionData> {
        return questionDao.getQuestionById(questionId)
    }

    fun updateQuestion(updatedQuestion: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.updateQuestion(updatedQuestion)
        }
    }

    suspend fun insertQuestion(question: QuestionData): Long {
        return questionDao.insertQuestion(question)
    }

    suspend fun insertOptions(options: List<String>) {
        // Add logic for handling options if needed.
    }
}