package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement
import QuestionViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData

@Composable
fun EditQuestionScreen(
    navController: NavHostController,
    questionId: Int,
    questionViewModel: QuestionViewModel = viewModel()
) {
    // Observe the selected question based on its ID
    val question by questionViewModel.getQuestionById(questionId).observeAsState()

    // Track modified values
    var questionText by remember { mutableStateOf(question?.questionText ?: "") }
    var options by remember { mutableStateOf(question?.options ?: listOf("", "", "", "")) }

    // Track changes to each option
    val optionFields = remember { mutableStateListOf(*options.toTypedArray()) }

    // Handle update of question and options
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Edit Question",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Edit Question Text
        OutlinedTextField(
            value = questionText,
            onValueChange = { questionText = it },
            label = { Text("Question Text") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Edit Options
        optionFields.forEachIndexed { index, option ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = option,
                    onValueChange = { newOption -> optionFields[index] = newOption },
                    label = { Text("Option ${index + 1}") },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        // Edit Option logic
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Option"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button
        Button(
            onClick = {
                val updatedQuestion = QuestionData(
                    id = questionId,
                    questionText = questionText,
                    options = optionFields.toList(),
                    correctAnswerIndex = question?.correctAnswerIndex ?: 0
                )
                questionViewModel.updateQuestion(updatedQuestion)
                navController.popBackStack()  // Go back to previous screen after saving
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save Changes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cancel Button
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cancel")
        }
    }
}
