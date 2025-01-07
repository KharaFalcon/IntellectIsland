package uk.ac.aber.dcs.cs31620.intellectisland

import UserName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.HomeScreen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.UserProfile
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.AddQuestions
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.EditAnswers
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.EditQuestionScreen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.RemoveQuestions
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode.Question
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode.Results
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode.StartQuiz
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.IntellectIslandTheme
import uk.ac.aber.dcs.cs31620.intellectisland.viewmodel.QuestionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            IntellectIslandTheme(dynamicColor = false) {
                BuildNavigationGraph()
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph(    questionViewModel : QuestionViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.UserName.route
    ) {
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.StartQuiz.route) { StartQuiz(navController) }
        composable(Screen.Question.route) { Question(navController) }
        composable(Screen.RemoveQuestions.route) { RemoveQuestions(navController) }
        composable(Screen.AddQuestions.route) { AddQuestions(navController) }
        //composable(Screen.EditQuestionScreen.route) { EditQuestionScreen(navController) }
        composable(Screen.EditAnswers.route) { EditAnswers(navController) }
        composable(Screen.Results.route) { Results(navController) }
        composable(Screen.UserName.route) { UserName(navController, questionViewModel) }
        composable("edit_question_screen/{questionId}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull() ?: return@composable
            EditQuestionScreen(questionId = questionId, navController = navController)
        }


        composable(Screen.UserProfile.route) { UserProfile(navController) }
    }}
