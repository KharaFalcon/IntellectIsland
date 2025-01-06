package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import QuestionViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.SegmentationButton
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.surfaceLight

@Composable
fun AddQuestions(navController: NavHostController) {
    val questionViewModel: QuestionViewModel = viewModel()
    var questionText by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(MutableList(4) { "" }) }
    var selectedCorrectAnswer by remember { mutableStateOf(0) }
    val allQuestions by questionViewModel.allQuestions.observeAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        snackbarHostState = snackbarHostState,
        pageContent = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                // Segmentation Button
                SegmentationButton(
                    modifier = Modifier,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    label = { Text("Enter your question here") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true
                )

                // Options input
                options.forEachIndexed { index, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = index == selectedCorrectAnswer,
                            onClick = { selectedCorrectAnswer = index }
                        )
                        OutlinedTextField(
                            value = option,
                            onValueChange = { newOption ->
                                options = options.toMutableList().apply { this[index] = newOption }
                            },
                            label = { Text("Option ${index + 1}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            singleLine = true
                        )
                    }
                }

                OutlinedButton(
                    onClick = {
                        if (questionText.isBlank() || options.any { it.isBlank() }) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Please fill out all fields.")
                            }
                            return@OutlinedButton
                        }

                        coroutineScope.launch {
                            try {
                                // Call the correct method to insert the question
                                questionViewModel.insertSingleQuestion(
                                    QuestionData(
                                        questionText = questionText,
                                        options = options,
                                        correctAnswerIndex = selectedCorrectAnswer,
                                        selectedAnswerIndex = -1, // Placeholder if you don't need this field for now
                                        userName = "" // You can add a user name if needed
                                    )

                                )
                                snackbarHostState.showSnackbar("Question added successfully!")

                                // Clear input fields after success
                                questionText = ""
                                options = MutableList(4) { "" }
                                selectedCorrectAnswer = 0
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Failed to add question: ${e.message}")
                            }
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Add Question", fontSize = 18.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Questions in the bank:",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Display all questions in the bank
                allQuestions.forEach { question ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = question.questionText, fontSize = 16.sp, color = Color.Black)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Options: ${question.options.joinToString(", ")}",
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Correct Answer: ${question.options[question.correctAnswerIndex]}",
                                fontSize = 14.sp,
                                color = Color.Green
                            )
                        }
                    }
                }
            }
        }
    )
}
