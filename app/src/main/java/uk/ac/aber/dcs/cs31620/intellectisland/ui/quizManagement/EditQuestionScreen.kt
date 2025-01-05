package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement
import QuestionViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
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
    Spacer(modifier = Modifier.height(20.dp))
    // Segmentation Button
    SegmentationButton(
        modifier = Modifier,
        navController = navController
    )
    Spacer(modifier = Modifier.height(20.dp))
    question?.let {
        EditableQuestionItem(navController,
            question = it,
            onQuestionChange = { updatedQuestion ->
                questionViewModel.updateQuestion(updatedQuestion)
                navController.popBackStack() // Navigate back after saving
            }
        )
    } ?: run {
        Text(
            text = "Loading question...",
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
    }
}


