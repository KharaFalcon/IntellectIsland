package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import QuestionViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.EditableQuestionItem
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.SegmentationButton

@Composable
fun EditQuestionScreen(
    questionId: Int,
    navController: NavHostController,
    questionViewModel: QuestionViewModel = viewModel()
) {
    val question by questionViewModel.getQuestionById(questionId).observeAsState()
    val coroutineScope = rememberCoroutineScope()
    var updatedQuestion by remember { mutableStateOf<QuestionData?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Navigation Segmentation Button
        SegmentationButton(
            modifier = Modifier,
            navController = navController
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Display and Edit Question
        question?.let { currentQuestion ->
            updatedQuestion = currentQuestion // Set the initial state for editing
            EditableQuestionItem(
                navController = navController,
                question = currentQuestion,
                onQuestionChange = { newQuestion ->
                    updatedQuestion = newQuestion // Update state with changes
                }
            )
        } ?: run {
            // Loading State
            Text(
                text = "Loading question...",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        // Save Changes Button
        Button(
            onClick = {
                updatedQuestion?.let { questionToSave ->
                    coroutineScope.launch {
                        questionViewModel.updateQuestion(questionToSave)
                        navController.popBackStack() // Navigate back after saving
                    }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Save Changes",
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )
        }
    }
}
