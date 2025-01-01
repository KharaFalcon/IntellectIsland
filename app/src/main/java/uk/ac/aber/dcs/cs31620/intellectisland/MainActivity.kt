
package uk.ac.aber.dcs.cs31620.intellectisland
import Question
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.HomeScreen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.AddAnswers
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.AddQuestions
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.EditAnswers
import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement.EditQuestions

import uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode.StartQuiz
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.IntellectIslandTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntellectIslandTheme(dynamicColor = false) {
               // HomeScreen()
                //StartQuiz()
            }
            BuildNavigationGraph()
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
            composable(Screen.HomeScreen.route){ HomeScreen(navController)}
            composable(Screen.StartQuiz.route){ StartQuiz(navController)}
            composable(Screen.Question.route){ Question(navController) }
            composable(Screen.AddQuestions.route){ AddQuestions(navController) }
            composable(Screen.AddAnswers.route){ AddAnswers(navController) }
            composable(Screen.EditQuestions.route){ EditQuestions(navController) }
            composable(Screen.EditAnswers.route){ EditAnswers(navController) }
    }
}

@Composable
fun GreetingPreview() {
    IntellectIslandTheme {
        //HomeScreen()
    }
}

