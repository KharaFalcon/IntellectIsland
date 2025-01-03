import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
@Composable
fun Results(
    navController: NavHostController,
    viewModel: QuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val questions by viewModel.allQuestions.observeAsState(emptyList())
    val userAnswers = viewModel.userAnswers.value

    var score by remember { mutableStateOf(0) }

    // Recalculate score whenever questions or user answers change
    LaunchedEffect(questions, userAnswers) {
        if (questions.isNotEmpty() && userAnswers.isNotEmpty()) {
            score = viewModel.calculateScore(questions, userAnswers)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the score
        Text(
            text = "Your score is: $score / ${questions.size}",
            fontSize = 24.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Display each question, answer, and feedback
        questions.forEach { question ->
            val userAnswer = userAnswers.find { it.questionId == question.id }
            val isCorrect = userAnswer?.selectedAnswerIndex == question.correctAnswerIndex
            val userAnswerText = question.options.getOrNull(userAnswer?.selectedAnswerIndex ?: -1)
            val correctAnswerText = question.options[question.correctAnswerIndex]

            Text(
                text = question.questionText,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // User's answer feedback
            Text(
                text = "Your answer: ${userAnswerText ?: "No answer"}",
                fontSize = 16.sp,
                color = if (isCorrect) Color.Green else Color.Red
            )

            // Show the correct answer if the user got it wrong
            if (!isCorrect) {
                Text(
                    text = "Correct answer: $correctAnswerText",
                    fontSize = 16.sp,
                    color = Color.Blue
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Button to navigate back to home
        Button(
            onClick = { navController.navigate(Screen.HomeScreen.route) }
        ) {
            Text("Return Home")
        }
    }
}
