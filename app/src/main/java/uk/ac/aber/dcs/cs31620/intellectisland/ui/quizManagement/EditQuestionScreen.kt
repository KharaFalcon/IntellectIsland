package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement
import QuestionViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    // Fetch the question data by ID
    val question by questionViewModel.getQuestionById(questionId).observeAsState()

    question?.let { questionData ->
        val editedText = remember { mutableStateOf(questionData.questionText) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Edit Question",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = editedText.value,
                onValueChange = { editedText.value = it },
                label = { Text("Edit your question") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Save changes
                    val updatedQuestion = questionData.copy(questionText = editedText.value)
                    questionViewModel.updateQuestion(updatedQuestion)
                    navController.popBackStack()
                    // navController.navigate(route = Screen..route)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save")
            }
        }
    }
}
