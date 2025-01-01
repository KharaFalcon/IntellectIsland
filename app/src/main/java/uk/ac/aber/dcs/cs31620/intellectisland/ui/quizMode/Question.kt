
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.RadioButtonGroup
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
import androidx.compose.material3.Button
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.QuestionProgressBar

@Composable
fun Question(navController: NavHostController, viewModel: QuestionViewModel = viewModel()) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    // Observing the list of questions
    val questionList by viewModel.allQuestions.observeAsState(initial = emptyList())
    var currentQuestionIndex by remember { mutableStateOf(0) }
    val currentQuestion = questionList.getOrNull(currentQuestionIndex)

    MainTopNavigationBar(navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Progress Bar
        QuestionProgressBar(
            currentQuestionIndex = currentQuestionIndex,
            totalQuestions = questionList.size
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display the question
        currentQuestion?.let { question ->
            Text(
                text = question.questionText,
                color = Color.Black,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Display the options
            RadioButtonGroup(
                options = question.options,
                selectedOption = selectedAnswer,
                onOptionSelected = { selectedAnswer = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Next Button
            Button(
                onClick = {
                    if (currentQuestionIndex < questionList.size - 1) {
                        currentQuestionIndex++ // Move to the next question
                        selectedAnswer = null // Reset selected answer
                    } else {
                        // Handle end of quiz logic here
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = selectedAnswer != null,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (currentQuestionIndex < questionList.size - 1) "Next" else "Finish",
                    modifier = Modifier.padding(horizontal = 80.dp)
                )
            }
        }
    }
}
