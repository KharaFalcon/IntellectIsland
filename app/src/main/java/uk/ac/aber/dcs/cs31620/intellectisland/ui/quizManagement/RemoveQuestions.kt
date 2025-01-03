package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar

@Composable
fun RemoveQuestions(navController: NavHostController) {
    val questions = remember { mutableStateOf(listOf("Question 1?", "Question 2?", "Question 3?")) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MainTopNavigationBar(navController)

        Text(
            text = "Remove Questions",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 110.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        QuestionsList(questions.value) { index ->
            navController.navigate("removeQuestionScreen/$index")
        }
    }
}

@Composable
fun QuestionsList(questions: List<String>, onEdit: (Int) -> Unit) {
    Column {
        questions.forEachIndexed { index, question ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(question, style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { onEdit(index) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Question")
                }
            }
        }
    }
}

@Composable
fun RemoveQuestionScreen(navController: NavHostController, index: Int, questionText: String) {
    var editedText = remember { mutableStateOf(questionText) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Edit Question", style = MaterialTheme.typography.titleLarge)

        TextField(
            value = editedText.value,
            onValueChange = { editedText.value = it },
            label = { Text("Edit your question") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Save")
        }
    }
}
