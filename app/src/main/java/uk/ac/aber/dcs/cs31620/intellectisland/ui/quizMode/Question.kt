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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.QuestionProgressBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight

@Composable
fun Question(
    navController: NavHostController,
    viewModel: QuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val questionList by viewModel.allQuestions.observeAsState(emptyList())
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    val currentQuestion = questionList.getOrNull(currentQuestionIndex)

    var selectedAnswerIndex by rememberSaveable { mutableStateOf(-1) } // -1 means no answer selected

    MainTopNavigationBar(navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuestionProgressBar(
            currentQuestionIndex = currentQuestionIndex,
            totalQuestions = questionList.size
        )
        Spacer(modifier = Modifier.height(32.dp))
        currentQuestion?.let { question ->
            Text(
                text = question.questionText,
                fontSize = 40.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(32.dp))

            val options = question.options
            options.forEachIndexed { index, option ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Circle with A, B, C, ...
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ('A' + index).toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        // Option text
                        Text(
                            text = option,
                            modifier = Modifier.weight(1f),
                            fontSize = 16.sp
                        )

                        // Radio button
                        RadioButton(
                            selected = selectedAnswerIndex == index,
                            onClick = {
                                selectedAnswerIndex = index
                                viewModel.saveUserAnswer(question.id, index)
                                Log.d("QuestionScreen", "Selected Option: $option at index $index")
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Navigation buttons
            if (currentQuestionIndex > 0) {
                Button(
                    onClick = { currentQuestionIndex-- },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Previous", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (currentQuestionIndex < questionList.size - 1) {
                Button(
                    onClick = {
                        if (selectedAnswerIndex == -1) {
                            viewModel.saveUserAnswer(question.id, -1)
                        } else {
                            viewModel.saveUserAnswer(question.id, selectedAnswerIndex)
                        }
                        currentQuestionIndex++
                    },
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
                ) {
                    Text(text = "Next", fontSize = 32.sp, color = Color.White, modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp, top = 10.dp, start = 150.dp))
                }
            } else {
                Button(
                    onClick = {
                        navController.navigate(Screen.Results.route)
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
