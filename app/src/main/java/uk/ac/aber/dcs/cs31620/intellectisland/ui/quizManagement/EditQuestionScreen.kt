package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import uk.ac.aber.dcs.cs31620.intellectisland.viewmodel.QuestionViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.datasource.model.QuestionData
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.EditableQuestionItem

/**
 * EditQuestionScreen
 * Allows users to edit and remove the details of each individual question in the question bank.
 * They can change which is the correct answer.
 * They can they save there changes or go back.
 */
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        Spacer(modifier = Modifier.height(20.dp))

        // Display and Edit Question
        question?.let { currentQuestion ->
            updatedQuestion = currentQuestion // Sets the state for editing
            EditableQuestionItem(
                navController = navController,
                question = currentQuestion,
                onQuestionChange = { newQuestion ->
                    updatedQuestion = newQuestion // Updates the state with changes
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
                        navController.popBackStack() // Navigate back
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
