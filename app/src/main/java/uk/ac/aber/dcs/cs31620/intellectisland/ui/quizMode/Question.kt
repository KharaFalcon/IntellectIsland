package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode

import QuestionViewModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.QuestionProgressBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.inverseOnSurfaceLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.inversePrimaryLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onSecondaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.secondaryContainerLight
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
            .verticalScroll(rememberScrollState())
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
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp),
                lineHeight = 48.sp // Added line height for better spacing when text wraps
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Blue box wrapping the options
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = secondaryContainerLight, // Light blue color
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp) // Inner padding for content
            ) {
                Column {
                    val options = question.options
                    options.forEachIndexed { index, option ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = inverseOnSurfaceLight, // Light blue color
                                    )
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Circle with A, B, C, ...
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
                                        text = ('A' + index).toString(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = onSecondaryContainerLight,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                // Option text
                                Text(
                                    text = option,
                                    modifier = Modifier.weight(1f),
                                    fontSize = 16.sp
                                )

                                RadioButton(
                                    selected = selectedAnswerIndex == index,
                                    onClick = {
                                        selectedAnswerIndex = index
                                        viewModel.saveAnswer(question.id, index) // Save the selected answer
                                    }
                                )



                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Navigation buttons
            if (currentQuestionIndex < questionList.size) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween // Ensures buttons are spaced apart
                ) {
                    // Show "Previous" button only if not on the first question
                    if (currentQuestionIndex > 0) {
                        Button(
                            onClick = {
                                currentQuestionIndex--
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp), // Add spacing between buttons
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight)
                        ) {
                            Text(
                                text = "Previous",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                    Button(
                        onClick = {
                            if (selectedAnswerIndex == -1) {
                                viewModel.saveAnswer(currentQuestion!!.id, -1)
                            } else {
                                viewModel.saveAnswer(currentQuestion!!.id, selectedAnswerIndex)
                            }
                            if (currentQuestionIndex < questionList.size - 1) {
                                currentQuestionIndex++
                            } else {
                                navController.navigate(Screen.Results.route) // Navigate to the Results screen
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = if (currentQuestionIndex > 0) 8.dp else 0.dp), // Adjust padding when "Previous" is absent
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight)
                    ) {
                        Text(
                            text = if (currentQuestionIndex < questionList.size - 1) "Next" else "Submit",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }}}}}}
