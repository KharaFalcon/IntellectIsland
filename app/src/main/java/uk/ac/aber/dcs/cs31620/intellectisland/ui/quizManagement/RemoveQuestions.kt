package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RemoveQuestions(navController: NavHostController) {
    val questions = remember { mutableStateOf(listOf("Question 1?", "Question 2?", "Question 3?")) }

    MainTopNavigationBar()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Remove Questions",
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 110.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(50.dp))
        QuestionsList(
            questions = questions.value,
            onEditButtonClick = { index ->
                // Navigate to the edit screen and pass the selected question index or data
                navController.navigate("removeQuestionScreen/$index")
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
    questions: List<String>,
    onEditButtonClick: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        questions.forEachIndexed { index, question ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onEditButtonClick(index) }) {
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
fun RemoveQuestionScreen(navController: NavHostController, questionIndex: Int, questionText: String) {
    val editedText = remember { mutableStateOf(questionText) }

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
                // Save changes and navigate back
                // You could pass the edited question back to the parent screen via a shared ViewModel
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Save")
        }
    }
}
