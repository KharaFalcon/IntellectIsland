package uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation


sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("homeScreen")
    object RemoveQuestions : Screen("removeQuestions")
    object StartQuiz : Screen("startQuiz")
    object Question : Screen("question")
    object AddQuestions : Screen("addQuestions")
    object EditQuestionScreen : Screen("edit_question_screen/{questionId}")
    object EditAnswers : Screen("editAnswers")
    object Results : Screen("results")
    object UserProfile : Screen("userProfile")
    object UserName : Screen("userName")
}

val screens = listOf(
    Screen.HomeScreen,
    Screen.AddQuestions,
    Screen.EditQuestionScreen,
    Screen.RemoveQuestions,
    Screen.StartQuiz,
    Screen.Question,
    Screen.EditAnswers,
    Screen.Results,
    Screen.UserProfile,
    Screen.UserName

)