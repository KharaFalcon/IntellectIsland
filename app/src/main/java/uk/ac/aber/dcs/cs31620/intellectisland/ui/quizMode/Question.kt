package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode

import android.util.Log
import uk.ac.aber.dcs.cs31620.intellectisland.viewmodel.QuestionViewModel
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
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.QuestionProgressBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.inverseOnSurfaceLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onSecondaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.secondaryContainerLight
@Composable
fun Question(
    navController: NavHostController,
    viewModel: QuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // Observe questions
    val originalQuestionList by viewModel.allQuestions.observeAsState(emptyList())
    val shuffledQuestionList = remember(originalQuestionList) {
        if (originalQuestionList.isNotEmpty()) originalQuestionList.shuffled() else emptyList()
    }

    // Log to debug issues
    if (originalQuestionList.isEmpty()) {
        Log.e("Question", "originalQuestionList is empty")
    } else {
        Log.d("Question", "originalQuestionList size: ${originalQuestionList.size}")
    }

    if (shuffledQuestionList.isEmpty()) {
        Log.e("Question", "shuffledQuestionList is empty")
    }

    // State variables for progress
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    val currentQuestion = shuffledQuestionList.getOrNull(currentQuestionIndex)
    var selectedAnswerIndex by rememberSaveable { mutableStateOf(-1) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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
            totalQuestions = shuffledQuestionList.size
        )
        Spacer(modifier = Modifier.height(32.dp))

        currentQuestion?.let { question ->
            Text(
                text = question.questionText,
                fontSize = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp),
                lineHeight = 48.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = secondaryContainerLight,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
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
                                    .background(inverseOnSurfaceLight)
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
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

                                Text(
                                    text = option,
                                    modifier = Modifier.weight(1f),
                                    fontSize = 16.sp
                                )

                                RadioButton(
                                    selected = selectedAnswerIndex == index,
                                    onClick = {
                                        selectedAnswerIndex = index
                                        viewModel.saveAnswer(question.id, index)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Navigation Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentQuestionIndex > 0) {
                    Button(
                        onClick = {
                            currentQuestionIndex--
                            selectedAnswerIndex = -1 // Reset selection
                        },
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight)
                    ) {
                        Text("Previous", fontSize = 16.sp, color = Color.White)
                    }
                }

                Button(
                    onClick = {
                        if (currentQuestionIndex < shuffledQuestionList.size - 1) {
                            currentQuestionIndex++
                            selectedAnswerIndex = -1
                        } else {
                            navController.navigate(Screen.Results.route)
                        }
                    },
                    modifier = Modifier.weight(1f).padding(start = if (currentQuestionIndex > 0) 8.dp else 0.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight)
                ) {
                    Text(
                        text = if (currentQuestionIndex < shuffledQuestionList.size - 1) "Next" else "Submit",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        } ?: run {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("No question found. Please try again.")
            }
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}
