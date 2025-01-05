package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement
import QuestionViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    // Observe questions from the ViewModel
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit Questions",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Segmentation Button
                SegmentationButton(
                    modifier = Modifier,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(20.dp))
                QuestionsList(
                    questions = questions,
                    onEditButtonClick = { question ->
                        navController.navigate("editQuestionScreen/${question.id}")
                    }, "edit"
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle Next Button Click */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier
                            .padding(horizontal = 80.dp)
                    )
                }
            }
        }
    )
}
