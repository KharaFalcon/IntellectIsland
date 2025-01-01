package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import QuestionViewModel
import androidx.compose.foundation.layout.*
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
fun AddQuestions(navController: NavHostController) {
    // Get the ViewModel instance
    val questionViewModel: QuestionViewModel = viewModel()

    // State to hold the question text input
    var textFieldValue by remember { mutableStateOf("") }

    // Observe all questions from the database (LiveData)
    val allQuestions by questionViewModel.allQuestions.observeAsState(emptyList())

    // Handle adding the question asynchronously
    val coroutineScope = rememberCoroutineScope()

    // Composable UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopNavigationBar(navController)

        Text(
            text = "Add a question",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp, top = 50.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(100.dp))

        // Text field for entering the question
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Enter your question here") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 50.dp),
            singleLine = true
        )

        // Add question button
        OutlinedButton(
            onClick = {
                if (textFieldValue.isNotBlank()) {
                    // Create a new QuestionData object
                    val newQuestion = QuestionData(
                        questionText = textFieldValue,
                        options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),  // Add your options here
                        correctAnswerIndex = 0  // Make sure to update with the actual correct index if needed
                    )
                    // Insert the question into the database
                    coroutineScope.launch {
                        questionViewModel.insertSingleQuestion(newQuestion)
                    }

                    // Clear the text field
                    textFieldValue = ""
                }
                navController.navigate(route = Screen.AddAnswers.route)
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Add Question",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp, start = 16.dp, end = 16.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of questions
        Text(
            text = "Questions in the bank:",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp)
        )

        Column(modifier = Modifier.padding(top = 8.dp)) {
            // Observe the list of questions and display them
            allQuestions.forEach { question ->
                Text(
                    text = question.questionText,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
