package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.inversePrimaryLightMediumContrast
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.viewmodel.QuestionViewModel

@Composable
fun Results(
    navController: NavHostController,
    viewModel: QuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val score by viewModel.calculateScore().observeAsState(0)
    val allQuestions by viewModel.allQuestions.observeAsState(emptyList())
    val totalQuestions = allQuestions.size
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        snackbarHostState = snackbarHostState,
        pageContent = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Questions & Answers",
                    fontSize = 28.sp,
                    color = Color.Black
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total: $score / $totalQuestions questions",
                    fontSize = 24.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Display questions with correctness
                allQuestions.forEachIndexed { index, question ->
                    val isCorrect = question.correctAnswerIndex == question.selectedAnswerIndex
                    val resultIcon = if (isCorrect) "✔" else "✘"

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .background(
                                inversePrimaryLightMediumContrast,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                1.dp,
                                Color.Gray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Display Question Number
                            Text(
                                text = "${index + 1}.",
                                fontSize = 18.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = question.questionText,
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                // Safely display user's answer
                                val userAnswerText = when {
                                    question.selectedAnswerIndex == -1 -> "Skipped"
                                    question.selectedAnswerIndex in question.options.indices ->
                                        question.options[question.selectedAnswerIndex]
                                    else -> "Invalid Answer"
                                }

                                Text(
                                    text = "Your Answer: $userAnswerText",
                                    fontSize = 16.sp,
                                    color = if (isCorrect) Color.Green else Color.Red
                                )
                                Text(
                                    text = "Correct Answer: ${question.options[question.correctAnswerIndex]}",
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }

                            // Show Correctness Icon
                            Text(
                                text = resultIcon,
                                fontSize = 24.sp,
                                color = if (isCorrect) Color.Green else Color.Red,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(Screen.HomeScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight)
                ) {
                    Text(
                        text = "Return Home",
                        color = Color.White,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
    )
}

