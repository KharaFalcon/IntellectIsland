import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.IntellectIslandRoomDatabase
import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.QuestionDao
import uk.ac.aber.dcs.cs31620.intellectisland.datasource1.util.QuestionRepository
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private val questionDao: QuestionDao = IntellectIslandRoomDatabase.getDatabase(application)?.questionDao()
        ?: throw IllegalStateException("Database is not initialized")

    private val questionRepository: QuestionRepository = QuestionRepository(questionDao)

    // LiveData for all questions in the database
    val allQuestions: LiveData<List<QuestionData>> = questionRepository.getAllQuestions()


    // Update a question in the database
    fun updateQuestion(question: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionRepository.updateQuestion(question)
        }
    }

    // Retrieve a single question by ID
    fun getQuestionById(questionId: Int): LiveData<QuestionData?> {
        return questionRepository.getQuestionById(questionId)
    }

    // Delete a question from the database
    fun deleteQuestion(question: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionRepository.deleteQuestion(question)
        }
    }
    suspend fun saveUserAnswer(questionId: Int, selectedAnswerIndex: Int) {
        questionDao.updateSelectedAnswer(questionId, selectedAnswerIndex)
        Log.d("QuestionRepository", "Saved Answer: Question ID: $questionId, Selected Index: $selectedAnswerIndex")
    }
    fun saveAnswer(questionId: Int, selectedAnswerIndex: Int) {
        viewModelScope.launch {
            saveUserAnswer(questionId, selectedAnswerIndex)
        }
    }


    fun calculateScore(): LiveData<Int> {
        val score = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            val questions = questionRepository.getAllQuestionsSync()
            val totalScore = questions.count { it.correctAnswerIndex == it.selectedAnswerIndex }
            Log.d("Score Calculation", "Questions: $questions, Score: $totalScore")
            score.postValue(totalScore)
        }
        return score
    }



    // Insert a single question into the database
    fun insertSingleQuestion(question: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.insertSingleQuestion(question)
        }
    }}