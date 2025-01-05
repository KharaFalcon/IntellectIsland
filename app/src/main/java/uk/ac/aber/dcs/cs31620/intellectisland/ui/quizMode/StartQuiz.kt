package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode

import QuestionViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.CircularProgressIndicator
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.secondaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.tertiaryContainerLight
@Composable
fun StartQuiz(navController: NavHostController,viewModel: QuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // Initialize coroutineScope and snackbarHostState
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    // MainTopNavigationBar
    MainTopNavigationBar(navController)

    // Scaffold for the entire page structure
    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        snackbarHostState = snackbarHostState,
        pageContent = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Quiz Title
                Text(
                    text = "Quiz Name",
                    fontSize = 40.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(40.dp)
                )
                val questionList by viewModel.allQuestions.observeAsState(emptyList())
                // Circular Progress Indicator
                CircularProgressIndicator(
                    progress = 1f, // 50% progress
                    question = "Questions",
                    largeNumber = questionList.size,
                    circleColor = tertiaryContainerLight,
                    trackColor = secondaryContainerLight,
                    circleSize = 200f,
                    strokeWidth = 12f
                )

                Spacer(modifier = Modifier.height(60.dp))

                // Start Quiz Button
                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.Question.route)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
                ) {
                    Text(
                        text = "Start Quiz",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.padding(
                            bottom = 16.dp,
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    )
                }
            }
        }
    )
}
