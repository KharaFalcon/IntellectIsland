package uk.ac.aber.dcs.cs31620.intellectisland

import Results
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.HomeScreen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.AddQuestions
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.EditAnswers
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.EditQuestionScreen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.RemoveQuestionScreen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.RemoveQuestions
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode.Question
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode.StartQuiz
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.IntellectIslandTheme

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
private fun BuildNavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.StartQuiz.route) { StartQuiz(navController) }
        composable(Screen.Question.route) { Question(navController) }
        composable(Screen.RemoveQuestions.route) { RemoveQuestions(navController) }
        composable(Screen.AddQuestions.route) { AddQuestions(navController) }
        composable(
            route = "editQuestionScreen/{questionId}",
            arguments = listOf(navArgument("questionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val questionId = backStackEntry.arguments?.getInt("questionId") ?: -1
            EditQuestionScreen(navController = navController, questionId = questionId)
        }
        composable(Screen.EditAnswers.route) { EditAnswers(navController) }
        composable(Screen.Results.route) { Results(navController) }
    }}
