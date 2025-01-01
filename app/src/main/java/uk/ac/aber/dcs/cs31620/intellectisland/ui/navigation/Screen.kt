package uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation


sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("homeScreen")
    object Login : Screen("login")
    object Delete : Screen("delete")
    object StartQuiz : Screen("startQuiz")
    object Question : Screen("question")
    object AddQuestions : Screen("addQuestions")
    object AddAnswers : Screen("addAnswers")
    object EditQuestions : Screen("editQuestions")
    object EditAnswers : Screen("editAnswers")
}

val screens = listOf(
    Screen.HomeScreen,
    Screen.Login,
    Screen.AddQuestions,
    Screen.EditQuestions,
    Screen.Delete,
    Screen.StartQuiz,
    Screen.Question,
    Screen.AddAnswers,
    Screen.EditAnswers

)