package uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation


sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("homeScreen")
    object Login : Screen("login")
    object RemoveQuestions : Screen("removeQuestions")
    object StartQuiz : Screen("startQuiz")
    object Question : Screen("question")
    object AddQuestions : Screen("addQuestions")
    object EditQuestionScreen : Screen("editQuestionScreen")
    object EditAnswers : Screen("editAnswers")
    object Results : Screen("results")
    object UserProfile : Screen("userProfile")
}

val screens = listOf(
    Screen.HomeScreen,
    Screen.Login,
    Screen.AddQuestions,
    Screen.EditQuestionScreen,
    Screen.RemoveQuestions,
    Screen.StartQuiz,
    Screen.Question,
    Screen.EditAnswers,
    Screen.Results,
    Screen.UserProfile

)