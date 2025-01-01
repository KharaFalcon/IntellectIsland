package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement
import QuestionViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight

@Composable
fun EditQuestions(navController: NavHostController, questionViewModel: QuestionViewModel = viewModel()) {
    // Observe questions from the ViewModel
    val questions by questionViewModel.allQuestions.observeAsState(emptyList())

    MainTopNavigationBar(navController)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Edit Questions",
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 110.dp)
                .align(Alignment.CenterHorizontally)
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(50.dp))
        QuestionsList(
            questions = questions,
            onEditButtonClick = { question ->
                // Navigate to the edit screen and pass the selected question data
                navController.navigate("editQuestionScreen/${question.id}")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle Next Button Click */ },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Next",
                modifier = Modifier
                    .padding(horizontal = 80.dp)
            )
        }
    }
}

@Composable
fun QuestionsList(
    questions: List<QuestionData>,
    onEditButtonClick: (QuestionData) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        questions.forEach { question ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = question.questionText,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onEditButtonClick(question) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Question"
                    )
                }
            }
        }
    }
}

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
