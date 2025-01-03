package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode

import QuestionViewModel
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.QuestionProgressBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
@Composable
fun Question(
    navController: NavHostController,
    viewModel: QuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val questionList by viewModel.allQuestions.observeAsState(emptyList())
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    val currentQuestion = questionList.getOrNull(currentQuestionIndex)

    // Track the selected answer index (nullable Int)
    var selectedAnswerIndex by rememberSaveable { mutableStateOf(-1) } // -1 means no answer selected

    // Debug log to track selectedAnswerIndex
    Log.d("QuestionScreen", "Selected Answer Index: $selectedAnswerIndex")

    MainTopNavigationBar(navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Display question progress
        QuestionProgressBar(
            currentQuestionIndex = currentQuestionIndex,
            totalQuestions = questionList.size
        )

        // Display the current question
        currentQuestion?.let { question ->
            Text(
                text = question.questionText,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            val options = question.options
            options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Check if the current option is selected based on the index
                    RadioButton(
                        selected = selectedAnswerIndex == index, // Compare index directly
                        onClick = {
                            selectedAnswerIndex = index
                            viewModel.saveUserAnswer(question.id, index)
                            Log.d(
                                "QuestionScreen",
                                "Selected Option: $option at index $index"
                            ) // Log selected answer
                        } // Set selected answer index
                    )
                    Text(text = option)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Show "Previous" button if not on the first question
            if (currentQuestionIndex > 0) {
                Button(
                    onClick = {
                        currentQuestionIndex--
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Previous", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Show "Next" button or "Results" button on the last question
            if (currentQuestionIndex < questionList.size - 1) {
                Button(
                    onClick = {
                        // Log the current selected answer when moving to next
                        Log.d("QuestionScreen", "Answer for Question ${question.id} saved with index: $selectedAnswerIndex")

                        // Save the user's answer (handle null for no answer)
                        if (selectedAnswerIndex == -1) {
                            // Treat as incorrect if no answer selected
                            viewModel.saveUserAnswer(question.id, -1) // Null for no answer
                        } else {
                            viewModel.saveUserAnswer(question.id, selectedAnswerIndex)
                        }

                        // Move to the next question
                        currentQuestionIndex++
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Next", fontSize = 16.sp)
                }
            } else {
                // Show Results button on the last question
                Button(
                    onClick = {
                        // Navigate to the results screen
                        navController.navigate(Screen.Results.route) // Use actual route for results
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Results", fontSize = 16.sp)
                }
            }
        }
    }
}
