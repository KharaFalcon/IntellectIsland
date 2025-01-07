package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import androidx.compose.foundation.background
import uk.ac.aber.dcs.cs31620.intellectisland.viewmodel.QuestionViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.datasource.model.QuestionData
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.SegmentationButton
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.inverseOnSurfaceLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onSecondaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.secondaryContainerLight

/**
 * AddQuestionsScreen
 * Allows user to add and manage questions for the quiz
 */
@Composable
fun AddQuestions(navController: NavHostController) {
    val questionViewModel: QuestionViewModel = viewModel()
    var questionText by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(MutableList(1) { "" }) }
    var selectedCorrectAnswer by remember { mutableStateOf(-1) } // Start with -1 for no selection
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
                // Segmentation Button for managment navigation
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

                // Options input allows it to be dynamic
                options.forEachIndexed { index, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = option,
                            onValueChange = { newOption ->
                                options = options.toMutableList().apply { this[index] = newOption }
                            },
                            label = { Text("Option ${index + 1}") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp),
                            singleLine = true
                        )
                        //removes options button if more than one exists
                        if (options.size > 1) {
                            IconButton(onClick = {
                                options = options.toMutableList().apply { removeAt(index) }
                                if (selectedCorrectAnswer == index) {
                                    selectedCorrectAnswer = -1 // Resets the correct answer if removed
                                } else if (selectedCorrectAnswer > index) {
                                    selectedCorrectAnswer--
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove option")
                            }
                        }
                    }
                }
                // add new option button
                if (options.size < 10) {
                    Button(
                        onClick = {
                            options = options.toMutableList().apply { add("") }
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Add Option")
                    }
                } else {
                    Text(
                        text = "You can only add up to 10 options.",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                OutlinedButton(
                    onClick = {
                        if (questionText.isBlank()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Please enter a question.")
                            }
                            return@OutlinedButton
                        }
                        if (options.any { it.isBlank() }) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Please fill out all options.")
                            }
                            return@OutlinedButton
                        }
                        if (selectedCorrectAnswer < 0 || selectedCorrectAnswer >= options.size) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Please select a correct answer.")
                            }
                            return@OutlinedButton
                        }
                        //adds question to db
                        coroutineScope.launch {
                            try {
                                questionViewModel.insertSingleQuestion(
                                    QuestionData(
                                        questionText = questionText,
                                        options = options,
                                        correctAnswerIndex = selectedCorrectAnswer,
                                        selectedAnswerIndex = -1,
                                        userName = ""
                                    )
                                )
                                snackbarHostState.showSnackbar("Question added successfully!")
                                questionText = ""
                                options = MutableList(1) { "" }
                                selectedCorrectAnswer = -1
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
                //displays list of questions in the question bank
                Text(
                    text = "Questions in the bank:",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Display numbered questions in the bank with circles
                allQuestions.forEachIndexed { index, question ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = inverseOnSurfaceLight)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Circle for the question number
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = secondaryContainerLight,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (index + 1).toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = onSecondaryContainerLight,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            // Question details
                            Column {
                                Text(
                                    text = question.questionText,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
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
        }
    )
}
