package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement
import QuestionViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.QuestionsList
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.SegmentationButton
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
@Composable
fun EditQuestions(navController: NavHostController, questionViewModel: QuestionViewModel = viewModel()) {
    val questions by questionViewModel.allQuestions.observeAsState(emptyList())
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                // Segmentation Button
                SegmentationButton(
                    modifier = Modifier,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Edit Questions",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp))

                if (questions.isNotEmpty()) {
                    questions.forEach { question ->
                        QuestionListItem(
                            question = question,
                            onClick = { navController.navigate("editQuestionScreen/${question.id}") }
                        )
                    }
                } else {
                    Text(
                        text = "No questions available.",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("nextScreen") },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Next", modifier = Modifier.padding(horizontal = 80.dp))
                }
            }
        }
    )
}

@Composable
fun QuestionListItem(question: QuestionData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Question")
            }
        }
    }
}
